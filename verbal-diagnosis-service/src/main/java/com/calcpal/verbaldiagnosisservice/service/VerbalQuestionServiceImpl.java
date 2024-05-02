package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalQuestion;
import com.calcpal.verbaldiagnosisservice.repository.VerbalQuestionRepository;
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
public class VerbalQuestionServiceImpl implements VerbalQuestionService {

    private final VerbalQuestionRepository questionBankRepository;
    @Override
    public ResponseEntity<?> add(VerbalQuestionDTO questionBankDTO) {
        VerbalQuestion question =  VerbalQuestion.builder()
                .questionNumber(questionBankDTO.getQuestionNumber())
                .language(questionBankDTO.getLanguage())
                .question(questionBankDTO.getQuestion())
                .answers(questionBankDTO.getAnswers())
                .correctAnswer(questionBankDTO.getCorrectAnswer())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<VerbalQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        VerbalQuestion randomQuestion = getRandomQuestion(questions);

        // MAPPING QUESTION DATA
        VerbalQuestionDTO question = VerbalQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private VerbalQuestion getRandomQuestion(List<VerbalQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<VerbalQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, VerbalQuestionDTO questionBankDTO) {
        Optional<VerbalQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }
        VerbalQuestion question = optionalVerbalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionBankDTO.getQuestionNumber());
        question.setLanguage(questionBankDTO.getLanguage());
        question.setQuestion(questionBankDTO.getQuestion());
        question.setAnswers(questionBankDTO.getAnswers());
        question.setCorrectAnswer(questionBankDTO.getCorrectAnswer());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<VerbalQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("questions deleted successfully");
    }
}
