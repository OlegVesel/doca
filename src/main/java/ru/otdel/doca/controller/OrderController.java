package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otdel.doca.model.request.document.OrderRequest;
import ru.otdel.doca.model.response.document.OrderResponse;
import ru.otdel.doca.service.documents.OrderService;

import java.util.function.BiConsumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final WSSender wsSender;

    @PostMapping
    public ResponseEntity<Boolean> assignExecutor(@RequestBody OrderRequest request){
        OrderResponse response = orderService.saveOrder(request);
        if (response != null){
            BiConsumer<String, OrderResponse> sender = wsSender.getSender();
            sender.accept(response.getLoginExecutor(), response);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
