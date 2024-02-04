package ru.otdel.doca.model.response.document;

import lombok.Data;
import ru.otdel.doca.model.response.UserResponse;

import java.util.UUID;


@Data
public class CardResponse {
    private UUID id;
    private UserResponse user;
}