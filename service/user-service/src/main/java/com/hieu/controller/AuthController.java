package com.hieu.controller;

import com.hieu.payload.dto.UserDto;
import com.hieu.payload.request.LoginRequest;
import com.hieu.payload.response.AuthResponse;
import com.hieu.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid UserDto userDto) throws Exception {
        AuthResponse response = authService.signup(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public  ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) throws Exception {
        AuthResponse response = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(response);
    }
}
