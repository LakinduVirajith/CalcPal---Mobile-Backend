package com.calcpal.lexicaldiagnosisservice.service;

import com.calcpal.lexicaldiagnosisservice.DTO.LexicalActivityDTO;
import com.calcpal.lexicaldiagnosisservice.collection.LexicalActivity;
import com.calcpal.lexicaldiagnosisservice.enums.Language;
import com.calcpal.lexicaldiagnosisservice.repository.LexicalActivityRepository;
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
public class LexicalActivityServiceImpl implements LexicalActivityService{

    private final LexicalActivityRepository activityBankRepository;

    @Override
    public ResponseEntity<?> add(LexicalActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + activityDTO.getLanguage());
        }

        // BUILD A LEXICAL ACTIVITY OBJECT AND SAVE IT
        LexicalActivity activity = buildLexicalActivity(activityDTO);
        activityBankRepository.save(activity);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<LexicalActivityDTO> activityDTOList) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (activityDTOList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 activities at once.");
        }

        // VALIDATE EACH ACTIVITY-DTO AND BUILD LEXICAL ACTIVITY OBJECTS
        List<LexicalActivity> activities = activityDTOList.stream()
                .map(dto -> {
                    if (!isValidLanguage(dto.getLanguage())) {
                        throw new IllegalArgumentException("Invalid language: " + dto.getLanguage());
                    }
                    return buildLexicalActivity(dto);
                })
                .collect(Collectors.toList());

        // SAVE ALL THE ACTIVITIES TO THE REPOSITORY
        activityBankRepository.saveAll(activities);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("All activities have been successfully added.");
    }

    @Override
    public ResponseEntity<?> getRandom(Long id, String language) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(language)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + language);
        }

        // FETCH ACTIVITY BY QUESTION NUMBER AND FILTER BY LANGUAGE
        List<LexicalActivity> questions = activityBankRepository.findByActivityNumber(id);
        List<LexicalActivity> filteredActivities = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (filteredActivities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found on the server for the provided activity number.");
        }

        // SELECT A RANDOM ACTIVITY FROM THE FILTERED LIST
        LexicalActivity randomActivity = getRandomActivity(filteredActivities);
        LexicalActivityDTO questionDTO = mapToDTO(randomActivity);

        // RETURN SUCCESS RESPONSE WITH RANDOM ACTIVITY
        return ResponseEntity.ok().body(questionDTO);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        // SET UP PAGINATION
        Pageable pageable = PageRequest.of(page, size);
        Page<LexicalActivity> pagedQuestions = activityBankRepository.findAll(pageable);

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities are currently available in the collection");
        }

        // RETURN SUCCESS RESPONSE WITH PAGED ACTIVITIES
        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, LexicalActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + activityDTO.getLanguage());
        }

        // FETCH EXISTING ACTIVITY BY ID
        Optional<LexicalActivity> optionalActivity = activityBankRepository.findById(id);
        if (optionalActivity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided ID");
        }

        // UPDATE EXISTING ACTIVITY WITH NEW DATA
        LexicalActivity activity = optionalActivity.get();
        updateActivityFromDTO(activity, activityDTO);
        activityBankRepository.save(activity);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok().body("Activity have been successfully updated");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        // CHECK IF ACTIVITY EXISTS BY ID
        if (activityBankRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided ID");
        }

        // DELETE ACTIVITY BY ID
        activityBankRepository.deleteById(id);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok().body("Activity have been successfully deleted");
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

    // BUILD A LEXICAL ACTIVITY OBJECT FROM DTO
    private LexicalActivity buildLexicalActivity(LexicalActivityDTO dto) {
        LexicalActivity activity = LexicalActivity.builder()
                .activityNumber(dto.getActivityNumber())
                .language(dto.getLanguage())
                .question(dto.getQuestion())
                .build();

        encodeToBase64BasedOnLanguage(dto, activity);

        return activity;
    }

    // UPDATE EXISTING VERBAL ACTIVITY FROM DTO
    private void updateActivityFromDTO(LexicalActivity activity, LexicalActivityDTO dto) {
        activity.setActivityNumber(dto.getActivityNumber());
        activity.setLanguage(dto.getLanguage());
        activity.setQuestion(dto.getQuestion());

        encodeToBase64BasedOnLanguage(dto, activity);
    }

    // MAP VERBAL ACTIVITY TO DTO
    private LexicalActivityDTO mapToDTO(LexicalActivity activity) {
        LexicalActivityDTO dto = LexicalActivityDTO.builder()
                .activityNumber(activity.getActivityNumber())
                .language(activity.getLanguage())
                .question(activity.getQuestion())
                .correctAnswer(activity.getCorrectAnswer())
                .build();

        if(activity.getAnswers() != null && !activity.getAnswers().isEmpty()){
            dto.setAnswers(activity.getAnswers());
        }

        return dto;
    }

    // GET A RANDOM ACTIVITY FROM A LIST
    private LexicalActivity getRandomActivity(List<LexicalActivity> activities) {
        Random random = new Random();
        return activities.get(random.nextInt(activities.size()));
    }

    private void encodeToBase64BasedOnLanguage(LexicalActivityDTO dto, LexicalActivity activity) {
        if(!dto.getLanguage().name().equals(Language.English.name())){
            if(dto.getAnswers() != null && !dto.getAnswers().isEmpty()){
                activity.setAnswers(encodeListToBase64(dto.getAnswers()));
            }
            activity.setCorrectAnswer(encodeToBase64(dto.getCorrectAnswer()));
        }else{
            if(dto.getAnswers() != null && !dto.getAnswers().isEmpty()){
                activity.setAnswers(dto.getAnswers());
            }
            activity.setCorrectAnswer(dto.getCorrectAnswer());
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
