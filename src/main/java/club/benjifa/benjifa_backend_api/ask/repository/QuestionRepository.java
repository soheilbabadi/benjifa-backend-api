package club.benjifa.benjifa_backend_api.ask.repository;

import club.benjifa.benjifa_backend_api.ask.dto.PetType;
import club.benjifa.benjifa_backend_api.ask.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByAuthor(String authorId);
    List<Question> findAllByPetTypeOrderByIdDesc(PetType petType);
    List<Question> findAllByQuestionTypeOrderByIdDesc(String questionType);

}
