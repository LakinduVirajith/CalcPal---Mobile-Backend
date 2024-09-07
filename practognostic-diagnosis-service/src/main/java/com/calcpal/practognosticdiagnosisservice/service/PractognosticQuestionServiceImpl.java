package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticQuestionDTO;
import com.calcpal.practognosticdiagnosisservice.collection.DiagnosisQuestionPractognostic;
import com.calcpal.practognosticdiagnosisservice.repository.PractognosticQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PractognosticQuestionServiceImpl implements PractognosticQuestionService {

    private final PractognosticQuestionRepository practognosticQuestionRepository;

    @Override
    public ResponseEntity<?> add(PractognosticQuestionDTO questionDTO) {
        DiagnosisQuestionPractognostic question = DiagnosisQuestionPractognostic.builder()
                    .questionNumber(questionDTO.getQuestionNumber())
                    .language(questionDTO.getLanguage())
                    .question(questionDTO.getQuestion())
                    .answers(questionDTO.getAnswers())
                    .correctAnswer(questionDTO.getCorrectAnswer())
                    .build();
        if(!questionDTO.getQuestionText().isEmpty()){
            question.setQuestionText(questionDTO.getQuestionText());
        }
        if(!questionDTO.getImageType().isEmpty()){
            question.setImageType(questionDTO.getImageType());
        }

        practognosticQuestionRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<DiagnosisQuestionPractognostic> questions = practognosticQuestionRepository.findByQuestionNumber(id);

        //Not found exception handle
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        //Randomly select one question form the fetched list
        DiagnosisQuestionPractognostic randomQuestion = getRandomQuestion(questions);

        //Mapping question data
        PractognosticQuestionDTO question = PractognosticQuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .answers(randomQuestion.getAnswers())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .build();

        if(!randomQuestion.getQuestionText().isEmpty()){
            question.setQuestionText(randomQuestion.getQuestionText());
        }
        if(!randomQuestion.getImageType().isEmpty()){
            question.setImageType(randomQuestion.getImageType());
        }

        return ResponseEntity.ok().body(question);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DiagnosisQuestionPractognostic> questions = practognosticQuestionRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, PractognosticQuestionDTO questionDTO) {
        Optional<DiagnosisQuestionPractognostic> optionalVerbalQuestion = practognosticQuestionRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }
        DiagnosisQuestionPractognostic question = optionalVerbalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        if(!questionDTO.getQuestionText().isEmpty()){
            question.setQuestionText(questionDTO.getQuestionText());
        }
        if(!questionDTO.getImageType().isEmpty()){
            question.setImageType(questionDTO.getImageType());
        }
        question.setAnswers(questionDTO.getAnswers());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        practognosticQuestionRepository.save(question);

        return ResponseEntity.ok().body("questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<DiagnosisQuestionPractognostic> question = practognosticQuestionRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the provided ID");
        }

        practognosticQuestionRepository.deleteById(id);

        return ResponseEntity.ok().body("questions deleted successfully");
    }

    private DiagnosisQuestionPractognostic getRandomQuestion(List<DiagnosisQuestionPractognostic> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }
}