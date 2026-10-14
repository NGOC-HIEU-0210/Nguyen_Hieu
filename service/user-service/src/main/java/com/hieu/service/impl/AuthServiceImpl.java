package com.hieu.service.impl;

import com.hieu.config.JWTProvider;
import com.hieu.enums.UserRole;
import com.hieu.mapper.UserMapper;
import com.hieu.model.User;
import com.hieu.payload.dto.UserDto;
import com.hieu.payload.response.AuthResponse;
import com.hieu.repository.UserRepository;
import com.hieu.service.AuthService;
import com.hieu.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    /*
     * LOGIN
     * 1. Load user by email
     * 2. Compare password with BCrypt
     * 3. Update lastLogin
     * 4. Generate JWT token
     * 5. Return token and user information
     */
    @Override
    public AuthResponse login(String email, String password) throws Exception {

        Authentication authentication = authenticate(email, password);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found!");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateToken(
                authentication,
                user.getId()
        );

        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(user));
        authResponse.setTitle("Welcome " + user.getFullName());
        authResponse.setMessage("Login Successfully");

        return authResponse;
    }

    /*
     * AUTHENTICATE
     * 1. Find user by email
     * 2. Check password
     * 3. Create Authentication object
     */
    private Authentication authenticate(
            String email,
            String password
    ) throws Exception {

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(
                password,
                userDetails.getPassword()
        )) {
            throw new Exception("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    /*
     * SIGNUP
     * 1. Check if email already exists
     * 2. Check system admin role
     * 3. Encode password using BCrypt
     * 4. Save user
     * 5. Generate JWT token
     * 6. Return token and user information
     */
    @Override
    public AuthResponse signup(UserDto req) throws Exception {

        // Check email already exists
        User existingUser =
                userRepository.findByEmail(req.getEmail());

        if (existingUser != null) {
            throw new Exception("email already registered!");
        }

        // Cannot register system admin
        if (req.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
            throw new Exception("You cannot sign up system admins!");
        }

        // Create new user
        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(req.getRole())
                .fullName(req.getFullName())
                .lastLogin(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        // Save user
        User savedUser = userRepository.save(newUser);

        /*
         * Create Authentication for JWT
         */
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(),
                        savedUser.getPassword()
                );

        // Generate JWT
        String jwt = jwtProvider.generateToken(
                authentication,
                savedUser.getId()
        );

        // Create response
        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(savedUser));
        authResponse.setTitle(
                "Welcome " + savedUser.getFullName()
        );
        authResponse.setMessage("Registered Successfully");

        return authResponse;
    }
}