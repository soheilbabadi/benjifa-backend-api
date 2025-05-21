package club.benjifa.benjifa_backend_api.blog.domain;

import jakarta.persistence.*;
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
@Entity
public class BlogPost implements Serializable {
    @Serial
    private static final long serialVersionUID = -7498458482937246188L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ElementCollection
    @CollectionTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Column(name = "tag")
    private List<String> tags;

    @OneToMany(targetEntity = BlogPost.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "post")
    private List<BlogComment> comments;


    private int viewCount;
}
