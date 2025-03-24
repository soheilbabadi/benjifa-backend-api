package club.benjifa.benjifa_backend_api.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.person.application.PersonService;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.security.JwtService;

@Service
@RequiredArgsConstructor
public class UserUtils {
    private final JwtService jwtService;
    private final PersonService personService;

    public Person getPersonFromToken(String token) {
        return personService.findByUsername(jwtService.getUsernameFromToken(token));
    }

    public String getUsernameFromToken(String token) {
        return jwtService.getUsernameFromToken(token);
    }

    public Person getPersonFromUsername(String username) {
        return personService.findByUsername(username);
    }
}
