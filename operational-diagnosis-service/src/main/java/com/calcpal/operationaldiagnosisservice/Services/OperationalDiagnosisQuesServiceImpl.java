package com.calcpal.operationaldiagnosisservice.Services;

import com.calcpal.operationaldiagnosisservice.Collections.OperationalDiagnosisQues;
import com.calcpal.operationaldiagnosisservice.DTO.QuestionDTO;
import com.calcpal.operationaldiagnosisservice.Repositary.OperationalDiagnosisQuesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OperationalDiagnosisQuesServiceImpl implements OperationalDiagnosisQuesService {

    private final OperationalDiagnosisQuesRepo questionBankRepository;

    @Override
    public ResponseEntity<?> add(QuestionDTO questionDTO) {
        OperationalDiagnosisQues question =  OperationalDiagnosisQues.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .incorrectAnswer1(questionDTO.getIncorrectAnswer1())
                .incorrectAnswer2(questionDTO.getIncorrectAnswer2())
                .build();

        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("New Operational diagnostic question inserted successfully");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<OperationalDiagnosisQues> questions = questionBankRepository.findByQuestionNumber(id);

        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no questions found for the given question number");
        }

        OperationalDiagnosisQues randomQuestion = getRandomQuestion(questions);

        QuestionDTO question = QuestionDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .incorrectAnswer1(randomQuestion.getIncorrectAnswer1())
                .incorrectAnswer2(randomQuestion.getIncorrectAnswer2())
                .build();

        return ResponseEntity.ok().body(question);
    }

    private OperationalDiagnosisQues getRandomQuestion(List<OperationalDiagnosisQues> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<OperationalDiagnosisQues> questions = questionBankRepository.findAll();

        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, QuestionDTO questionDTO) {
        Optional<OperationalDiagnosisQues> optionalVerbalQuestion = questionBankRepository.findById(id);

        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No operational questions found for the provided ID");
        }
        OperationalDiagnosisQues question = optionalVerbalQuestion.get();

        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setIncorrectAnswer1(questionDTO.getIncorrectAnswer1());
        question.setIncorrectAnswer2(questionDTO.getIncorrectAnswer2());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("Operational Question updated successfully!");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<OperationalDiagnosisQues> question = questionBankRepository.findById(id);

        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No operational questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Question deleted successfully!");
    }
}
