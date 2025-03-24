package club.benjifa.benjifa_backend_api.pet.enums;

import lombok.Getter;

@Getter
public enum AdoptionStatusEnum {
    AVAILABLE("Available"),
    PENDING("Pending"),
    ADOPTED("Adopted"),
    UNKNOWN("unknown");


    private final String value;

    AdoptionStatusEnum(String value) {
        this.value = value;
    }
}