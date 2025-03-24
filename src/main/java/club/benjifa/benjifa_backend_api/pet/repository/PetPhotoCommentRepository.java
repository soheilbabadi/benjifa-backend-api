package club.benjifa.benjifa_backend_api.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhoto;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhotoComment;

import java.util.List;

@Repository
public interface PetPhotoCommentRepository extends JpaRepository<PetPhotoComment, Long> {


    List<PetPhotoComment> findAllByPerson(Person person);

    List<PetPhotoComment> findAllByPetPhoto(PetPhoto petPhoto);
}
