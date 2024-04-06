package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.request.user.UserRequest;
import ru.otdel.doca.model.response.notification.NotificationResponse;
import ru.otdel.doca.model.response.notification.TypeNotification;
import ru.otdel.doca.model.response.user.ShortUserResponse;
import ru.otdel.doca.model.response.user.UserResponse;
import ru.otdel.doca.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WSSender wsSender;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> response = userService.getAll();
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserResponse> getByLogin(@PathVariable String login){
        UserResponse response = userService.findByLogin(login);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping
    public ResponseEntity<UserResponse> getByLogin(@RequestBody UserRequest request){
        UserResponse response = userService.update(request);
        if (response != null){
            BiConsumer<String, Object> sender = wsSender.getSender();
            NotificationResponse notificationResponse = new NotificationResponse();
            notificationResponse.setType(TypeNotification.CHANGE_USER);
            notificationResponse.setBody("Данные пользователя успешно изменены");
            sender.accept(response.getLogin(), notificationResponse);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/short")
    public ResponseEntity<List<ShortUserResponse>> getAllShort(){
        List<ShortUserResponse> response = userService.getAllShort();
        if (response != null && !response.isEmpty())
            return ResponseEntity.ok(response);
        else if (response != null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
