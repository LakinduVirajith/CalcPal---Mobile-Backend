package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;
import com.calcpal.lexicaldiagnosisservice.collection.LexicalQuestion;
import com.calcpal.lexicaldiagnosisservice.repository.LexicalQuestionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LexicalQuestionServiceImpl implements LexicalQuestionService{

    private final LexicalQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(LexicalQuestionDTO questionDTO) {
        LexicalQuestion question =  LexicalQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("Question has been successfully added");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<LexicalQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found on the server for the provided question number.");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        LexicalQuestion randomQuestion = getRandomQuestion(questions);

        // MAPPING QUESTION DATA
        LexicalQuestionDTO question = LexicalQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private LexicalQuestion getRandomQuestion(List<LexicalQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LexicalQuestion> pagedQuestions = questionBankRepository.findAll(pageable);

        // NOT FOUND EXCEPTION HANDLE
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, LexicalQuestionDTO questionDTO) {
        Optional<LexicalQuestion> optionalLexicalQuestion = questionBankRepository.findById(id);
        
        // NOT FOUND EXCEPTION HANDLE
        if (optionalLexicalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }
        LexicalQuestion question = optionalLexicalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(questionDTO.getAnswers());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("Questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<LexicalQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Questions deleted successfully");
    }
}
