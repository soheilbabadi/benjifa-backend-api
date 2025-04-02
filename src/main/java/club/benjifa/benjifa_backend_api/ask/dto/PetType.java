package club.benjifa.benjifa_backend_api.ask.dto;

import lombok.Getter;

@Getter
public enum PetType {
    DOG("سگ"),
    CAT("گربه"),
    BIRD("پرنده"),
    FISH("ماهی"),
    REPTILE("خزنده"),
    BIG_ANIMAL("دام بزرگ"),
    OTHER("سایر");

    private final String displayName;

    PetType(String displayName) {
        this.displayName = displayName;
    }
}