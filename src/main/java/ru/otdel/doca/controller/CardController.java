package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.request.document.CardRequest;
import ru.otdel.doca.model.response.document.CardResponse;
import ru.otdel.doca.service.documents.CardService;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<CardResponse> update(@ModelAttribute CardRequest request){
        CardResponse response = cardService.saveCard(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> softDelete(@PathVariable UUID id){
        Boolean response = cardService.softDeleteCardById(id);
        if (response)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
