package ru.otdel.doca.model.facade.document;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.entity.user.UserEntity;
import ru.otdel.doca.model.facade.BaseFacade;
import ru.otdel.doca.model.request.document.OrderRequest;
import ru.otdel.doca.model.response.document.OrderResponse;
import ru.otdel.doca.model.response.document.ShortOrderResponse;
import ru.otdel.doca.repo.UserRepo;
import ru.otdel.doca.repo.document.CardRepo;
import ru.otdel.doca.repo.document.OrderRepo;
import ru.otdel.doca.service.documents.CardService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderFacade implements BaseFacade<Order, OrderRequest, OrderResponse> {
    private final UserRepo userRepo;
    private final CardService cardService;
    private final OrderRepo orderRepo;
    private final CardFacade cardFacade;
    private final CardRepo cardRepo;
    @Override
    public Order requestToEntity(OrderRequest request) {
        Order entity;
        if (request.getId() != null)
            entity = orderRepo.findById(request.getId()).orElse(new Order());
        else
            entity = new Order();
        if (request.getExecuted() != null)
            entity.setExecuted(request.getExecuted());
        else
            entity.setExecuted(false);
        if (request.getExecuteTo() != null)
            entity.setExecuteTo(request.getExecuteTo());

        if (request.getLoginExecutor() != null){
            Optional<UserEntity> byLogin = userRepo.findByLogin(request.getLoginExecutor());
            byLogin.ifPresent(entity::setExecutor);
        }
        if (request.getCardId() != null){
            Card newCard = cardService.copyCardById(request.getCardId(), request.getLoginExecutor());
            entity.setCardExecutor(newCard);
            entity.setCardCustomer(
                    cardRepo.findById(request.getCardId()).orElse(null)
            );
        }
        String customerLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> customerByLogin = userRepo.findByLogin(customerLogin);
        customerByLogin.ifPresent(entity::setCustomer);
        return entity;
    }

    @Override
    public OrderResponse entityToResponse(Order entity) {
        OrderResponse response = new OrderResponse();
        response.setId(entity.getId());
        response.setLoginExecutor(
                entity.getExecutor().getLogin()
        );
        response.setCardExecutor(
                cardFacade.entityToResponse(entity.getCardExecutor())
        );
        response.setCardCustomer(
                cardFacade.entityToResponse(entity.getCardCustomer())
        );
        response.setExecuteTo(
                entity.getExecuteTo()
        );
        response.setExecuted(entity.getExecuted());

        return response;
    }
}
