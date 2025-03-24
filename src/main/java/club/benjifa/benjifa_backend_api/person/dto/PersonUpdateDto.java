package club.benjifa.benjifa_backend_api.person.dto;

public record PersonUpdateDto(
        String nationalId,
        String firstName,
        String lastname,
        String phone,
        String username
) {
}