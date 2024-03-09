package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class WSSender {
    private final SimpMessagingTemplate template;

    public <T> BiConsumer<String, T> getSender(){
        return (String loginExecutor, T payload)->{
            template.convertAndSend("/specific/notification/" + loginExecutor,
                    payload);
        };
    }
}
