package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otdel.doca.model.request.UserRequest;
import ru.otdel.doca.model.response.UserResponse;
import ru.otdel.doca.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){
        UserResponse response = userService.login(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registrationUser(@RequestBody UserRequest request){
        UserResponse response = userService.register(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }
}
