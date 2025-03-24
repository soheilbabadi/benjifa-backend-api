package club.benjifa.benjifa_backend_api.lost.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LostDogAdvertiseDto(
        Long id,
        String dogName,
        String breed,
        int breedId,
        int age,
        String color,
        int colorId,
        String lastSeenLocation,
        String ownerName,
        String description,
        boolean found,
        LocalDate lostOn,
        LocalDateTime registerOn,
        LocalDateTime expireOn,
        String username,
        boolean active
) {
}