package ru.otdel.doca.model.request.document;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CardRequest {
    private UUID id;
    private String userLogin;
    private String title;
    private List<DocumentRequest> documents;
}
