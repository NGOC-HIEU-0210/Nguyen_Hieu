package com.hieu.service.impl;

import com.hieu.mapper.UserMapper;
import com.hieu.model.User;
import com.hieu.payload.dto.UserDto;
import com.hieu.repository.UserRepository;
import com.hieu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with email");
        }
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("User not found with id " + id)
        );
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= userRepository.findAll();
        return UserMapper.toDtoList(users);
    }
}
