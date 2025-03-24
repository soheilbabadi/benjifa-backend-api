package club.benjifa.benjifa_backend_api.person.presentation.dto;


public record UpdatePasswordDto(

        String oldPassword,
        String newPassword

) {
}
