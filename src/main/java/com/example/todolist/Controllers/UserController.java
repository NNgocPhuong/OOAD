package com.example.todolist.Controllers;

import com.example.todolist.Models.User;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.UserVM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserVM> getAllUsers() {
        return userRepository.findAll_user();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserVM> getUserById(@PathVariable Integer id) {
        UserVM user = userRepository.findUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserVM userVM) {
    if(userVM == null) {
        return ResponseEntity.badRequest().build();
    }
    User user = new User();
    user.setFullName(userVM.getFullName());
    user.setRole(userVM.getRole());
    user.setUsername(userVM.getUsername());
    user.setEmail(userVM.getEmail());
    User savedUser = userRepository.save(user);
    return ResponseEntity.ok(savedUser);
    }
}