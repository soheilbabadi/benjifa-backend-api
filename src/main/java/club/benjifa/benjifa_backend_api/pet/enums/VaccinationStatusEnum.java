package club.benjifa.benjifa_backend_api.pet.enums;

import lombok.Getter;

@Getter
public enum VaccinationStatusEnum {
    NOT_VACCINATED("Not Vaccinated"),
    PARTIALLY_VACCINATED("Partially Vaccinated"),
    FULLY_VACCINATED("Fully Vaccinated"),
    UNKNOWN("unknown");


    private final String value;

    VaccinationStatusEnum(String value) {
        this.value = value;
    }
}