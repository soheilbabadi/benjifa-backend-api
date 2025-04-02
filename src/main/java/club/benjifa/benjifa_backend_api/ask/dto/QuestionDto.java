package club.benjifa.benjifa_backend_api.ask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2064610940962143800L;

    private long id;

    private String author;

    private String questionType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private List<String> tags;

    private int viewCount ;

    private boolean resolved ;

    private String title;

    private String content;

    private PetType petType;

    private List<AnswerDto> answers;
}
