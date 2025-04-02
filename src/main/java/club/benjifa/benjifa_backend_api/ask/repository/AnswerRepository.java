package club.benjifa.benjifa_backend_api.ask.repository;

import club.benjifa.benjifa_backend_api.ask.model.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Answer findByIdAndAuthor(Long id, String authorId);

    Answer findByQuestionId(Long questionId);

    void deleteByQuestionId(Long questionId);
}
