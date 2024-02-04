package ru.otdel.doca.model.request.document;

import lombok.Data;

import java.util.UUID;


@Data
public class DocumentRequest {
    private UUID id;
    private String title;
    private String pathToDoc;
    private UUID typeDocId;
    private UUID cardId;
}