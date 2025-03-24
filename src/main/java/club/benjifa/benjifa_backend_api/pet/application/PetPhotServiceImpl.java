package club.benjifa.benjifa_backend_api.pet.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.person.application.PersonService;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhoto;
import club.benjifa.benjifa_backend_api.pet.dto.PetPhotoDto;
import club.benjifa.benjifa_backend_api.pet.repository.PetPhotoRepository;
import club.benjifa.benjifa_backend_api.pet.repository.PetRepository;
import club.benjifa.benjifa_backend_api.security.JwtService;
import club.benjifa.benjifa_backend_api.utils.Strutting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetPhotServiceImpl {

    private PetPhotoRepository petPhotoRepository;
    private PetRepository petRepository;
    private final JwtService jwtService;
    private final PersonService personService;
    @Value("${pics.baseUrl}")
    private String baseUrl;


    public PetPhotoDto create (PetPhotoDto petPhotoDto,String token) {
        String username = jwtService.getUsernameFromToken(token);

        var pet = petRepository.findById (petPhotoDto.getPetId ()).orElseThrow (() -> new RuntimeException (""));
        var photo = PetPhoto.builder ().pet (pet)
                .mimeType (petPhotoDto.getMimeType ())
                .createAt (LocalDateTime.now ())
                .fileContent (petPhotoDto.getFileContent ())
                .fileSize (petPhotoDto.getFileSize ())
                .photoUrl (baseUrl + Strutting.generateRandom (10))
                .fileName (petPhotoDto.getFileName ())
                .username (username)
                .build ();

        petPhotoRepository.save (photo);

        return fromEntity (photo);
    }



    public void delete(long id, String token) {
        String username = jwtService.getUsernameFromToken(token);
        PetPhoto photo = petPhotoRepository.findById(id).orElseThrow(() -> new RuntimeException("Photo not found"));

        if (!photo.getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this photo");
        }

        petPhotoRepository.delete(photo);
    }
    public PetPhotoDto findById(long id) {
        PetPhoto petPhoto = petPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        return fromEntity(petPhoto);
    }

    public PetPhotoDto findByUrl(String photoUrl) {
        PetPhoto petPhoto = petPhotoRepository.findByPhotoUrl(photoUrl)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        return fromEntity(petPhoto);
    }
    public List<PetPhotoDto> getAllPhotosOfPet(String petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        List<PetPhoto> petPhotos = petPhotoRepository.findAllByPet(pet);

        return petPhotos.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
    private Person getPersonFromtoken(String token) {
        return personService.findByUsername(jwtService.getUsernameFromToken(token));
    }


    private  PetPhoto fromDto(PetPhotoDto petPhotoDto, Pet pet, String baseUrl) {
        return PetPhoto.builder()
                .id(petPhotoDto.getId())
                .fileContent(petPhotoDto.getFileContent())
                .mimeType(petPhotoDto.getMimeType())
                .fileName(petPhotoDto.getFileName())
                .fileSize(petPhotoDto.getFileSize())
                .createAt(LocalDateTime.now())
                .photoUrl(baseUrl + Strutting.generateRandom (12))
                .pet(pet)
                .share (petPhotoDto.isShare ())
                .build();
    }

    private PetPhotoDto fromEntity(PetPhoto petPhoto) {
        return PetPhotoDto.builder()
                .id(petPhoto.getId())
                .fileContent(petPhoto.getFileContent())
                .mimeType(petPhoto.getMimeType())
                .fileName(petPhoto.getFileName())
                .fileSize(petPhoto.getFileSize())
                .username(petPhoto.getPet().getPerson().getUsername())
                .petId(petPhoto.getPet().getId())
                .share(petPhoto.isShare())
                .build();
    }
}
