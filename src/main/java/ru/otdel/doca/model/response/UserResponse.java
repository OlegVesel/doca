package ru.otdel.doca.model.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String accessToken;
    private String tokenType = "Bearer_";
}
