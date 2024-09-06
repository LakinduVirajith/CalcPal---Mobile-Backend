package com.calcpal.iqtestservice.service;


import com.calcpal.iqtestservice.DTO.IQQuestionDTO;
import com.calcpal.iqtestservice.collection.IQQuestion;
import com.calcpal.iqtestservice.enums.Language;
import com.calcpal.iqtestservice.repository.IQQuestionRepository;
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
public class IQQuestionServiceImpl implements IQQuestionService{

    private final IQQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(IQQuestionDTO questionDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionDTO.getLanguage());
        }

        // BUILD A LEXICAL ACTIVITY OBJECT AND SAVE IT
        IQQuestion question = buildIQQuestion(questionDTO);
        questionBankRepository.save(question);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Question has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<IQQuestionDTO> questionDTOList) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (questionDTOList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 questions at once.");
        }

        // VALIDATE EACH QUESTION-DTO AND BUILD VERBAL QUESTION OBJECTS
        List<IQQuestion> questions = questionDTOList.stream()
                .map(dto -> {
                    if (!isValidLanguage(dto.getLanguage())) {
                        throw new IllegalArgumentException("Invalid language: " + dto.getLanguage());
                    }
                    return buildIQQuestion(dto);
                })
                .collect(Collectors.toList());

        // SAVE ALL THE QUESTIONS TO THE REPOSITORY
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

        // FETCH QUESTION BY QUESTION NUMBER AND FILTER BY LANGUAGE
        List<IQQuestion> questions = questionBankRepository.findByQuestionNumber(id);
        List<IQQuestion> filteredQuestions = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // NOT FOUND EXCEPTION HANDLE
        if (filteredQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found on the server for the provided question number.");
        }

        // SELECT A RANDOM QUESTION FROM THE FILTERED LIST
        IQQuestion randomQuestion = getRandomQuestion(filteredQuestions);
        IQQuestionDTO questionDTO = mapToDTO(randomQuestion);

        // RETURN SUCCESS RESPONSE WITH RANDOM QUESTION
        return ResponseEntity.ok().body(questionDTO);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IQQuestion> pagedQuestions = questionBankRepository.findAll(pageable);

        // NOT FOUND EXCEPTION HANDLE
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, IQQuestionDTO questionBankDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(questionBankDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + questionBankDTO.getLanguage());
        }

        // FETCH EXISTING QUESTION BY ID
        Optional<IQQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }

        // UPDATE EXISTING QUESTION WITH NEW DATA
        IQQuestion question = optionalVerbalQuestion.get();
        updateQuestionFromDTO(question, questionBankDTO);
        questionBankRepository.save(question);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok().body("Questions updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<IQQuestion> question = questionBankRepository.findById(id);

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
    private IQQuestion buildIQQuestion(IQQuestionDTO dto) {
        IQQuestion question = IQQuestion.builder()
                .questionNumber(dto.getQuestionNumber())
                .language(dto.getLanguage())
                .build();

        encodeToBase64BasedOnLanguage(dto, question);
        return question;
    }

    // UPDATE EXISTING QUESTION
    private void updateQuestionFromDTO(IQQuestion question, IQQuestionDTO dto) {
        question.setLanguage(dto.getLanguage());
        encodeToBase64BasedOnLanguage(dto, question);
    }

    // MAP VERBAL QUESTION TO DTO
    private IQQuestionDTO mapToDTO(IQQuestion question) {
        return IQQuestionDTO.builder()
                .questionNumber(question.getQuestionNumber())
                .language(question.getLanguage())
                .question(question.getQuestion())
                .answers(question.getAnswers())
                .correctAnswer(question.getCorrectAnswer())
                .build();
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private IQQuestion getRandomQuestion(List<IQQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    private void encodeToBase64BasedOnLanguage(IQQuestionDTO dto, IQQuestion question) {
        if(!dto.getLanguage().name().equals(Language.English.name())){
            question.setQuestion(encodeToBase64(dto.getQuestion()));
            question.setAnswers(encodeListToBase64(dto.getAnswers()));
            question.setCorrectAnswer(encodeToBase64(dto.getCorrectAnswer()));
        }else{
            question.setQuestion(dto.getQuestion());
            question.setAnswers(dto.getAnswers());
            question.setCorrectAnswer(dto.getCorrectAnswer());
        }
    }

    private List<String> encodeListToBase64(List<String> answers) {
        return answers.stream()
                .map(answer -> Base64.getEncoder().encodeToString(answer.getBytes()))
                .collect(Collectors.toList());
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
