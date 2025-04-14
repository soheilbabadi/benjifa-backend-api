package club.benjifa.benjifa_backend_api.blog.presentation;

import club.benjifa.benjifa_backend_api.blog.application.BlogPostService;
import club.benjifa.benjifa_backend_api.blog.dto.BlogPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog-post")
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public List<BlogPostDto> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return blogPostService.getAllPosts(pageable);
    }

    @GetMapping("/get-by-author/author")
    public List<BlogPostDto> getPostsByAuthor(@RequestParam String author, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return blogPostService.getPostsByAuthor(author, pageable);
    }

    @GetMapping("/{id}")
    public BlogPostDto getPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public BlogPostDto createPost(@RequestBody BlogPostDto blogPostDto) {
        return blogPostService.createPost(blogPostDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public BlogPostDto updatePost(@RequestBody BlogPostDto blogPostDto) {
        return blogPostService.updatePost(blogPostDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
    }
}