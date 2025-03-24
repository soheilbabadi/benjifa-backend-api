package club.benjifa.benjifa_backend_api.person.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    SUPPORT("ROLE_SUPPORT"),
    USER("ROLE_USER"),
    SITTER("ROLE_SITTER"),
    GUEST("ROLE_GUEST");

    private final String value;

}
