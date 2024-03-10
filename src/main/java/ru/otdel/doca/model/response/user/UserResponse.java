package ru.otdel.doca.model.response.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String accessToken;
    private String tokenType = "Bearer_";
    private List<String> roles = new ArrayList<>();
}
