package ru.otdel.doca.model.request.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String password;
}
