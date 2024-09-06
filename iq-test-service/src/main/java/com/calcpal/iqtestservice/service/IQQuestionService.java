package com.calcpal.iqtestservice.service;

import com.calcpal.iqtestservice.DTO.IQQuestionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQQuestionService {
    ResponseEntity<?> add(IQQuestionDTO questionDTO);

    ResponseEntity<?> addAll(List<IQQuestionDTO> questionDTOList);

    ResponseEntity<?> getRandom(Long id, String language);

    ResponseEntity<?> getAll(int page, int size);

    ResponseEntity<?> update(String id, IQQuestionDTO questionDTO);

    ResponseEntity<?> delete(String id);
}
