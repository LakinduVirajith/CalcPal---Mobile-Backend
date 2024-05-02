package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalQuestionDTO;
import com.calcpal.lexicaldiagnosisservice.collection.LexicalQuestion;
import com.calcpal.lexicaldiagnosisservice.repository.LexicalQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<LexicalQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        LexicalQuestion randomQuestion = getRandomQuestion(questions);

        // MAPPING QUESTION DATA
        LexicalQuestionDTO question = LexicalQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .correctAnswer(randomQuestion.getCorrectAnswer())
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
    public ResponseEntity<?> getAll() {
        List<LexicalQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(ObjectId id, LexicalQuestionDTO questionDTO) {
        Optional<LexicalQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }
        LexicalQuestion question = optionalVerbalQuestion.get();

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
    public ResponseEntity<?> delete(ObjectId id) {
        Optional<LexicalQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("questions deleted successfully");
    }
}
