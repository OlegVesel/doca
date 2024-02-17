package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.otdel.doca.model.entity.dictionary.TypeDoc;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.repo.document.DocumentRepo;
import ru.otdel.doca.repo.document.TypeDocRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final TypeDocRepo typeDocRepo;
    private final DocumentRepo documentRepo;

    public Document multipartToDocument(MultipartFile multipartFile, UUID typeDocId) {
        if (multipartFile == null)
            return null;
        TypeDoc typeDoc = typeDocId == null
                ? typeDocRepo.findById(UUID.fromString("bde4700c-e7f3-4844-a3c5-654802233c80")).orElse(null)
                : typeDocRepo.findById(typeDocId).orElse(null);
        try {
            Document document = new Document();
            document.setData(multipartFile.getBytes());
            document.setTitle(multipartFile.getOriginalFilename());
            document.setTypeDoc(typeDoc);
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Document> multipartToDocument(List<MultipartFile> multipartFiles, UUID typeDocId) {
        if (multipartFiles != null && !multipartFiles.isEmpty()){
            List<Document> response = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                response.add(multipartToDocument(multipartFile, typeDocId));
            }
        return response;
        }
        return null;
    }

    public Boolean softDeleteById(UUID id) {
        Document document = documentRepo.findById(id).orElse(null);
        if (document == null)
            return false;
        document.setIsDeleted(true);
        documentRepo.save(document);
        return true;
    }

    @Transactional
    public Boolean hardDeleteById(UUID id) {
        documentRepo.deleteRelation(id);
        documentRepo.deleteById(id);
        return true;
    }
}
