package club.benjifa.benjifa_backend_api.pet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import club.benjifa.benjifa_backend_api.card.model.ReminderCard;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.enums.AdoptionStatusEnum;
import club.benjifa.benjifa_backend_api.pet.enums.GenderEnum;
import club.benjifa.benjifa_backend_api.pet.enums.VaccinationStatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Pet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @UUID
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500, columnDefinition = "varchar(500)")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Lookup.class)
    private Lookup breed;

    @Column(nullable = false)
    private LocalDate birthDate;


    @Column(nullable = false)
    private LocalDateTime createDate;


    @Column(nullable = false)
    private float weight;
    @Column(nullable = false)
    private float height;


    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(value = EnumType.STRING)
    private VaccinationStatusEnum vaccinationStatus;

    @Column(nullable = false)
    private boolean neutered;
    private String color;

    @Enumerated(value = EnumType.STRING)
    private AdoptionStatusEnum adoptionStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Person.class)
    private Person person;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "pet", targetEntity = PetPhoto.class)
    private List<PetPhoto> PetPhotoSet;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = ReminderCard.class,mappedBy = "pet")
    private List<ReminderCard> reminderCardList;


}
