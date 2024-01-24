package ru.otdel.doca.model.request;


import lombok.Data;

@Data
public class UserRequest {
    private String login;
    private String password;
}
