package club.benjifa.benjifa_backend_api.card.enums;

import lombok.Getter;

@Getter
public enum ReminderStatusEnum {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    CANCELLED("CANCELLED");

    private final String value;

    ReminderStatusEnum(String value) {
        this.value = value;
    }
}
