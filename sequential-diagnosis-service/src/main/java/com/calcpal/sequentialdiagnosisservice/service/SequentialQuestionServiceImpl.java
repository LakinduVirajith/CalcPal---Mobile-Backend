package com.calcpal.sequentialdiagnosisservice.service;

import com.calcpal.sequentialdiagnosisservice.DTO.SequentialQuestionDTO;
import com.calcpal.sequentialdiagnosisservice.collection.SequentialQuestion;
import com.calcpal.sequentialdiagnosisservice.enums.Language;
import com.calcpal.sequentialdiagnosisservice.repository.SequentialQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SequentialQuestionServiceImpl implements SequentialQuestionService{

    private final SequentialQuestionRepository quectionBankRepository;

    @Override
    public ResponseEntity<?> add(SequentialQuestionDTO questionDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionDTO.getLanguage());
        }

        SequentialQuestion question = SequentialQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .build();

        quectionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("Question added successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id, String language) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(language)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + language);
        }

        List<SequentialQuestion> questions = quectionBankRepository.findByQuestionNumber(id);
        List<SequentialQuestion> filteredQuestions = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .toList();

        if (filteredQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }

        SequentialQuestion randomQuestion = getRandomQuestion(filteredQuestions);

        SequentialQuestionDTO question = SequentialQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .build();

        return ResponseEntity.ok().body(question);
    }

    private SequentialQuestion getRandomQuestion(List<SequentialQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<SequentialQuestion> questions = quectionBankRepository.findAll();

        if (questions.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found in Collection");
        }
        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, SequentialQuestionDTO questionDTO) {
        Optional<SequentialQuestion> optionalSequentialQuestion = quectionBankRepository.findById(id);
        if (optionalSequentialQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found for given id");
        }
        SequentialQuestion question = optionalSequentialQuestion.get();

        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(questionDTO.getAnswers());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        quectionBankRepository.save(question);

        return ResponseEntity.ok().body("Question updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<SequentialQuestion> question = quectionBankRepository.findById(id);

        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found for given id");

        }
        quectionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Question deleted successfully");
    }

    // VALIDATE LANGUAGE AGAINST ENUM VALUES
    private boolean isValidLanguage(Object language) {
        try {
            Language.valueOf(language.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
