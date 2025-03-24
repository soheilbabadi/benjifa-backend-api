package club.benjifa.benjifa_backend_api.lost.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.lost.application.LostDogService;
import club.benjifa.benjifa_backend_api.lost.dto.LostDogAdvertiseDto;

@RequiredArgsConstructor
@RequestMapping("/lost-dog")
@RestController
public class LostDogAdvertiseController {

    private final LostDogService lostDogService;


    @GetMapping("/all/{page}/{size}")
    public ResponseEntity<?> findAll(@PathVariable int page, @PathVariable int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(lostDogService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(lostDogService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        return ResponseEntity.ok(lostDogService.findAll(pageable));

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_SUPPORT')")
    @PostMapping
    public ResponseEntity<?> register(@RequestHeader("Authorization") String token, @RequestBody LostDogAdvertiseDto lostDogAdvertiseDto) {
        return ResponseEntity.ok(lostDogService.register(lostDogAdvertiseDto, token));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_SUPPORT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) throws BenjiCustomException.UnauthorizedException {
        lostDogService.delete(id, token);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_SUPPORT')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@RequestHeader("Authorization") String token, @PathVariable Long id) throws BenjiCustomException.UnauthorizedException {
        lostDogService.disable(id, token);
        return ResponseEntity.ok().build();
    }
}
