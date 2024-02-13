package ru.otdel.doca.model.response.document;

import lombok.Data;
import ru.otdel.doca.model.entity.dictionary.TypeDoc;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DocumentResponse {
    private UUID id;
    private LocalDate created;
    private String title;
    private String pathToDoc;
    private TypeDoc typeDoc;
    private UUID cardId;
    private Boolean isDeleted;
}
