package club.benjifa.benjifa_backend_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseConfig {


    public static final boolean PW_POLICY_ENABLE = true;
    public static final int PW_POLICY_MIN_LENGTH = 6;
    public static final int PW_POLICY_MAX_LENGTH = 10;
    public static final String PW_POLICY_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
    public static final String PW_POLICY_ERROR_MSG = "passwordPolicy.errorMsg";


    public enum ErrorMessages {

        ERROR_CODE_VALIDATING_PASSWORD_POLICY("93810650_500", "Error while validating password policy");


        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return code + " - " + message;
        }
    }
}