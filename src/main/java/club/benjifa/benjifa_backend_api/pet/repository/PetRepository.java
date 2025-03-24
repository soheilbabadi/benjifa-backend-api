package club.benjifa.benjifa_backend_api.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, String> {

    List<Pet> findAllByPerson(Person person);

    List<Pet> findAllByNameContains(String name);

}
