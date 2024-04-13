package ru.otdel.doca.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otdel.doca.model.request.user.UserShortRequest;
import ru.otdel.doca.model.response.user.UserResponse;
import ru.otdel.doca.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserShortRequest request){
        UserResponse response = userService.login(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registrationUser(@RequestBody UserShortRequest request){
        UserResponse response = userService.register(request);
        if (response != null)
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
