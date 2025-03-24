package club.benjifa.benjifa_backend_api.card.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.benjifa.benjifa_backend_api.card.application.ReminderInboxService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reminder-inbox")
public class ReminderInboxController {

    private final ReminderInboxService reminderInboxService;


    @GetMapping
    public ResponseEntity<?> getInbox(@RequestHeader("Authorization") String token) {
        var list = reminderInboxService.findAll(token);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var reminderInboxDto = reminderInboxService.findById(id, token);
        return new ResponseEntity<>(reminderInboxDto, HttpStatus.OK);
    }


    @GetMapping("/pet/{petId}")
    public ResponseEntity<?> getByPetId(@RequestHeader("Authorization") String token, @PathVariable String petId) {
        var list = reminderInboxService.findAllByPet(token, petId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/mark-as-seen/{id}")
    public ResponseEntity<?> markAsSeen(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        reminderInboxService.markReminderAsSeen(id, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        reminderInboxService.delete(id, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
