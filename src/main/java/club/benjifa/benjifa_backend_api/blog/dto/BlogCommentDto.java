package club.benjifa.benjifa_backend_api.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogCommentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1655131892575231925L;

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Long postId;

}
