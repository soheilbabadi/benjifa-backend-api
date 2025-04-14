package club.benjifa.benjifa_backend_api.blog.application;

import club.benjifa.benjifa_backend_api.blog.domain.BlogPost;
import club.benjifa.benjifa_backend_api.blog.dto.BlogPostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogPostService {
    List<BlogPostDto> getAllPosts(Pageable pageable);

    List<BlogPostDto> getPostsByAuthor(String author, Pageable pageable);

    BlogPostDto getPostById(Long id);

    BlogPostDto createPost(BlogPostDto blogPostDto);

    BlogPostDto updatePost(BlogPostDto blogPostDto);

    void deletePost(Long id);

    BlogPostDto fromEntity(BlogPost blogPost);

    BlogPost toEntity(BlogPostDto blogPostDto);
}
