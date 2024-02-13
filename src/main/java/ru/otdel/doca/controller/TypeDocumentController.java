package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otdel.doca.model.response.TypeDocResponse;
import ru.otdel.doca.service.dictionary.TypeDocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary/type_docs")
@RequiredArgsConstructor
public class TypeDocumentController {
    private final TypeDocumentService typeDocumentService;


    @GetMapping
    public ResponseEntity<List<TypeDocResponse>> getAll(){
        List<TypeDocResponse> responses = typeDocumentService.getAll();
        if (responses != null)
            return ResponseEntity.ok(responses);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
