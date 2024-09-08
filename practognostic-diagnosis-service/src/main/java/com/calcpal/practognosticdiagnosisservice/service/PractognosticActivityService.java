package com.calcpal.practognosticdiagnosisservice.service;

import com.calcpal.practognosticdiagnosisservice.DTO.PractognosticActivityDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PractognosticActivityService {
    ResponseEntity<?> add(PractognosticActivityDTO activityDTO);

    ResponseEntity<?> addAll(List<PractognosticActivityDTO> activityDTOList);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, PractognosticActivityDTO activityDTO);

    ResponseEntity<?> delete(String id);
}
