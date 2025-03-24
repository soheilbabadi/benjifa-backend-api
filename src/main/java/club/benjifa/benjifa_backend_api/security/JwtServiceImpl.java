package club.benjifa.benjifa_backend_api.security;

import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.security.dto.TokenModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    private final TokenSaverService tokenSaverService;

    @Value("${jwt.expiration}")
    private String expireTime;

    @Value("${jwt.refresh-expiration}")
    private String refreshExpireTime;


    @Value("${jwt.issuer}")
    private String issuer;


    @Value("${jwt.secret}")
    private String secret;


    @Override
    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String generateToken(Person userInfo) {

        String jwt = Jwts.builder()
                .setSubject(userInfo.getUsername())
                .claim("email", userInfo.getEmail())
                .claim("role", userInfo.getRole())
                .claim("phone", userInfo.getPhone())
//                .claim("profileImageUrl", userInfo.getPersonPhoto().getPhotoUrl())
                .claim("firstName", userInfo.getFirstName())
                .claim("lastName", userInfo.getLastname())
                .claim("status", userInfo.getStatus().getValue())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expireTime)))
                .signWith(getSigningKey())
                .setIssuer(issuer)
                .compact();

        TokenModel tokenModel = new TokenModel(jwt, userInfo.getUsername(), new Date(System.currentTimeMillis() + Long.parseLong(expireTime)));
        tokenSaverService.saveToken(tokenModel);
        return jwt;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        var jws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

        if (!existToken(token)) {
            return false;
        }

        return jws.parseClaimsJws(token).getBody().getSubject().equals(userDetails.getUsername())
                && jws.parseClaimsJws(token).getBody().getExpiration().after(new Date());
    }


    @Override
    public String getUsernameFromToken(String token) {
        token = token.replaceAll("Bearer ", "");
        if (!existToken(token)) {
            return null;
        }
        return getClaimFromToken(token, Claims::getSubject);
    }


    @Override
    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("role").toString());
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String getRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(this.refreshExpireTime)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public void logout(String token) {

//        tokenSaverService.deleteByToken(token);
    }

    @Override
    public boolean existToken(String token) {
        return tokenSaverService.existsByToken(token);
    }

    @Override
    public boolean isAdmin(String token) {
        return !getRoleFromToken(token).equals("ROLE_ADMIN");
    }

    @Override
    public boolean isSupport(String token) {
        return !getRoleFromToken(token).equals("ROLE_SUPPORT");
    }

    @Override
    public ResponseCookie createCookie(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(false)
                .secure(true)
                .path("/api/v1")
                .maxAge(0)
                .build();
    }


}
