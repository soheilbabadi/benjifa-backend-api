package club.benjifa.benjifa_backend_api.notification.enums;

public enum MessageTypeEnum {

    SIGNUP("signup"),
    SIGNING("signing"),
    VERIFY_OTP("verify-otp"),
    REFRESH("refresh"),
    LOGOUT("logout"),
    UPDATE_PASSWORD("update-password");

    private final String type;

    MessageTypeEnum(String type) {
        this.type = type;
    }
}
