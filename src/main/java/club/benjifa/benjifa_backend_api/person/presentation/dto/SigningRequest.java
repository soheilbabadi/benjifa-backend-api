package club.benjifa.benjifa_backend_api.person.presentation.dto;

import jakarta.validation.constraints.NotEmpty;

public record SigningRequest(
        @NotEmpty(message = "Email is required")
        String email,

        @NotEmpty(message = "Password is required")
        String password
) {

}