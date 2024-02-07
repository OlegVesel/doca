package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> save(@RequestBody CardRequest request){
        CardResponse response = cardService.saveCard(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAll(){
        List<CardResponse> response = cardService.getAllByUser();
        if (response != null && !response.isEmpty())
            return ResponseEntity.ok(response);
        else if(response != null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<CardResponse> update(@RequestBody CardRequest request){
        CardResponse response = cardService.saveCard(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

}
