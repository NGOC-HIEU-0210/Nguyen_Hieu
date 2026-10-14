package com.hieu.service;

import com.hieu.model.User;
import com.hieu.payload.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email) throws Exception;
    UserDto getUserById(Long id) throws Exception;
    List<UserDto> getAllUsers();
}
