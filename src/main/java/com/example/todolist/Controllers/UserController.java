package com.example.todolist.Controllers;

import com.example.todolist.Models.User;
import com.example.todolist.Service.UserService;
import com.example.todolist.ViewModels.LoginVM;
import com.example.todolist.ViewModels.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserVM>>> getAllUsers() {
        return userService.getAllUsers()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserVM>> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .thenApply(user -> user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build());
    }

    @GetMapping("/personal")
    public CompletableFuture<ResponseEntity<UserVM>> getPersonalInfo(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getPersonalInfo(currentUser.getUsername())
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<User>> createUser(@RequestBody UserVM userVM) {
        return userService.createUser(userVM)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> updateUser(@PathVariable Integer id, @RequestBody UserVM userVM, @AuthenticationPrincipal UserDetails currentUser) {
        return userService.updateUser(id, userVM, currentUser.getUsername())
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<String>> login(@RequestBody LoginVM loginRequest) {
        return userService.login(loginRequest)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()));
    }

    @PostMapping("/logout")
    public CompletableFuture<ResponseEntity<String>> logout() {
        return userService.logout()
                .thenApply(ResponseEntity::ok);
    }
}
