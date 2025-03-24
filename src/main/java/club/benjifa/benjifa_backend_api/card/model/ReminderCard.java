package club.benjifa.benjifa_backend_api.card.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.card.enums.ReminderStatusEnum;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ReminderCard implements Serializable {

    @Serial
    private static final long serialVersionUID = -1899846247040957279L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime reminderDate;

    @Column(nullable = false)
    private String username;

    @Enumerated(value = EnumType.STRING)
    private ReminderStatusEnum status;

    @Column(nullable = false)
    private LocalDateTime createOn;
    private LocalDateTime expireOn;

    @Column(nullable = false)
    private boolean notifyMe;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Pet.class)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}