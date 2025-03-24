package club.benjifa.benjifa_backend_api.card.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ReminderInbox implements Serializable {

    @Serial
    private static final long serialVersionUID = -3086595324280684728L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime reminderDate;

    private boolean seen;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Person.class)
    private Person person;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Pet.class)
    private Pet pet;

}
