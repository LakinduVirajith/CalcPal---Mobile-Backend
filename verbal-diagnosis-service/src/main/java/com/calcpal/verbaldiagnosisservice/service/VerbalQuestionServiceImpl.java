package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalQuestion;
import com.calcpal.verbaldiagnosisservice.repository.VerbalQuestionRepository;
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
import java.util.stream.Collectors;

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

        return ResponseEntity.status(HttpStatus.CREATED).body("Question Inserted Successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id, String language) {
        List<VerbalQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // Filter questions by language
        List<VerbalQuestion> filteredQuestions = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // NOT FOUND EXCEPTION HANDLE
        if (filteredQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        VerbalQuestion randomQuestion = getRandomQuestion(filteredQuestions);

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
        Optional<VerbalQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the provided ID");
        }
        VerbalQuestion question = optionalVerbalQuestion.get();

        // MAPPING QUESTION DATA
        question.setQuestionNumber(questionBankDTO.getQuestionNumber());
        question.setLanguage(questionBankDTO.getLanguage());
        question.setQuestion(questionBankDTO.getQuestion());
        question.setAnswers(questionBankDTO.getAnswers());
        question.setCorrectAnswer(questionBankDTO.getCorrectAnswer());

        questionBankRepository.save(question);

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
}
