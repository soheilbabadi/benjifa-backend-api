package club.benjifa.benjifa_backend_api.person.presentation.dto;


public record JwtAuthenticationResponse(
        String token,
        String refreshToken
) {

}