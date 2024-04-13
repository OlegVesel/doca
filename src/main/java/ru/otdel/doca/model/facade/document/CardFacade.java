package ru.otdel.doca.model.facade.document;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.facade.BaseFacade;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.model.response.document.ShortOrderResponse;
import ru.otdel.doca.repo.document.CardRepo;
import ru.otdel.doca.repo.document.OrderRepo;
import ru.otdel.doca.service.documents.DocumentService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardFacade implements BaseFacade<Card, CardRequest, CardResponse> {
    private final CardRepo cardRepo;
    private final DocumentFacade documentFacade;
    private final DocumentService documentService;
    private final OrderRepo orderRepo;

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
        if (request.getMultipartFiles() != null && !request.getMultipartFiles().isEmpty()) {
            entity.getDocuments().addAll(documentService.multipartToDocument(request.getMultipartFiles(), request.getTypeDocId()));
        }
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
        if (entity.getDocuments() != null && !entity.getDocuments().isEmpty())
            response.setDocuments(
                    entity.getDocuments()
                            .stream()
                            .map(documentFacade::entityToResponse)
                            .toList()
            );

        //находим всех исполнителей нашей карточки
        List<Order> customerCards = orderRepo.findByCardCustomer_Id(entity.getId());
        if (customerCards.size() > 0) {
            ShortOrderResponse shortOrderResponse = orderToShortOrderResponse(customerCards.get(0));
            List<String> loginExecutors = customerCards.stream().map(order -> order.getExecutor().getLogin()).toList();
            shortOrderResponse.setLoginExecutors(loginExecutors);
            response.setCustomerOrder(shortOrderResponse);
        }
        //находим заказчика нашей карточки
        orderRepo.findByCardExecutor_Id(entity.getId()).ifPresent(order -> {
            ShortOrderResponse shortOrderResponse = orderToShortOrderResponse(order);
            response.setExecutorOrder(shortOrderResponse);
        });


        return response;
    }

    private ShortOrderResponse orderToShortOrderResponse(Order order) {
        ShortOrderResponse shortOrderResponse = new ShortOrderResponse();
        shortOrderResponse.setId(order.getId());
        shortOrderResponse.setLoginCustomer(order.getCustomer().getLogin());
        shortOrderResponse.setCardCustomerId(order.getCardCustomer().getId());
        shortOrderResponse.setExecuted(order.getExecuted());
        shortOrderResponse.setExecuteTo(order.getExecuteTo());
        shortOrderResponse.setNeedReport(order.getNeedReport());
        return shortOrderResponse;
    }
}
