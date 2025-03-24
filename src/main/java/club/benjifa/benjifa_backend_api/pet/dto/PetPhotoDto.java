package club.benjifa.benjifa_backend_api.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetPhotoDto {
    private long id;
    private byte[] fileContent;
    private String mimeType;
    private String fileName;
    private long fileSize;
    private String username;
    private String petId;
    private boolean share;


}