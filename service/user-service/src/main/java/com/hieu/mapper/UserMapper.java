package com.hieu.mapper;

import com.hieu.model.User;
import com.hieu.payload.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
//                .password(user.getPassword())
                .phone(user.getPhone())
                .build();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
