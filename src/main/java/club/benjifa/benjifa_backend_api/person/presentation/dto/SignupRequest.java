package club.benjifa.benjifa_backend_api.person.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(
        @Email(message = "Email is not valid")
        @NotEmpty(message = "Email is required")
        String email,

        @NotEmpty(message = "Password is required")
        String password,

        @NotEmpty(message = "Firstname is required")
        String firstName,

        @NotEmpty(message = "Lastname is required")
        String lastName
) {

}