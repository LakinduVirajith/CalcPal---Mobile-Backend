package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticQuestion;
import com.calcpal.ideognosticdiagnosisservice.repository.IdeognosticQuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IdeognosticQuestionServiceImpl implements IdeognosticQuestionService {

    private final IdeognosticQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(@Valid IdeognosticQuestionDTO questionDTO) {
        IdeognosticQuestion question =  IdeognosticQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("Ideognostic question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<IdeognosticQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        IdeognosticQuestion randomQuestion = getRandomQuestion(questions);

        // MAPPING QUESTION DATA
        IdeognosticQuestionDTO question = IdeognosticQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private IdeognosticQuestion getRandomQuestion(List<IdeognosticQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<IdeognosticQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, @Valid IdeognosticQuestionDTO questionDTO) {
        Optional<IdeognosticQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the provided ID");
        }
        IdeognosticQuestion question = optionalVerbalQuestion.get();

        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("Question updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<IdeognosticQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Question deleted successfully");
    }
}
