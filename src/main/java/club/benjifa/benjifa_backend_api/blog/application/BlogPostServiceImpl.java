package club.benjifa.benjifa_backend_api.blog.application;

import club.benjifa.benjifa_backend_api.blog.domain.BlogPost;
import club.benjifa.benjifa_backend_api.blog.dto.BlogCommentDto;
import club.benjifa.benjifa_backend_api.blog.dto.BlogPostDto;
import club.benjifa.benjifa_backend_api.blog.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    @Override
    public List<BlogPostDto> getAllPosts(Pageable pageable) {

        return blogPostRepository.findAll(pageable).stream()
                .map(this::fromEntity)
                .toList();
    }

    @Override
    public List<BlogPostDto> getPostsByAuthor(String author, Pageable pageable) {
        return blogPostRepository.findAllByAuthor(author, pageable).stream()
                .map(this::fromEntity)
                .toList();
    }

    @Override
    public BlogPostDto getPostById(Long id) {
        return blogPostRepository.findById(id)
                .map(this::fromEntity)
                .orElse(null);
    }
    @Override
    public BlogPostDto createPost(BlogPostDto blogPostDto) {
        BlogPost blogPost = toEntity(blogPostDto);
        blogPost.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        blogPost.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
        blogPost.setViewCount(0);

        return fromEntity(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostDto updatePost(BlogPostDto blogPostDto) {
        return blogPostRepository.findById(blogPostDto.getId())
                .map(blogPost -> {
                    BlogPost updatedBlogPost = toEntity(blogPostDto);
                    updatedBlogPost.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
                    return fromEntity(blogPostRepository.save(blogPost));
                })
                .orElse(null);
    }
    @Override
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }


    @Override
    public BlogPostDto fromEntity(BlogPost blogPost) {
        return BlogPostDto.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .author(blogPost.getAuthor())
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .tags(blogPost.getTags())
                .viewCount(blogPost.getViewCount())
                .comments(blogPost.getComments().stream()
                        .map(comment -> BlogCommentDto.builder()
                                .id(comment.getId())
                                .content(comment.getContent())
                                .author(comment.getAuthor())
                                .createdAt(comment.getCreatedAt())
                                .build())
                        .sorted((comment1, comment2) -> comment2.getCreatedAt().compareTo(comment1.getCreatedAt()))
                        .toList())
                .build();
    }

    @Override
    public BlogPost toEntity(BlogPostDto blogPostDto) {
        return BlogPost.builder()
                .title(blogPostDto.getTitle())
                .content(blogPostDto.getContent())
                .author(blogPostDto.getAuthor())
                .createdAt(blogPostDto.getCreatedAt())
                .updatedAt(blogPostDto.getUpdatedAt())
                .tags(blogPostDto.getTags())
                .viewCount(blogPostDto.getViewCount())
                .build();
    }
}
