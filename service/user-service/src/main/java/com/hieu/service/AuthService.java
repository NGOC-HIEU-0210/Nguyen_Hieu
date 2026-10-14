package com.hieu.service;

import com.hieu.payload.dto.UserDto;
import com.hieu.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signup(UserDto req) throws Exception;

}
