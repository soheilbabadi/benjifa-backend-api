package club.benjifa.benjifa_backend_api.person.dto;


public record PersonPhotoDto(
        long id,
        byte[] fileContent,
        String mimeType,
        String fileName,
        long fileSize,
        String username
) {

}