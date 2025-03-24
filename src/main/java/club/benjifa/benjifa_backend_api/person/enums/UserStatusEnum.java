package club.benjifa.benjifa_backend_api.person.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED"),
    BLOCKED("BLOCKED"),
    SUSPENDED("SUSPENDED"),
    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    DRAFT("DRAFT"),
    ;

    private final String value;


}