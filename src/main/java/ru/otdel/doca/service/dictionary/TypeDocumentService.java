package ru.otdel.doca.service.dictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.response.TypeDocResponse;
import ru.otdel.doca.repo.document.TypeDocRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeDocumentService {
    private final TypeDocRepo typeDocRepo;


    public List<TypeDocResponse> getAll() {
        return typeDocRepo.findAll().stream().map(t -> {
            TypeDocResponse response = new TypeDocResponse();
            response.setId(t.getId());
            response.setName(t.getName());
            return response;
        }).toList();
    }
}
