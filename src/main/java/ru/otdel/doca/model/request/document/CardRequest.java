package ru.otdel.doca.model.request.document;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CardRequest {
    private UUID id;
    private String userLogin;
    private String title;
    private String comment;
    private LocalDate executeTo;
    private UUID typeDocId;
    private List<MultipartFile> documents;
}
