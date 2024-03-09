package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.facade.document.OrderFacade;
import ru.otdel.doca.model.request.document.OrderRequest;
import ru.otdel.doca.model.response.document.OrderResponse;
import ru.otdel.doca.repo.document.OrderRepo;

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
}
