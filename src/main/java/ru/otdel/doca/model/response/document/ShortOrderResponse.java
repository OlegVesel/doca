package ru.otdel.doca.model.response.document;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ShortOrderResponse {
    private UUID id;
    private List<String> loginExecutors; //исполнителей может быть много
    private UUID cardCustomerId; //для указания конкретной карточки заказчика
    private String loginCustomer; //заказчик всегда один
    private LocalDateTime executeTo;
    private Boolean executed;
    private Boolean needReport;
}
