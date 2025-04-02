package club.benjifa.benjifa_backend_api.ask.model;

import club.benjifa.benjifa_backend_api.ask.dto.PetType;
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

@Table(name = "question", indexes = {
        @Index(name = "idx_author", columnList = "author"),
        @Index(name = "idx_question_type", columnList = "questionType"),
})
public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 2064610940962143800L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;

    private String questionType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @ElementCollection
    @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private List<String> tags;

    private int viewCount = 0;

    private boolean resolved = false;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    @Enumerated(EnumType.STRING)
    private PetType petType;

}
