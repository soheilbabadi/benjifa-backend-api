package club.benjifa.benjifa_backend_api.person.application;


import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.person.domain.PersonPhoto;
import club.benjifa.benjifa_backend_api.person.dto.PersonPhotoDto;
import club.benjifa.benjifa_backend_api.person.repository.PersonPhotoRepo;
import club.benjifa.benjifa_backend_api.person.repository.PersonRepo;
import club.benjifa.benjifa_backend_api.security.JwtService;
import club.benjifa.benjifa_backend_api.utils.PictureUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class PersonPhotoServiceImpl implements PersonPhotoService {

    private final PersonPhotoRepo personPhotoRepo;
    private final PersonRepo personRepo;
    private final JwtService jwtService;

    @Value("${pics.baseUrl}")
    private String baseUrl;


    @Override
    public PersonPhotoDto create(MultipartFile file, String token) throws IOException {

        String username = jwtService.getUsernameFromToken(token);

        var person = personRepo.findByUsername(username).orElseThrow();

        if (personPhotoRepo.findByPerson(person).isPresent()) {


            var photo = personPhotoRepo.findByPerson(person).get();
            photo.setFileContent(PictureUtil.createThumbnail(file));
            photo.setFileName(file.getOriginalFilename());
            photo.setFileSize(file.getSize());
            photo.setMimeType(file.getContentType());
            photo.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")));
            photo.setPhotoUrl(baseUrl + "person/get-photo/" + username);
//            photo.setPerson(person);
            var savedPhoto = personPhotoRepo.save(photo);
            return convertToDto(savedPhoto);

        }

        var photo = PersonPhoto.builder()
                .fileContent(PictureUtil.createThumbnail(file))
                .fileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .createAt(LocalDateTime.now(ZoneId.of("UTC")))
                .photoUrl(baseUrl + "person/get-photo/" + username)
//                .person(person)
                .build();

        var savedPhoto = personPhotoRepo.save(photo);
        return convertToDto(savedPhoto);

    }

    @Override
    public void delete(String token) {
        String username = jwtService.getUsernameFromToken(token);

        var person = personRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        var photo = personPhotoRepo.findByPerson(person)
                .orElseThrow(() -> new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        personPhotoRepo.delete(photo);
    }

    @Override
    public PersonPhotoDto getByUsername(String username) {
        var person = personRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        var photo = personPhotoRepo.findByPerson(person)
                .orElseThrow(() -> new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        return convertToDto(photo);
    }

    @Override
    public PersonPhotoDto getById(Long id) {
        var photo = personPhotoRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        return convertToDto(photo);

    }

    private PersonPhotoDto convertToDto(PersonPhoto photo) {
        return null;
    }
}
