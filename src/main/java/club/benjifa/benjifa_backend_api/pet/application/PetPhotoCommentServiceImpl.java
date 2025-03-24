package club.benjifa.benjifa_backend_api.pet.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhotoComment;
import club.benjifa.benjifa_backend_api.pet.dto.PetPhotoCommentDto;
import club.benjifa.benjifa_backend_api.pet.repository.PetPhotoCommentRepository;
import club.benjifa.benjifa_backend_api.pet.repository.PetPhotoRepository;
import club.benjifa.benjifa_backend_api.utils.UserUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetPhotoCommentServiceImpl {

    private final PetPhotoCommentRepository petPhotoCommentRepository;
    private final PetPhotoRepository petPhotoRepository;
    private final UserUtils userUtils;

    public PetPhotoCommentDto createPetPhotoComment(PetPhotoCommentDto petPhotoCommentDto, String token) {
        var petPhoto = petPhotoRepository.findById(petPhotoCommentDto.getPetPhotoId()).orElseThrow(() -> new RuntimeException("0"));
        var person = userUtils.getPersonFromToken(token);

        PetPhotoComment comment = PetPhotoComment.builder()
                .commentText(petPhotoCommentDto.getCommentText())
                .createdAt(LocalDateTime.now())
                .petPhoto(petPhoto)
                .person(person)
                .build();
        return fromEntity(petPhotoCommentRepository.save(comment));
    }

    public void deletePetPhotoComment(long id) {
        petPhotoCommentRepository.deleteById(id);
    }


    private PetPhotoCommentDto fromEntity(PetPhotoComment comment) {
        List<PersonDto> personDtoList = comment.getLikedBy().stream()
                .map(person -> PersonDto.builder()
                        .id(person.getId())
                        .username(person.getUsername())
                        .build())
                .collect(Collectors.toList());

        return PetPhotoCommentDto.builder()
                .commentText(comment.getCommentText())
                .createdAt(comment.getCreatedAt())
                .id(comment.getId())
                .petPhotoId(comment.getPetPhoto().getId())
                .username(comment.getPerson().getUsername())
                .personDtoList(personDtoList)
                .build();
    }
}
