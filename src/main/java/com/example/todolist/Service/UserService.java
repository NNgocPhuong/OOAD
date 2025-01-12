package com.example.todolist.Service;

import com.example.todolist.Config.PasswordUtil;
import com.example.todolist.Models.User;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.LoginVM;
import com.example.todolist.ViewModels.UserVM;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async("taskExecutor")
    public CompletableFuture<List<UserVM>> getAllUsers() {
        return CompletableFuture.supplyAsync(userRepository::findAll_user);
    }

    @Async("taskExecutor")
    public CompletableFuture<UserVM> getUserById(Integer id) {
        return CompletableFuture.supplyAsync(() -> userRepository.findUserById(id));
    }

    @Async("taskExecutor")
    public CompletableFuture<User> createUser(UserVM userVM) {
        return CompletableFuture.supplyAsync(() -> {
            User user = new User();
            user.setFullName(userVM.getFullName());
            user.setRole(userVM.getRole() == null ? "member" : userVM.getRole());
            user.setUsername(userVM.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userVM.getPassword()));
            user.setEmail(userVM.getEmail());
            return userRepository.save(user);
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<User> updateUser(Integer id, UserVM userVM, String currentUsername) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id).orElse(null);
            if (user == null || !user.getUsername().equals(currentUsername)) {
                throw new RuntimeException("Access denied or user not found");
            }
            user.setFullName(userVM.getFullName());
            user.setRole(userVM.getRole());
            user.setUsername(userVM.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userVM.getPassword()));
            user.setEmail(userVM.getEmail());
            return userRepository.save(user);
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<String> login(LoginVM loginRequest) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findByUsername(loginRequest.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            boolean passwordMatches = PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword());
            if (!passwordMatches) {
                throw new RuntimeException("Invalid credentials");
            }
            // Khởi tạo các thuộc tính LAZY
            Hibernate.initialize(user.getUserGroups());

            // Lấy danh sách các nhóm mà người dùng tham gia
            List<Integer> groupIds = user.getUserGroups().stream()
                    .map(userGroup -> userGroup.getGroup().getGroupId())
                    .collect(Collectors.toList());

            return "{\"message\": \"Login successful\", \"role\": \"" + user.getRole() + "\", \"groupIds\": " + groupIds + "}";
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<String> logout() {
        return CompletableFuture.completedFuture("Logout successful");
    }
    @Async("taskExecutor")
    @Transactional
    public CompletableFuture<List<Integer>> getUserGroups(String username) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            Hibernate.initialize(user.getUserGroups());
            return user.getUserGroups().stream()
                    .map(userGroup -> userGroup.getGroup().getGroupId())
                    .collect(Collectors.toList());
        });
    }
}
