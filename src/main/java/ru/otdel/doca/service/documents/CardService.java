package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.model.facade.document.CardFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.repo.document.CardRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepo cardRepo;
    private final CardFacade cardFacade;
    private final DocumentService documentService;

    @Transactional
    public CardResponse saveCard(CardRequest request) {
        Card card = cardFacade.requestToEntity(request);
        if (request.getMultipartFiles() != null && !request.getMultipartFiles().isEmpty()) {
            card.getDocuments().addAll(documentService.multipartToDocument(request.getMultipartFiles(), request.getTypeDocId()));
        }
        Card save = cardRepo.save(card);
        return cardFacade.entityToResponse(save);
    }

    public List<CardResponse> getAllByUser(Boolean isDeleted) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return cardRepo.findByUserLoginAndIsDeleted(userLogin, isDeleted)
                .stream()
                .map(cardFacade::entityToResponse)
                .toList();
    }

    public Boolean deleteCardById(UUID id) {
        Card card = cardRepo.findById(id).orElse(null);
        if (card == null)
            return false;
        if (!card.getIsDeleted()) {
            card.setIsDeleted(true);
            cardRepo.save(card);
        } else {
            cardRepo.delete(card);
        }
        return true;
    }

    public Card copyCardById(UUID cardId, String newUserLogin) {
        Card oldCard = cardRepo.findById(cardId).orElse(null);
        if (oldCard == null)
            return null;
        List<Document> newDocs = new ArrayList<>();
        for (Document oldDoc : oldCard.getDocuments()) {
            Document newDoc = new Document();
            BeanUtils.copyProperties(oldDoc, newDoc, "id", "updated", "created", "cards");
            newDocs.add(newDoc);
        }
        Card newCard = new Card();
        BeanUtils.copyProperties(oldCard, newCard, "id", "updated", "created", "executeTo", "userLogin", "documents");
        newCard.setUserLogin(newUserLogin);
        newCard.setDocuments(newDocs);
        return cardRepo.save(newCard);
    }

    public CardResponse getCardById(UUID id) {
        Card card = cardRepo.findById(id).orElse(null);
        if (card != null)
            return cardFacade.entityToResponse(card);
        return null;
    }
}
