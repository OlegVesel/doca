package ru.otdel.doca.model.request.document;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderRequest {
    private UUID id;
    private String loginExecutor;
    private UUID cardId;
    private LocalDateTime executeTo;
    private Boolean executed;
    private Boolean needReport;
}
