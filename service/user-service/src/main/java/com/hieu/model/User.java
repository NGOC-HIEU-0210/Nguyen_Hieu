package com.hieu.model;

import com.hieu.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private  String phone;

    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime lastLogin;
}
