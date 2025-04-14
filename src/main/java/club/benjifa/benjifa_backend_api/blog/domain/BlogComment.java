package club.benjifa.benjifa_backend_api.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class BlogComment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1655131892575231925L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = BlogPost.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BlogPost post;

}
