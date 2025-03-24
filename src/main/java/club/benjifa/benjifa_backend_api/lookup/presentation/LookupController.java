package club.benjifa.benjifa_backend_api.lookup.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import club.benjifa.benjifa_backend_api.lookup.dto.LookupDto;
import club.benjifa.benjifa_backend_api.lookup.service.LookupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
public class LookupController {

    private final LookupService lookupService;


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<LookupDto>> findAllByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(lookupService.findAllByCategoryId(categoryId));
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<LookupDto>> findAllByParentId(@PathVariable int parentId) {
        return ResponseEntity.ok(lookupService.findAllByParentId(parentId));
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<LookupDto> findByValue(@PathVariable String value) {
        return ResponseEntity.ok(lookupService.findByValue(value));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LookupDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(lookupService.findById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<LookupDto>> findAllByTitleContaining(@PathVariable String title) {
        return ResponseEntity.ok(lookupService.findAllByTitleContaining(title));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<LookupDto> create(@RequestBody LookupDto lookupDto) {
        return ResponseEntity.ok(lookupService.create(lookupDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin")
    public ResponseEntity<LookupDto> update(@RequestBody LookupDto lookupDto) {
        return ResponseEntity.ok(lookupService.update(lookupDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        lookupService.delete(id);
        return ResponseEntity.ok().build();
    }
}
