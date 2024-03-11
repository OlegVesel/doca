package ru.otdel.doca.model.response.document;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class CardResponse {
    private UUID id;
    private LocalDate created;
    private String userLogin;
    private String title;
    private String comment;
    private LocalDate executeTo;
    private List<DocumentResponse> documents = new ArrayList<>();
    private ShortOrderResponse customerOrder; //для отображения, что ты передал кому-то эту карточку
    private ShortOrderResponse executorOrder; // для отображения, что тебе кто-то эту карточку передал
}