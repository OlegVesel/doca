package ru.otdel.doca.model.response.document;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderResponse {
    private UUID id;
    private String loginExecutor;
    private UUID cardId;
    private LocalDateTime executeTo;
    private Boolean executed;
}