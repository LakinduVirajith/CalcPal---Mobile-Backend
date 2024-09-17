package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionResponseDTO;
import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionUploadDTO;
import com.calcpal.ideognosticdiagnosisservice.collection.IdeognosticQuestion;
import com.calcpal.ideognosticdiagnosisservice.repository.IdeognosticQuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IdeognosticQuestionServiceImpl implements IdeognosticQuestionService {

    private final IdeognosticQuestionRepository questionBankRepository;

    @Override
    public ResponseEntity<?> add(@Valid IdeognosticQuestionUploadDTO questionDTO) {

        byte[] imageBytes = null;

        if (questionDTO.getImage() != null && !questionDTO.getImage().isEmpty()) {
            try {
                imageBytes = questionDTO.getImage().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process image");
            }
        }

        IdeognosticQuestion question = IdeognosticQuestion.builder()
                .questionNumber(questionDTO.getQuestionNumber())
                .language(questionDTO.getLanguage())
                .question(questionDTO.getQuestion())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .allAnswers(questionDTO.getAllAnswers())
                .image(imageBytes)  // Store image as byte array
                .build();

        // Save to the repository
        questionBankRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body("Ideognostic question inserted successfully");
    }

    // Helper method to convert MultipartFile to Base64 String
    private String encodeImageToBase64(MultipartFile image) throws IOException {
        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    @Override
    public ResponseEntity<?> getRandom(Long id) {
        List<IdeognosticQuestion> questions = questionBankRepository.findByQuestionNumber(id);

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the given question number");
        }

        // RANDOMLY SELECT ONE QUESTION FORM THE FETCHED LIST
        IdeognosticQuestion randomQuestion = getRandomQuestion(questions);

        // Convert byte array to Base64 string
        String base64Image = randomQuestion.getImage() != null ? Base64.getEncoder().encodeToString(randomQuestion.getImage()) : null;

        IdeognosticQuestionResponseDTO question = IdeognosticQuestionResponseDTO.builder()
                .questionNumber(randomQuestion.getQuestionNumber())
                .language(randomQuestion.getLanguage())
                .question(randomQuestion.getQuestion())
                .correctAnswer(randomQuestion.getCorrectAnswer())
                .allAnswers(randomQuestion.getAllAnswers())
                .image(base64Image)  // Include Base64 image string in response
                .build();

        return ResponseEntity.ok().body(question);
    }

    // METHOD TO GET A RANDOM QUESTION FROM A LIST QUESTIONS
    private IdeognosticQuestion getRandomQuestion(List<IdeognosticQuestion> questions) {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<IdeognosticQuestion> questions = questionBankRepository.findAll();

        // NOT FOUND EXCEPTION HANDLE
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions are currently available in the collection");
        }

        return ResponseEntity.ok().body(questions);
    }

    @Override
    public ResponseEntity<?> update(String id, @Valid IdeognosticQuestionUploadDTO questionDTO) {
        Optional<IdeognosticQuestion> optionalVerbalQuestion = questionBankRepository.findById(id);

        if (optionalVerbalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the provided ID");
        }
        IdeognosticQuestion question = optionalVerbalQuestion.get();

        question.setQuestionNumber(questionDTO.getQuestionNumber());
        question.setLanguage(questionDTO.getLanguage());
        question.setQuestion(questionDTO.getQuestion());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setAllAnswers(questionDTO.getAllAnswers());

        questionBankRepository.save(question);

        return ResponseEntity.ok().body("Question updated successfully");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<IdeognosticQuestion> question = questionBankRepository.findById(id);

        // NOT FOUND EXCEPTION HANDLE
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Ideognostic Questions found for the provided ID");
        }

        questionBankRepository.deleteById(id);

        return ResponseEntity.ok().body("Question deleted successfully");
    }
}
