package com.calcpal.ideognosticdiagnosisservice.service;

import com.calcpal.ideognosticdiagnosisservice.DTO.IdeognosticQuestionUploadDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IdeognosticQuestionService {
    ResponseEntity<?> add(@RequestParam("file") MultipartFile image, @Valid IdeognosticQuestionUploadDTO questionDTO);

    ResponseEntity<?> getRandom(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(String id, @Valid IdeognosticQuestionUploadDTO questionDTO);

    ResponseEntity<?> delete(String id);
}
