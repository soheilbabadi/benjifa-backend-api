package club.benjifa.benjifa_backend_api.security;

import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import club.benjifa.benjifa_backend_api.person.domain.Person;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(Person userInfo);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

    String getRoleFromToken(String token);

    Claims getClaimsFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    String getRefreshToken(Map<String, Object> claims, UserDetails userDetails);

    Key getSigningKey();

    void logout(String token);

    boolean existToken(String token);


    boolean isAdmin(String token);

    boolean isSupport(String token);

    ResponseCookie createCookie(String name, String value);
}




