package club.benjifa.benjifa_backend_api.person.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.domain.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, String> {

    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsername(String username);

    Optional<Person> findByPhone(String phone);

    List<Person> findAllByCity(Lookup city);

    long countByEmailOrPhone(String email, String phone);
    boolean existsByEmail(String email);

}
