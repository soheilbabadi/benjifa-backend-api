package club.benjifa.benjifa_backend_api.ask.service;

import club.benjifa.benjifa_backend_api.ask.dto.AnswerDto;
import club.benjifa.benjifa_backend_api.ask.dto.QuestionDto;
import club.benjifa.benjifa_backend_api.ask.model.Question;
import club.benjifa.benjifa_backend_api.ask.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl {

    private final QuestionRepository questionRepository;


    QuestionDto getQuestionById(Long id) {
        return questionRepository.findById(id)
                .map(this::fromModel)
                .orElse(null);
    }

    private QuestionDto fromModel(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .content(question.getContent())
                .author(question.getAuthor())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .petType(question.getPetType())
                .questionType(question.getQuestionType())
                .resolved(question.isResolved())
                .tags(question.getTags())
                .title(question.getTitle())
                .viewCount(question.getViewCount())
                .answers(question.getAnswers().stream()
                        .map(answer -> AnswerDto.builder()
                                .id(answer.getId())
                                .content(answer.getContent())
                                .author(answer.getAuthor())
                                .createdAt(answer.getCreatedAt())
                                .updatedAt(answer.getUpdatedAt())
                                .accepted(answer.isAccepted())
                                .upvotes(answer.getUpvotes())
                                .build())
                        .toList())

                .build();
    }
}
