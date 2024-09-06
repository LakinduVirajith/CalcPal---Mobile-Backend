package com.calcpal.verbaldiagnosisservice.service;

import com.calcpal.verbaldiagnosisservice.DTO.VerbalQuestionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VerbalQuestionService {
    ResponseEntity<?> add(VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> addAll(List<VerbalQuestionDTO> questionBankDTOList);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, VerbalQuestionDTO questionBankDTO);

    ResponseEntity<?> delete(String id);
}
