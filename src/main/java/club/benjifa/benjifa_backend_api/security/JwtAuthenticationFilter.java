package club.benjifa.benjifa_backend_api.security;


import club.benjifa.benjifa_backend_api.person.application.PersonService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final PersonService personService;
    @Value("${jwt.prefix}")
    private String prefix;
    @Value("${jwt.header}")
    private String header;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(header);
        final String jwt;
        String username = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith(prefix)) {
            filterChain.doFilter(request, response);
            return;
        }


        jwt = authorizationHeader.split(" ")[1].trim();
         jwt.replaceAll(" ", "");
        request.setAttribute("Authorization", jwt);
        username = jwtService.getUsernameFromToken(jwt);


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = personService.userDetailsService().loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                var passwordAuthenticationToken = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
                passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(passwordAuthenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }

}
