package com.example.todolist.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // Mỗi người dùng chỉ có thể xem task statistics của chính mình
    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskStatisticsVM>> getAllTaskStatisticsByUserId(@PathVariable Integer userId) {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        User currentUser = userRepository.findByUsername(currentUsername);

        // Kiểm tra xem userId có khớp với userId của người dùng hiện tại không
        if (!currentUser.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build(); // Trả về mã trạng thái 403 Forbidden nếu không khớp
        }

        List<TaskStatisticsVM> taskStatistics = taskStatisticsRepository.findAllTaskStatisticsByUserId(userId);
        return ResponseEntity.ok(taskStatistics);
    }
}