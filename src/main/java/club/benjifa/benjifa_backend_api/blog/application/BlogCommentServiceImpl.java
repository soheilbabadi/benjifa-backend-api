package club.benjifa.benjifa_backend_api.blog.application;

import club.benjifa.benjifa_backend_api.blog.domain.BlogComment;
import club.benjifa.benjifa_backend_api.blog.domain.BlogPost;
import club.benjifa.benjifa_backend_api.blog.dto.BlogCommentDto;
import club.benjifa.benjifa_backend_api.blog.repository.BlogCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {

    private final BlogCommentRepository blogCommentRepository;

    @Override
    public void deleteAllByPostId(Long postId) {
        blogCommentRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteById(Long id) {
        blogCommentRepository.deleteById(id);
    }

    @Override
    public List<BlogCommentDto> getAllByPostId(Long postId) {
        return blogCommentRepository.findAllByPostIdOrderByCreatedAt(postId).stream()
                .map(this::fromEntity)
                .toList();
    }

    @Override
    public BlogCommentDto createComment(BlogCommentDto blogCommentDto) {
        return fromEntity(blogCommentRepository.save(toEntity(blogCommentDto)));
    }

    @Override
    public BlogCommentDto updateComment(BlogCommentDto blogCommentDto) {
        return fromEntity(blogCommentRepository.save(toEntity(blogCommentDto)));
    }

    @Override
    public BlogCommentDto fromEntity(BlogComment blogComment) {
        var blogCommentDto = new BlogCommentDto();
        blogCommentDto.setId(blogComment.getId());
        blogCommentDto.setPostId(blogComment.getPost().getId());
        blogCommentDto.setAuthor(blogComment.getAuthor());
        blogCommentDto.setContent(blogComment.getContent());
        blogCommentDto.setCreatedAt(blogComment.getCreatedAt());
        return blogCommentDto;
    }

    @Override
    public BlogComment toEntity(BlogCommentDto blogCommentDto) {
        var post = new BlogPost();
        post.setId(blogCommentDto.getPostId());

        var blogComment = new BlogComment();
        blogComment.setId(blogCommentDto.getId());
        blogComment.setPost(post);
        blogComment.setAuthor(blogCommentDto.getAuthor());
        blogComment.setContent(blogCommentDto.getContent());
        return blogComment;
    }

}
