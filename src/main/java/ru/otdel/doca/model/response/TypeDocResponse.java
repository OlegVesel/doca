package ru.otdel.doca.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class TypeDocResponse {
    private UUID id;
    private String name;
}
