package ru.otdel.doca.model.facade.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.model.facade.BaseFacade;
import ru.otdel.doca.model.request.document.DocumentRequest;
import ru.otdel.doca.model.response.document.DocumentResponse;
import ru.otdel.doca.repo.document.DocumentRepo;
import ru.otdel.doca.repo.document.TypeDocRepo;

@Component
@RequiredArgsConstructor
public class DocumentFacade implements BaseFacade<Document, DocumentRequest, DocumentResponse> {
    private final DocumentRepo documentRepo;
    private final TypeDocRepo typeDocRepo;
    @Override
    public Document requestToEntity(DocumentRequest request) {
        Document entity;
        if (request.getId() != null)
            entity = documentRepo.findById(request.getId()).orElse(new Document());
        else
            entity = new Document();

        entity.setTitle(request.getTitle());
        entity.setPathToDoc(request.getPathToDoc());
        entity.setTypeDoc(
                typeDocRepo.findById(request.getTypeDocId()).orElse(null)
        );

        return entity;
    }

    @Override
    public DocumentResponse entityToResponse(Document entity) {
        DocumentResponse response = new DocumentResponse();
        response.setId(entity.getId());
        response.setTypeDoc(entity.getTypeDoc());
        response.setTitle(entity.getTitle());
        response.setPathToDoc(entity.getPathToDoc());
        return response;
    }
}
