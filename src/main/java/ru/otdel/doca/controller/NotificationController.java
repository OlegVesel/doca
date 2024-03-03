package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otdel.doca.model.request.document.OrderRequest;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/task")
    public void sendNotification(@Payload OrderRequest request){
        System.out.println("send to " + request.getLoginExecutor());
        simpMessagingTemplate.convertAndSend("/specific/notification/" + request.getLoginExecutor(), request);
    }
}
