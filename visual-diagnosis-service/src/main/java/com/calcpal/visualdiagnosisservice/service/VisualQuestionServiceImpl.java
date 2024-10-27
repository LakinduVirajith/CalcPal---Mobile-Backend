package com.calcpal.visualdiagnosisservice.service;

import com.calcpal.visualdiagnosisservice.DTO.VisualQuestionDTO;
import com.calcpal.visualdiagnosisservice.collection.VisualQuestion;
import com.calcpal.visualdiagnosisservice.enums.Language;
import com.calcpal.visualdiagnosisservice.repository.VisualQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VisualQuestionServiceImpl implements VisualQuestionService {

    private final VisualQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(VisualQuestionDTO questionDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionDTO.getLanguage());
        }

        VisualQuestion question =  VisualQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id, String language) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(language)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + language);
        }

        List<VisualQuestion> questions = questionBankRepository.findByQuestionNumber(id);
        List<VisualQuestion> filteredQuestions = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .toList();

        // NOT FOUND EXCEPTION HANDLE
        if (filteredQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        VisualQuestion randomQuestion = getRandomQuestion(filteredQuestions);

        // MAPPING QUESTION DATA
        VisualQuestionDTO question = VisualQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private VisualQuestion getRandomQuestion(List<VisualQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<VisualQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, VisualQuestionDTO questionDTO) {
        Optional<VisualQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }
        VisualQuestion question = optionalVerbalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(questionDTO.getAnswers());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<VisualQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("questions deleted successfully");
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
