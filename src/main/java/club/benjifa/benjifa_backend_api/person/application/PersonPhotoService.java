package club.benjifa.benjifa_backend_api.person.application;


import org.springframework.web.multipart.MultipartFile;
import club.benjifa.benjifa_backend_api.person.dto.PersonPhotoDto;

import java.io.IOException;

public interface PersonPhotoService {
    PersonPhotoDto create(MultipartFile file, String username) throws IOException;

    void delete(String token);

    PersonPhotoDto getByUsername(String token);

    PersonPhotoDto getById(Long id);
}
