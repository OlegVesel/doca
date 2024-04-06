package ru.otdel.doca.model.request.user;


import lombok.Data;

@Data
public class UserShortRequest {
    private String login;
    private String password;
}
