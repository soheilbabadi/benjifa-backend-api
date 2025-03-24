package club.benjifa.benjifa_backend_api.pet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.person.domain.Person;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class PetPhotoComment implements Serializable {

    @Serial
    private static final long serialVersionUID = 4262799439836596860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String commentText;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private long likeCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = PetPhoto.class)
    @JoinColumn(nullable = false)
    private PetPhoto petPhoto;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Person.class)
    @JoinColumn(nullable = false)
    private Person person;

    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> likedBy;
}