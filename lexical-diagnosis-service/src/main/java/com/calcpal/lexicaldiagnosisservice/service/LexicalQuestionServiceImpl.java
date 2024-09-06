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

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LexicalQuestionServiceImpl implements LexicalQuestionService{

    private final LexicalQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(LexicalQuestionDTO questionDTO) {
        // BUILD A LEXICAL ACTIVITY OBJECT AND SAVE IT
        LexicalQuestion question = buildLexicalQuestion(questionDTO);
        questionBankRepository.save(question);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Question has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<LexicalQuestionDTO> questionDTOS) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (questionDTOS.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 questions at once.");
        }

        // BUILD LEXICAL QUESTION OBJECTS
        List<LexicalQuestion> questions = questionDTOS.stream()
                .map(this::buildLexicalQuestion)
                .collect(Collectors.toList());

        // SAVE ALL THE ACTIVITIES TO THE REPOSITORY
        questionBankRepository.saveAll(questions);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("All questions have been successfully added.");
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
        LexicalQuestionDTO questionDTO = mapToDTO(randomQuestion);

        return ResponseEntity.ok().body(questionDTO);
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
        encodeToBase64BasedAnswers(question);

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

    // BUILD A LEXICAL QUESTION OBJECT FROM DTO
    private LexicalQuestion buildLexicalQuestion(LexicalQuestionDTO dto) {
        LexicalQuestion question = LexicalQuestion.builder()
                .questionNumber(dto.getQuestionNumber())
                .question(dto.getQuestion())
                .answers(dto.getAnswers())
                .build();

        encodeToBase64BasedAnswers(question);

        return question;
    }

    // MAP VERBAL ACTIVITY TO DTO
    private LexicalQuestionDTO mapToDTO(LexicalQuestion question) {
        return LexicalQuestionDTO.builder()
                .questionNumber(question.getQuestionNumber())
                .question(question.getQuestion())
                .answers(question.getAnswers())
                .build();
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private LexicalQuestion getRandomQuestion(List<LexicalQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    private void encodeToBase64BasedAnswers(LexicalQuestion question) {
        if(question.getAnswers() != null && !question.getAnswers().isEmpty()){
            List<String> answers = question.getAnswers();

            // ENCODE ONLY 3ND (INDEX 2) AND 4RD (INDEX 3) ANSWERS
            if (answers.size() > 2) {
                answers.set(2, encodeToBase64(answers.get(2)));
            }
            if (answers.size() > 3) {
                answers.set(3, encodeToBase64(answers.get(3)));
            }

            // UPDATE THE DTO WITH THE MODIFIED ANSWER LIST
            question.setAnswers(answers);
        }
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
