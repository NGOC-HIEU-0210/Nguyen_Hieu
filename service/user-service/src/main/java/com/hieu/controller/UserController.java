package com.hieu.controller;


import com.hieu.model.User;
import com.hieu.payload.dto.UserDto;
import com.hieu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("X-User-Email") String email
    ) throws Exception {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Long userId) throws Exception {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> users = userService.getAllUsers();
        return  ResponseEntity.ok(users);
    }

}


