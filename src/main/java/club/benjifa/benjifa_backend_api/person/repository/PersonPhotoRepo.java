package club.benjifa.benjifa_backend_api.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.person.domain.PersonPhoto;

import java.util.Optional;

@Repository
public interface PersonPhotoRepo extends JpaRepository<PersonPhoto, Long> {

    Optional<PersonPhoto> findByPerson(Person person);

    @Query(value = "delete from person_photo where person_id in (select id from person where username = '?1')", nativeQuery = true)
    void deletePersonPhoto(String username);
}
