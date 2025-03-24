package club.benjifa.benjifa_backend_api.exception;

import lombok.Getter;

@Getter

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    LOOKUP_NOT_FOUND("lookup.not.found"),
    DUPLICATE_ENTITY("duplicate"),
    ENTITY_EXCEPTION("exception"),
    VALUE_EXCEPTION("value.not.correct"),
    PARENT_NOT_FOUND("parent.not.found"),
    ACCESS_EXCEPTION("access.exception"),
    EXCEL_VALIDATION("excel.validation"),
    USER_DISABLED("user.disable"),
    COUNT_EXCEPTION("count.exception"),
    ID_EMPTY("id.empty"),
    VALIDATION_EXCEPTION("validation.exception");


    final String value;

    ExceptionType(String value) {
        this.value = value;
    }
}
