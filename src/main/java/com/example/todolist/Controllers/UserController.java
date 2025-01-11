package com.example.todolist.Controllers;

import com.example.todolist.Config.PasswordUtil;
import com.example.todolist.Models.User;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.LoginVM;
import com.example.todolist.ViewModels.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // Tiêm UserRepository vào để sử dụng
    private UserRepository userRepository;
    
    @GetMapping 
    public ResponseEntity<List<UserVM>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll_user());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVM> getUserById(@PathVariable Integer id) {
        UserVM user = userRepository.findUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserVM userVM) {
        if (userVM == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = new User();
        user.setFullName(userVM.getFullName());
        if(userVM.getRole() == null) {
            user.setRole("member");
        } else {
            user.setRole(userVM.getRole());
        }
        user.setUsername(userVM.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userVM.getPassword())); // Mã hóa mật khẩu
        user.setEmail(userVM.getEmail());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserVM userVM, @AuthenticationPrincipal UserDetails currentUser) {
        // Kiểm tra xem người dùng hiện tại có phải là người dùng đang được cập nhật hay không
        if (!currentUser.getUsername().equals(userRepository.findById(id).orElse(null).getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setFullName(userVM.getFullName());
        user.setRole(userVM.getRole());
        user.setUsername(userVM.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userVM.getPassword()));
        user.setEmail(userVM.getEmail());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginVM loginRequest) {
    // Kiểm tra đầu vào
    if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
        return ResponseEntity.badRequest().body("Username or password is missing");
    }

    // Tìm người dùng theo username
    User user = userRepository.findByUsername(loginRequest.getUsername());
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }

    // Kiểm tra mật khẩu
    boolean passwordMatches = PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword());
    if (!passwordMatches) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    return ResponseEntity.ok("{\"message\": \"Login successful\", \"role\": \"" + user.getRole() + "\"}");
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        
        return ResponseEntity.ok("Logout successful");
    }
}