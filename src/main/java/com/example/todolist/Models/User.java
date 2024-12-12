package com.example.todolist.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Users") // Chỉ định đúng tên bảng trong CSDL
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Chỉ định đúng tên cột
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String role; // member, manager

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
    private List<Task> tasks;
}