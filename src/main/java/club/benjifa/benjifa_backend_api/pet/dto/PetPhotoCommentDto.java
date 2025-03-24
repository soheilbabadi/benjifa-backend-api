package club.benjifa.benjifa_backend_api.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetPhotoCommentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4262799439836596860L;

    private long id;
    private String commentText;
    private LocalDateTime createdAt;
    private long petPhotoId;
    private long likeCount;
    private String username;
    private List<PersonDto> personDtoList;
}