package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otdel.doca.model.response.user.ShortUserResponse;
import ru.otdel.doca.model.response.user.UserResponse;
import ru.otdel.doca.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> response = userService.getAll();
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
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
