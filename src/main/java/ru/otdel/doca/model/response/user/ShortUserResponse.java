package ru.otdel.doca.model.response.user;

import lombok.Data;

@Data
public class ShortUserResponse {
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String fullName;
}
