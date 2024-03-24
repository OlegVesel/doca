package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.document.Card;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.facade.document.OrderFacade;
import ru.otdel.doca.model.request.document.OrderRequest;
import ru.otdel.doca.model.response.document.OrderResponse;
import ru.otdel.doca.repo.document.OrderRepo;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFacade orderFacade;
    private final OrderRepo orderRepo;
    public OrderResponse saveOrder(OrderRequest request){
        Order order = orderFacade.requestToEntity(request);
        Order savedOrder = orderRepo.save(order);
        return orderFacade.entityToResponse(savedOrder);
    }

    public Boolean executeOrder(UUID executorCardId) {
        Optional<Order> orderOpt = orderRepo.findByCardExecutor_Id(executorCardId);
        if (orderOpt.isPresent()){
            Order order = orderOpt.get();
            order.setExecuted(true);
            orderRepo.save(order);
        } else {
            throw new RuntimeException("Заказчик не найден!");
        }
        return true;
    }

    public Card findCustomerCardByExecutorCardId(UUID executorCardId){
        Optional<Order> orderOpt = orderRepo.findByCardExecutor_Id(executorCardId);
        return orderOpt.map(Order::getCardCustomer).orElse(null);
    }
}
