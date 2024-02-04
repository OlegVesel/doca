package ru.otdel.doca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.facade.document.CardFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.repo.document.CardRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepo cardRepo;
    private final CardFacade cardFacade;

    public CardResponse saveCard(CardRequest request){
        Card card = cardFacade.requestToEntity(request);
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
}
