package ru.otdel.doca.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String login;
    private String accessToken;
    private String tokenType;
}
