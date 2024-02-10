package ru.otdel.doca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otdel.doca.model.entity.dictionary.TypeDoc;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.repo.document.DocumentRepo;
import ru.otdel.doca.repo.document.TypeDocRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final TypeDocRepo typeDocRepo;
    private final DocumentRepo documentRepo;

    public List<Document> multipartToDocument(List<MultipartFile> multipartFiles) {
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            TypeDoc typeDoc = typeDocRepo.findById(UUID.fromString("bde4700c-e7f3-4844-a3c5-654802233c80")).orElse(null);
            return multipartFiles.stream().map(file -> {
                        try {
                            Document document = new Document();
                            document.setData(file.getBytes());
                            document.setTitle(file.getOriginalFilename());
                            document.setTypeDoc(typeDoc);
                            return document;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ).toList();
        }
        return null;
    }
}
