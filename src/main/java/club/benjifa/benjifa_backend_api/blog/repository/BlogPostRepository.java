package club.benjifa.benjifa_backend_api.blog.repository;

import club.benjifa.benjifa_backend_api.blog.domain.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {

    Page<BlogPost> findAllByAuthor(String author, Pageable pageable);

}
