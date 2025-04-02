package club.benjifa.benjifa_backend_api.ask.dto;

import lombok.Getter;

@Getter
public enum QuestionType {
    HEALTH("سلامتی"),
    BEHAVIOR("رفتار"),
    NUTRITION("تغذیه"),
    TRAINING("آموزش"),
    GROOMING("آرایش"),
    BREEDING("پرورش"),
    ADOPTION("سرپرستی"),
    EQUIPMENT("تجهیزات و لوازم"),
    VETERINARY("مراقبت های دامپزشکی"),
    FIRST_AID("کمک های اولیه"),
    LEGAL("قانون و مقررات"),
    TRAVEL("سفر با حیوانات خانگی"),
    OTHER("سایر");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }
}