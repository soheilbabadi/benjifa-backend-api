package club.benjifa.benjifa_backend_api.person.application;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonUpdateDto;

import java.util.List;

public interface PersonService {
    UserDetailsService userDetailsService();


    PersonDto findUserByEmail(String email);

    PersonDto findUserId(String id);

    Person findByEmail(String email);

    Person findByUsername(String username);

    PersonDto findDtoByUsername(String username);

    PersonDto findByMobile(String mobile);

    PersonDto setAccountActive(String email);

    UserDetails loadUserByUsername(String username);

    PersonDto update(PersonUpdateDto Person, String token);
    List<Person> findByCity(Lookup city);


}
