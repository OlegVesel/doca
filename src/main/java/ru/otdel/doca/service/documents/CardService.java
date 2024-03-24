package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.entity.document.Document;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.facade.document.CardFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.repo.document.CardRepo;
import ru.otdel.doca.repo.document.OrderRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepo cardRepo;
    private final CardFacade cardFacade;
    private final DocumentService documentService;
    private final OrderRepo orderRepo;

    @Transactional
    public CardResponse saveCard(CardRequest request) {
        Card card = cardFacade.requestToEntity(request);
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

    //метод сохраняет карточку как выполненную
    @Transactional
    public CardResponse executeCard(CardRequest request) {
        //получаем файлы отчетов
        List<Document> reports = documentService.multipartToDocument(request.getMultipartFiles(), null);
        //отмечаем заказ, как выполненный
        Optional<Order> orderOpt = orderRepo.findByCardExecutor_Id(request.getId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setExecuted(true);
            orderRepo.save(order);
        } else {
            throw new RuntimeException("Заказчик не найден!");
        }
        //если отметили успешно, то добавляем файл отчета в карточку заказчика
        Card cardCustomer = orderRepo.findByCardExecutor_Id(request.getId()).map(Order::getCardCustomer).orElse(null);
        if (cardCustomer != null && reports != null) {
            cardCustomer.getDocuments().addAll(reports);
            cardRepo.save(cardCustomer);
            return saveCard(request);
        } else if (cardCustomer != null) {
            return saveCard(request);
        }
        throw new RuntimeException("При сохранении что-то пошло не так");
    }

}
