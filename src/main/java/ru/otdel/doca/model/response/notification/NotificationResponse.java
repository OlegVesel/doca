package ru.otdel.doca.model.response.notification;

import lombok.Data;

@Data
public class NotificationResponse {
    private TypeNotification type;
    private Object body;
}
