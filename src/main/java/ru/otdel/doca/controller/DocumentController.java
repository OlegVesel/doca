package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.service.documents.DocumentService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getDoc(@PathVariable UUID id) {
        Document response = documentService.getDocumentById(id);
        if (response != null) {
            ByteArrayResource resource = new ByteArrayResource(response.getData());
            String[] split = response.getTitle().split("\\.");
            String fileName = URLEncoder.encode(split[0], StandardCharsets.UTF_8);
            String contentDisposition = ContentDisposition.attachment()
                    .filename(fileName)
                    .build().toString();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                    .body(resource);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> softDeleteById(@PathVariable UUID id) {
        Boolean response = documentService.softDeleteById(id);
        if (response)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Boolean> hardDeleteById(@PathVariable UUID id) {
        Boolean response = documentService.hardDeleteById(id);
        if (response)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
