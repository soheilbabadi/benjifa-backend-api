package club.benjifa.benjifa_backend_api.lost.domain;


import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class LostDogAdvertise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dogName;

    @ManyToOne(targetEntity = Lookup.class)
    @JoinColumn(name = "breed_id")
    private Lookup breed;

    @Column(nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Lookup color;

    private String lastSeenLocation;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "owner_name_id")
    private Person ownerName;

    @Column(length = 500, nullable = false, columnDefinition = "varchar(500)")
    private String description;

    @Column(nullable = false)
    private boolean found = false;
    @Column(nullable = false)
    private LocalDate lostOn = LocalDate.now();
    @Column(nullable = false)
    private LocalDateTime registerOn = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime expireOn = LocalDateTime.now().plusMonths(1);

    @Column(nullable = false)
    private boolean active = true;
}
