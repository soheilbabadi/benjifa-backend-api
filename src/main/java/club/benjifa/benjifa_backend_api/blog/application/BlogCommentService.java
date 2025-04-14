package club.benjifa.benjifa_backend_api.blog.application;

import club.benjifa.benjifa_backend_api.blog.domain.BlogComment;
import club.benjifa.benjifa_backend_api.blog.dto.BlogCommentDto;

import java.util.List;

public interface BlogCommentService {
    void deleteAllByPostId(Long postId);

    void deleteById(Long id);

    List<BlogCommentDto> getAllByPostId(Long postId);

    BlogCommentDto createComment(BlogCommentDto blogCommentDto);

    BlogCommentDto updateComment(BlogCommentDto blogCommentDto);

    BlogCommentDto fromEntity(BlogComment blogComment);

    BlogComment toEntity(BlogCommentDto blogCommentDto);
}
