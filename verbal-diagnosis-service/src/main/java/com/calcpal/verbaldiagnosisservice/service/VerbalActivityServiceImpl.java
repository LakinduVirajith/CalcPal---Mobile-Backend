package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalActivityDTO;
import com.calcpal.verbaldiagnosisservice.collection.VerbalActivity;
import com.calcpal.verbaldiagnosisservice.enums.Language;
import com.calcpal.verbaldiagnosisservice.repository.VerbalActivityRepository;
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
public class VerbalActivityServiceImpl implements VerbalActivityService{

    private final VerbalActivityRepository activityBankRepository;

    @Override
    public ResponseEntity<?> add(VerbalActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The language provided is not valid.");
        }

        // BUILD A VERBAL ACTIVITY OBJECT AND SAVE IT
        VerbalActivity activity = buildVerbalActivity(activityDTO);
        activityBankRepository.save(activity);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity has been successfully added");
    }

    @Override
    public ResponseEntity<?> addAll(List<VerbalActivityDTO> dtoList) {
        // CHECK IF THE LIST HAS MORE THAN 10 ITEMS
        if (dtoList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 10 activities at once.");
        }

        // VALIDATE EACH ACTIVITY-DTO AND BUILD LEXICAL ACTIVITY OBJECTS
        List<VerbalActivity> activities = dtoList.stream()
                .map(dto -> {
                    if (!isValidLanguage(dto.getLanguage())) {
                        throw new IllegalArgumentException("Invalid language: " + dto.getLanguage());
                    }
                    return buildVerbalActivity(dto);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The language provided is not valid.");
        }

        // FETCH QUESTIONS BY QUESTION NUMBER AND FILTER BY LANGUAGE
        List<VerbalActivity> questions = activityBankRepository.findByActivityNumber(id);
        List<VerbalActivity> filteredActivities = questions.stream()
                .filter(q -> q.getLanguage().name().equalsIgnoreCase(language))
                .collect(Collectors.toList());

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (filteredActivities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities found on the server for the provided question number.");
        }

        // SELECT A RANDOM ACTIVITY FROM THE FILTERED LIST
        VerbalActivity randomActivity = getRandomActivity(filteredActivities);
        VerbalActivityDTO questionDTO = mapToDTO(randomActivity);

        // RETURN SUCCESS RESPONSE WITH RANDOM ACTIVITY
        return ResponseEntity.ok().body(questionDTO);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        // SET UP PAGINATION
        Pageable pageable = PageRequest.of(page, size);
        Page<VerbalActivity> pagedQuestions = activityBankRepository.findAll(pageable);

        // HANDLE CASE WHERE NO ACTIVITIES ARE FOUND
        if (pagedQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activities are currently available in the collection");
        }

        // RETURN SUCCESS RESPONSE WITH PAGED ACTIVITIES
        return ResponseEntity.ok().body(pagedQuestions.getContent());
    }

    @Override
    public ResponseEntity<?> update(String id, VerbalActivityDTO activityDTO) {
        // VALIDATE THE LANGUAGE AGAINST ENUM VALUES
        if (!isValidLanguage(activityDTO.getLanguage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The language provided is not valid.");
        }

        // FETCH EXISTING ACTIVITY BY ID
        Optional<VerbalActivity> optionalActivity = activityBankRepository.findById(id);
        if (optionalActivity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No activity found for the provided ID");
        }

        // UPDATE EXISTING ACTIVITY WITH NEW DATA
        VerbalActivity activity = optionalActivity.get();
        updateActivityFromDTO(activity, activityDTO);
        activityBankRepository.save(activity);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok().body("Activity updated successfully");
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
        return ResponseEntity.ok().body("Activity deleted successfully");
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

    // BUILD A VERBAL ACTIVITY OBJECT FROM DTO
    private VerbalActivity buildVerbalActivity(VerbalActivityDTO dto) {
        VerbalActivity activity = VerbalActivity.builder()
                .activityNumber(dto.getActivityNumber())
                .language(dto.getLanguage())
                .question(dto.getQuestion())
                .answer(dto.getAnswer())
                .answers(dto.getAnswers())
                .build();

        encodeToBase64BasedOnLanguage(dto, activity);
        return activity;
    }

    // UPDATE EXISTING VERBAL ACTIVITY FROM DTO
    private void updateActivityFromDTO(VerbalActivity activity, VerbalActivityDTO dto) {
        activity.setActivityNumber(dto.getActivityNumber());
        activity.setLanguage(dto.getLanguage());
        activity.setQuestion(dto.getQuestion());
        activity.setAnswer(dto.getAnswer());
        activity.setAnswers(dto.getAnswers());

        encodeToBase64BasedOnLanguage(dto, activity);
    }

    // MAP VERBAL ACTIVITY TO DTO
    private VerbalActivityDTO mapToDTO(VerbalActivity activity) {
        return VerbalActivityDTO.builder()
                .activityNumber(activity.getActivityNumber())
                .language(activity.getLanguage())
                .question(activity.getQuestion())
                .answer(activity.getAnswer())
                .answers(activity.getAnswers())
                .correctAnswerAudioText(activity.getCorrectAnswerAudioText())
                .wrongAnswerAudioText(activity.getWrongAnswerAudioText())
                .build();
    }

    // GET A RANDOM ACTIVITY FROM A LIST
    private VerbalActivity getRandomActivity(List<VerbalActivity> activities) {
        Random random = new Random();
        return activities.get(random.nextInt(activities.size()));
    }

    private void encodeToBase64BasedOnLanguage(VerbalActivityDTO dto, VerbalActivity activity) {
        if(!dto.getLanguage().name().equals(Language.English.name())){
            activity.setCorrectAnswerAudioText(encodeToBase64(dto.getCorrectAnswerAudioText()));
            activity.setWrongAnswerAudioText(encodeToBase64(dto.getWrongAnswerAudioText()));
        }else{
            activity.setCorrectAnswerAudioText(dto.getCorrectAnswerAudioText());
            activity.setWrongAnswerAudioText(dto.getWrongAnswerAudioText());
        }
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
