package club.benjifa.benjifa_backend_api.blog.repository;

import club.benjifa.benjifa_backend_api.blog.domain.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    List<BlogComment> findAllByPostIdOrderByCreatedAt(Long postId);

    void deleteAllByPostId(Long postId);


}
