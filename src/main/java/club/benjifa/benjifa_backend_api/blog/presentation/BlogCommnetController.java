package club.benjifa.benjifa_backend_api.blog.presentation;

import club.benjifa.benjifa_backend_api.blog.application.BlogCommentService;
import club.benjifa.benjifa_backend_api.blog.dto.BlogCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/blog-comment")
public class BlogCommnetController {

    private final BlogCommentService blogCommentService;

    // Add methods to handle requests related to blog comments

    @GetMapping("/get-by-blog-post/{postId}")
    public ResponseEntity<List<BlogCommentDto>> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(blogCommentService.getAllByPostId(postId));
    }

    @DeleteMapping("/delete-all-by-blog-post/{postId}")
    public ResponseEntity<Void> deleteAllComments(@PathVariable Long postId) {
        blogCommentService.deleteAllByPostId(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        blogCommentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<BlogCommentDto> createComment(@RequestBody BlogCommentDto blogCommentDto) {
        return ResponseEntity.ok(blogCommentService.createComment(blogCommentDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<BlogCommentDto> updateComment(@RequestBody BlogCommentDto blogCommentDto) {
        return ResponseEntity.ok(blogCommentService.updateComment(blogCommentDto));
    }

}
