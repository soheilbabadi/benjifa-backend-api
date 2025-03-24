package club.benjifa.benjifa_backend_api.pet.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }
}