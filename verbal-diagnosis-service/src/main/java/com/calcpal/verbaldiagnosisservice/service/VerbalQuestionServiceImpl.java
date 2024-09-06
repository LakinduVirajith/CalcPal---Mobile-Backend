package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalQuestion;
import com.calcpal.verbaldiagnosisservice.enums.Language;
import com.calcpal.verbaldiagnosisservice.repository.VerbalQuestionRepository;
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
public class VerbalQuestionServiceImpl implements VerbalQuestionService {

    private final VerbalQuestionRepository questionBankRepository;
    @Override
    public ResponseEntity<?> add(VerbalQuestionDTO questionBankDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionBankDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionBankDTO.getLanguage());
        }

        // BUILD A LEXICAL ACTIVITY OBJECT AND SAVE IT
        VerbalQuestion question = buildVerbalQuestion(questionBankDTO);
        questionBankRepository.save(question);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Question has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<VerbalQuestionDTO> questionBankDTOList) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (questionBankDTOList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 questions at once.");
        }

        // VALIDATE EACH ACTIVITY-DTO AND BUILD VERBAL QUESTION OBJECTS
        List<VerbalQuestion> questions = questionBankDTOList.stream()
                .map(dto -> {
                    if (!isValidLanguage(dto.getLanguage())) {
                        throw new IllegalArgumentException("Invalid language: " + dto.getLanguage());
                    }
                    return buildVerbalQuestion(dto);
                })
                .collect(Collectors.toList());

        // SAVE ALL THE ACTIVITIES TO THE REPOSITORY
        questionBankRepository.saveAll(questions);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("All questions have been successfully added.");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id, String language) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(language)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + language);
        }

        // FETCH ACTIVITY BY ACTIVITY NUMBER AND FILTER BY LANGUAGE
        List<VerbalQuestion> questions = questionBankRepository.findByQuestionNumber(id);
        List<VerbalQuestion> filteredQuestions = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // NOT FOUND EXCEPTION HANDLE
        if (filteredQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found on the server for the provided question number.");
        }

        // SELECT A RANDOM QUESTION FROM THE FILTERED LIST
        VerbalQuestion randomQuestion = getRandomQuestion(filteredQuestions);
        VerbalQuestionDTO questionDTO = mapToDTO(randomQuestion);

        // RETURN SUCCESS RESPONSE WITH RANDOM QUESTION
        return ResponseEntity.ok().body(questionDTO);
    }


    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VerbalQuestion> pagedQuestions = questionBankRepository.findAll(pageable);

        // NOT FOUND EXCEPTION HANDLE
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, VerbalQuestionDTO questionBankDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionBankDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionBankDTO.getLanguage());
        }

        // FETCH EXISTING QUESTION BY ID
        Optional<VerbalQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }

        // UPDATE EXISTING ACTIVITY WITH NEW DATA
        VerbalQuestion question = optionalVerbalQuestion.get();
        updateQuestionFromDTO(question, questionBankDTO);
        questionBankRepository.save(question);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok().body("Questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<VerbalQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Questions deleted successfully");
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

    // BUILD A VERBAL QUESTION OBJECT FROM DTO
    private VerbalQuestion buildVerbalQuestion(VerbalQuestionDTO dto) {
        VerbalQuestion question = VerbalQuestion.builder()
                .questionNumber(dto.getQuestionNumber())
                .language(dto.getLanguage())
                .question(dto.getQuestion())
                .answers(dto.getAnswers())
                .correctAnswer(dto.getCorrectAnswer())
                .build();

        encodeToBase64BasedOnLanguage(dto, question);

        return question;
    }

    // UPDATE EXISTING QUESTION ACTIVITY FROM DTO
    private void updateQuestionFromDTO(VerbalQuestion question, VerbalQuestionDTO dto) {
        question.setLanguage(dto.getLanguage());
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setCorrectAnswer(dto.getCorrectAnswer());

        encodeToBase64BasedOnLanguage(dto, question);
    }

    // MAP VERBAL QUESTION TO DTO
    private VerbalQuestionDTO mapToDTO(VerbalQuestion question) {
        return VerbalQuestionDTO.builder()
                .questionNumber(question.getQuestionNumber())
                .language(question.getLanguage())
                .question(question.getQuestion())
                .answers(question.getAnswers())
                .correctAnswer(question.getCorrectAnswer())
                .build();
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private VerbalQuestion getRandomQuestion(List<VerbalQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    private void encodeToBase64BasedOnLanguage(VerbalQuestionDTO dto, VerbalQuestion question) {
        if(!dto.getLanguage().name().equals(Language.English.name())){
            question.setQuestion(encodeToBase64(dto.getQuestion()));
        }else{
            question.setQuestion(dto.getQuestion());
        }
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
