package ru.otdel.doca.service.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.document.Order;
import ru.otdel.doca.model.facade.document.OrderFacade;
import ru.otdel.doca.model.request.document.OrderRequest;
import ru.otdel.doca.repo.document.OrderRepo;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFacade orderFacade;
    private final OrderRepo orderRepo;
    public Boolean saveOrder(OrderRequest request){
        Order order = orderFacade.requestToEntity(request);
        orderRepo.save(order);
        return true;
    }
}
