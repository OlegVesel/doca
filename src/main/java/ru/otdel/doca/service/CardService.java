package ru.otdel.doca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.facade.document.CardFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.repo.document.CardRepo;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepo cardRepo;
    private final CardFacade cardFacade;
    private final DocumentService documentService;

    @Transactional
    public CardResponse saveCard(CardRequest request){
        Card card = cardFacade.requestToEntity(request);
        if (request.getDocuments() != null && !request.getDocuments().isEmpty()) {
          card.getDocuments().addAll(documentService.multipartToDocument(request.getDocuments()));
        }
        Card save = cardRepo.save(card);
        return cardFacade.entityToResponse(save);
    }

    public List<CardResponse> getAllByUser() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return cardRepo.findAllByUserLogin(userLogin)
                .stream()
                .map(cardFacade::entityToResponse)
                .toList();
    }

    public Boolean deleteCard(UUID id) {
        cardRepo.deleteById(id);
        return true;
    }
}
