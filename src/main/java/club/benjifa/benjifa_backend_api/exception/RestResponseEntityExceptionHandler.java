 package club.benjifa.benjifa_backend_api.exception;

 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.context.request.WebRequest;
 import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

 @ControllerAdvice
 public class RestResponseEntityExceptionHandler
         extends ResponseEntityExceptionHandler {

     @ExceptionHandler(value
             = {IllegalArgumentException.class, IllegalStateException.class})
     protected ResponseEntity<Object> handleConflict(
             RuntimeException ex, WebRequest request) {
         String bodyOfResponse = "This should be application specific";
         return handleExceptionInternal(ex, bodyOfResponse,
                 new HttpHeaders(), HttpStatus.CONFLICT, request);
     }

     @ExceptionHandler(value
             = {BenjiCustomException.EntityDidNotFoundException.class})
     protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
         String bodyOfResponse = "Entity not found";
         return handleExceptionInternal(ex, bodyOfResponse,
                 new HttpHeaders(), HttpStatus.NOT_FOUND, request);
     }

     @ExceptionHandler(value
             = {BenjiCustomException.UnauthorizedException.class})
     protected ResponseEntity<Object> handleUnAuthorized(RuntimeException ex, WebRequest request) {
         String bodyOfResponse = "UnAuthorized";
         return handleExceptionInternal(ex, bodyOfResponse,
                 new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
     }
 }