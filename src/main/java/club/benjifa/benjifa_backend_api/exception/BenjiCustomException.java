package club.benjifa.benjifa_backend_api.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Component
@RequiredArgsConstructor
public class BenjiCustomException extends RuntimeException {

    public static final String ENTITY_NOT_FOUND = "Entity not found";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String IO_EXCEPTION = "unable to read file";
    @Serial
    private static final long serialVersionUID = 2937969386484720825L;

    public static Exception RuntimeException(String messageTemplate) {
        return new EntityDidNotFoundException(messageTemplate);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class EntityDidNotFoundException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 8490379462774046464L;

        public EntityDidNotFoundException(String message) {
            super(message);
        }
    }


    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 8824943035015754069L;

        public UnauthorizedException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class DuplicateEntityException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 6097697152768645228L;

        public DuplicateEntityException(String message) {
            super(message);
        }
    }

    public static class CustomException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -1584388453062884064L;

        public CustomException(String message) {
            super(message);
        }
    }

    public static class ExcelException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -9214904749662505366L;

        public ExcelException(String message) {
            super(message);
        }
    }

    public static class ParentNotFoundException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -5814948709735338639L;

        public ParentNotFoundException(String message) {
            super(message);
        }
    }


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)

    public static class FileContentException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 8038292358982007090L;

        public FileContentException(String message) {
            super(message);
        }
    }

}
