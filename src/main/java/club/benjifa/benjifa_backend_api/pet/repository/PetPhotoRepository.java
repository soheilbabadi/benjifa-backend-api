package club.benjifa.benjifa_backend_api.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhoto;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {

    List<PetPhoto> findAllByPet(Pet pet);

    Optional<PetPhoto> findByPhotoUrl(String photoUrl);

}
