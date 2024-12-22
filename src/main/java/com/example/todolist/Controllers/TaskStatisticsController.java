package com.example.todolist.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.Models.User;
import com.example.todolist.Repository.TaskStatisticsRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.TaskStatisticsVM;

@RestController
@RequestMapping("/taskstatistics")
public class TaskStatisticsController {
    @Autowired
    private TaskStatisticsRepository taskStatisticsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<TaskStatisticsVM>> getAllTaskStatistics() {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        User currentUser = userRepository.findByUsername(currentUsername);

        // Lấy userId từ người dùng hiện tại
        Integer userId = currentUser.getUserId();

        List<TaskStatisticsVM> taskStatistics = taskStatisticsRepository.findAllTaskStatisticsByUserId(userId);
        return ResponseEntity.ok(taskStatistics);
    }
}