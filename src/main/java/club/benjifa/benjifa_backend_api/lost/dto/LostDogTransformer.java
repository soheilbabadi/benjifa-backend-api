package club.benjifa.benjifa_backend_api.lost.dto;

import club.benjifa.benjifa_backend_api.lookup.repository.LookupRepository;
import club.benjifa.benjifa_backend_api.lost.domain.LostDogAdvertise;
import club.benjifa.benjifa_backend_api.person.repository.PersonRepo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LostDogTransformer {

    private static LookupRepository lookupRepository;
    private static PersonRepo personRepository;

    public static club.benjifa.benjifa_backend_api.lost.dto.LostDogAdvertiseDto fromEntity(LostDogAdvertise entity) {
        return new LostDogAdvertiseDto(
                entity.getId(),
                entity.getDogName(),
                entity.getBreed().getTitle(),
                entity.getBreed().getId(),
                entity.getAge(),
                entity.getColor().getTitle(),
                entity.getColor().getId(),
                entity.getLastSeenLocation(),
                entity.getOwnerName().getFirstName() + " " + entity.getOwnerName().getLastname(),
                entity.getDescription(),
                entity.isFound(),
                entity.getLostOn(),
                entity.getRegisterOn(),
                entity.getExpireOn(),
                entity.getOwnerName().getUsername(),
                entity.isActive()
        );
    }

    public static LostDogAdvertise toEntity(LostDogAdvertiseDto dto) {
        return LostDogAdvertise.builder()
                .id(dto.id())
                .dogName(dto.dogName())
                .breed(lookupRepository.findById(dto.breedId()).orElseThrow(() -> new RuntimeException("Breed not found")))
                .age(dto.age())
                .color(lookupRepository.findById(dto.colorId()).orElseThrow(() -> new RuntimeException("Color not found")))
                .lastSeenLocation(dto.lastSeenLocation())
                .ownerName(personRepository.findByUsername(dto.username()).orElseThrow(() -> new RuntimeException("Owner not found")))
                .description(dto.description())
                .found(dto.found())
                .lostOn(dto.lostOn())
                .registerOn(dto.registerOn())
                .expireOn(dto.expireOn())
                .active(dto.active())
                .build();
    }
}
