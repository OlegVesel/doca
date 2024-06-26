package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.model.response.document.OrderResponse;
import ru.otdel.doca.model.response.notification.NotificationResponse;
import ru.otdel.doca.model.response.notification.TypeNotification;
import ru.otdel.doca.service.documents.CardService;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final WSSender wsSender;

    @PostMapping
    public ResponseEntity<CardResponse> save(@RequestBody CardRequest request){
        CardResponse response = cardService.saveCard(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/execute")
    public ResponseEntity<CardResponse> execute(@ModelAttribute CardRequest request){
        CardResponse response = cardService.executeCard(request);
        if (response != null){
            String loginCustomer = response.getExecutorOrder().getLoginCustomer();
            BiConsumer<String, Object> sender = wsSender.getSender();
            NotificationResponse notificationResponse = new NotificationResponse();
            notificationResponse.setType(TypeNotification.EXECUTE);
            notificationResponse.setBody(response);
            sender.accept(loginCustomer, notificationResponse);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAll(){
        List<CardResponse> response = cardService.getAllByUser(false);
        if (response != null && !response.isEmpty())
            return ResponseEntity.ok(response);
        else if(response != null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<CardResponse>> getAllDeleted(){
        List<CardResponse> response = cardService.getAllByUser(true);
        if (response != null && !response.isEmpty())
            return ResponseEntity.ok(response);
        else if(response != null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable UUID id){
        CardResponse response = cardService.getCardById(id);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<CardResponse> update(@ModelAttribute CardRequest request){
        CardResponse response = cardService.saveCard(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> softDelete(@PathVariable UUID id){
        Boolean response = cardService.deleteCardById(id);
        if (response)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
