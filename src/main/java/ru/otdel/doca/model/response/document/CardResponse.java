package ru.otdel.doca.model.response.document;

import lombok.Data;
import ru.otdel.doca.model.response.UserResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class CardResponse {
    private UUID id;
    private LocalDate created;
    private UserResponse user;
    private String title;
    private String comment;
    private LocalDate executeTo;
    private List<DocumentResponse> documents = new ArrayList<>();
}