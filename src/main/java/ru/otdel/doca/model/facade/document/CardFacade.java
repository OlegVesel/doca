package ru.otdel.doca.model.facade.document;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.facade.BaseFacade;
import ru.otdel.doca.model.facade.UserFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.repo.UserRepo;
import ru.otdel.doca.repo.document.CardRepo;

@Component
@RequiredArgsConstructor
public class CardFacade implements BaseFacade<Card, CardRequest, CardResponse> {
    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final UserFacade userFacade;
    private final DocumentFacade documentFacade;

    @Override
    public Card requestToEntity(CardRequest request) {
        Card entity;
        if (request.getId() != null)
            entity = cardRepo.findById(request.getId()).orElse(new Card());
        else
            entity = new Card();
        if (request.getUserLogin() != null) {
            entity.setUserLogin(request.getUserLogin());
        } else {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            entity.setUserLogin(login);
        }
        entity.setComment(request.getComment());
        entity.setExecuteTo(request.getExecuteTo());
        if (request.getTitle() != null)
            entity.setTitle(request.getTitle());
        return entity;
    }

    @Override
    public CardResponse entityToResponse(Card entity) {
        CardResponse response = new CardResponse();
        response.setId(entity.getId());
        response.setCreated(entity.getCreated());
        response.setUserLogin(entity.getUserLogin());
        response.setTitle(entity.getTitle());
        response.setComment(entity.getComment());
        response.setExecuteTo(entity.getExecuteTo());
        if (entity.getDocuments() != null)
            response.setDocuments(
                    entity.getDocuments()
                            .stream()
                            .map(documentFacade::entityToResponse)
                            .toList()
            );
        return response;
    }
}
