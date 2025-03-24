package club.benjifa.benjifa_backend_api.card.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.benjifa.benjifa_backend_api.card.application.ReminderCardService;
import club.benjifa.benjifa_backend_api.card.dto.ReminderCardDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reminder-card")
public class ReminderCardController {
    private final ReminderCardService reminderCardService;


    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        var list = reminderCardService.findAllByUsername(token);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var reminderCardDto = reminderCardService.findById(id, token);
        return new ResponseEntity<>(reminderCardDto, HttpStatus.OK);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<?> getByPetId(@RequestHeader("Authorization") String token, @PathVariable String petId) {
        var list = reminderCardService.findAllByPetId(petId, token);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/notifiable/pet/{petId}")
    public ResponseEntity<?> getNotifiableByPetId(@RequestHeader("Authorization") String token, @PathVariable String petId) {
        var list = reminderCardService.findAllNotifiable(petId, token);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token, @RequestBody ReminderCardDto reminderCardDto) {
        var reminderCard = reminderCardService.create(reminderCardDto, token);
        return new ResponseEntity<>(reminderCard, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody ReminderCardDto reminderCardDto) {
        var reminderCard = reminderCardService.update(reminderCardDto, token);
        return new ResponseEntity<>(reminderCard, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        reminderCardService.delete(id, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/inactivate")
    public ResponseEntity<?> inactivate(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var reminderCard = reminderCardService.inactivate(id, token);
        return new ResponseEntity<>(reminderCard, HttpStatus.OK);
    }


    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var reminderCard = reminderCardService.complete(id, token);
        return new ResponseEntity<>(reminderCard, HttpStatus.OK);
    }


}
