package club.benjifa.benjifa_backend_api.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    private String id;
    private String name;
    private String description;
    private String breed;
    private int breedId;
    private LocalDate birthDate;
    private LocalDateTime createDate;

    private float weight;
    private float height;
    private String gender;
    private String vaccinationStatus;
    private boolean neutered;
    private String color;
    private String adoptionStatus;
    private String ownerName;
    private String username;

}