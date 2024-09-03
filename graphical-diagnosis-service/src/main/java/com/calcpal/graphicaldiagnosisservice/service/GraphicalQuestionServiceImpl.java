package com.calcpal.graphicaldiagnosisservice.service;

import com.calcpal.graphicaldiagnosisservice.DTO.GraphicalQuestionDTO;
import com.calcpal.graphicaldiagnosisservice.collection.GraphicalQuestion;
import com.calcpal.graphicaldiagnosisservice.repository.GraphicalQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GraphicalQuestionServiceImpl implements GraphicalQuestionService {

    private final GraphicalQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(GraphicalQuestionDTO questionDTO) {
        GraphicalQuestion question =  GraphicalQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<GraphicalQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        GraphicalQuestion randomQuestion = getRandomQuestion(questions);

        // MAPPING QUESTION DATA
        GraphicalQuestionDTO question = GraphicalQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private GraphicalQuestion getRandomQuestion(List<GraphicalQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<GraphicalQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, GraphicalQuestionDTO questionDTO) {
        Optional<GraphicalQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }
        GraphicalQuestion question = optionalVerbalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(questionDTO.getAnswers());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<GraphicalQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("questions deleted successfully");
    }
}
