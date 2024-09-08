package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticActivityDTO;
import com.calcpal.practognosticdiagnosisservice.collection.PractognosticActivity;
import com.calcpal.practognosticdiagnosisservice.enums.Languages;
import com.calcpal.practognosticdiagnosisservice.repository.PractognosticActivityRepository;
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
public class PractognosticActivityServiceImpl implements PractognosticActivityService{

    private final PractognosticActivityRepository activityBankRepository;

    @Override
    public ResponseEntity<?> add(PractognosticActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + activityDTO.getLanguage());
        }

        // BUILD A LEXICAL ACTIVITY OBJECT AND SAVE IT
        PractognosticActivity activity = buildLexicalActivity(activityDTO);
        activityBankRepository.save(activity);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<PractognosticActivityDTO> activityDTOList) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (activityDTOList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 activities at once.");
        }

        // VALIDATE EACH ACTIVITY-DTO AND BUILD LEXICAL ACTIVITY OBJECTS
        List<PractognosticActivity> activities = activityDTOList.stream()
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
        List<PractognosticActivity> questions = activityBankRepository.findByActivityNumber(id);
        List<PractognosticActivity> filteredActivities = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (filteredActivities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found on the server for the provided activity number.");
        }

        // SELECT A RANDOM ACTIVITY FROM THE FILTERED LIST
        PractognosticActivity randomActivity = getRandomActivity(filteredActivities);
        PractognosticActivityDTO questionDTO = mapToDTO(randomActivity);

        // RETURN SUCCESS RESPONSE WITH RANDOM ACTIVITY
        return ResponseEntity.ok().body(questionDTO);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        // SET UP PAGINATION
        Pageable pageable = PageRequest.of(page, size);
        Page<PractognosticActivity> pagedQuestions = activityBankRepository.findAll(pageable);

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities are currently available in the collection");
        }

        // RETURN SUCCESS RESPONSE WITH PAGED ACTIVITIES
        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, PractognosticActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + activityDTO.getLanguage());
        }

        // FETCH EXISTING ACTIVITY BY ID
        Optional<PractognosticActivity> optionalActivity = activityBankRepository.findById(id);
        if (optionalActivity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided ID");
        }

        // UPDATE EXISTING ACTIVITY WITH NEW DATA
        PractognosticActivity activity = optionalActivity.get();
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
            Languages.valueOf(language.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // BUILD A LEXICAL ACTIVITY OBJECT FROM DTO
    private PractognosticActivity buildLexicalActivity(PractognosticActivityDTO dto) {
        PractognosticActivity activity = PractognosticActivity.builder()
                .activityNumber(dto.getActivityNumber())
                .language(dto.getLanguage())
                .activityLevelType(dto.getActivityLevelType())
                .question(dto.getQuestion())
                .build();

        if(dto.getQuestionText() != null){
            activity.setQuestionText(dto.getQuestionText());
        }
        if(dto.getImageType() != null){
            activity.setImageType(dto.getImageType());
        }
        if(dto.getAnswers() != null && !dto.getAnswers().isEmpty()){
            activity.setAnswers(dto.getAnswers());
        }
        if(dto.getCorrectAnswer() != null){
            activity.setCorrectAnswer(dto.getCorrectAnswer());
        }

        return activity;
    }

    // UPDATE EXISTING VERBAL ACTIVITY FROM DTO
    private void updateActivityFromDTO(PractognosticActivity activity, PractognosticActivityDTO dto) {
        activity.setActivityNumber(dto.getActivityNumber());
        activity.setLanguage(dto.getLanguage());
        activity.setActivityLevelType(dto.getActivityLevelType());
        activity.setQuestion(dto.getQuestion());

        if(dto.getQuestionText() != null){
            activity.setQuestionText(dto.getQuestionText());
        }
        if(dto.getImageType() != null){
            activity.setImageType(dto.getImageType());
        }
        if(dto.getAnswers() != null && !dto.getAnswers().isEmpty()){
            activity.setAnswers(dto.getAnswers());
        }
        if(dto.getCorrectAnswer() != null){
            activity.setCorrectAnswer(dto.getCorrectAnswer());
        }
    }

    // MAP VERBAL ACTIVITY TO DTO
    private PractognosticActivityDTO mapToDTO(PractognosticActivity activity) {
        PractognosticActivityDTO dto = PractognosticActivityDTO.builder()
                .activityNumber(activity.getActivityNumber())
                .language(activity.getLanguage())
                .activityLevelType(activity.getActivityLevelType())
                .question(activity.getQuestion())
                .build();

        if(activity.getQuestionText() != null){
            dto.setQuestionText(activity.getQuestionText());
        }
        if(activity.getImageType() != null){
            dto.setImageType(activity.getImageType());
        }
        if(activity.getAnswers() != null && !activity.getAnswers().isEmpty()){
            dto.setAnswers(activity.getAnswers());
        }
        if(activity.getCorrectAnswer() != null){
            dto.setCorrectAnswer(activity.getCorrectAnswer());
        }

        return dto;
    }

    // GET A RANDOM ACTIVITY FROM A LIST
    private PractognosticActivity getRandomActivity(List<PractognosticActivity> activities) {
        Random random = new Random();
        return activities.get(random.nextInt(activities.size()));
    }
}
