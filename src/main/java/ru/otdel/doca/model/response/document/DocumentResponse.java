package ru.otdel.doca.model.response.document;

import lombok.Data;
import ru.otdel.doca.model.response.TypeDocResponse;

import java.util.UUID;

@Data
public class DocumentResponse {
    private UUID id;
    private String title;
    private String pathToDoc;
    private TypeDocResponse typeDoc;
    private UUID cardId;
}
