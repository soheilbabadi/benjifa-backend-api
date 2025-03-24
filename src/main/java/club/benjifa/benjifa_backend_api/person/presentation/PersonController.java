package club.benjifa.benjifa_backend_api.person.presentation;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import club.benjifa.benjifa_backend_api.person.application.PersonPhotoService;
import club.benjifa.benjifa_backend_api.person.application.PersonService;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonUpdateDto;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonPhotoService personPhotoService;

    @GetMapping("/get/{username}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable String username) {
        return ResponseEntity.ok(personService.findDtoByUsername(username));
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<PersonDto> getPersonByEmail(@PathVariable String email) {
        return ResponseEntity.ok(personService.findUserByEmail(email));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_UER','ROLE_SUPPORT')")
    @PutMapping("/update")
    public ResponseEntity<PersonDto> updatePerson(@RequestHeader("Authorization") String token, @RequestBody PersonUpdateDto dto) {
        return ResponseEntity.ok(personService.update(dto, token));
    }

    @GetMapping(value = "/get-photo-by-id/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPersonPhotoById(@PathVariable Long id) {
        var picture = personPhotoService.getById(id);
        return picture.fileContent();
    }

    @GetMapping(value = "/get-photo/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPersonPhoto(@PathVariable String username) {
        var picture = personPhotoService.getByUsername(username);
        return picture.fileContent();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_UER','ROLE_SUPPORT')")
    @PostMapping(value = "/upload-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] uploadPhoto(@RequestHeader("Authorization") String token, @RequestPart(value = "file") MultipartFile file) throws IOException {
        var picture = personPhotoService.create(file, token);
        return picture.fileContent();
    }


    @DeleteMapping("/delete-photo")
    public ResponseEntity<Void> deletePhoto(@RequestHeader("Authorization") String token) {
        personPhotoService.delete(token);
        return ResponseEntity.ok().build();
    }
}
