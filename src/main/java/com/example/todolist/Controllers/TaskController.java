package com.example.todolist.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.todolist.Models.Task;
import com.example.todolist.Models.User;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.TaskVM;

import java.time.LocalDateTime;
import java.util.List;



    @RestController
    @RequestMapping("/tasks")
    public class TaskController {

        @Autowired
        private TaskRepository taskRepository;
        @Autowired
        private UserRepository userRepository;
        
        
                @GetMapping
                public List<TaskVM> getAllTasks() {
                    return taskRepository.findAll_task();
                }
        
                @GetMapping("/{id}")
                public ResponseEntity<TaskVM> getTaskById(@PathVariable int id) {
                    TaskVM task = taskRepository.findById_task(id);
                    if (task != null) {
                        return ResponseEntity.ok(task);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                }
                @GetMapping("/personal")
            public ResponseEntity<List<TaskVM>> getPersonalTasks(@AuthenticationPrincipal UserDetails currentUser) {
                List<TaskVM> tasks = taskRepository.findPersonalTasksByUserId(currentUser.getUsername());
                if (tasks.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(tasks);
            }
        
            @GetMapping("/group/{groupId}")
            public ResponseEntity<List<TaskVM>> getGroupTasks(@PathVariable int groupId, @AuthenticationPrincipal UserDetails currentUser) {
                // Kiểm tra xem người dùng hiện tại có thuộc nhóm này không
                if (!taskRepository.isUserInGroup(currentUser.getUsername(), groupId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                List<TaskVM> tasks = taskRepository.findGroupTasksByGroupId(groupId);
                if (tasks.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(tasks);
            }
        
        
        
        @PostMapping
        public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails currentUser) {
            User user = userRepository.findByUsername(currentUser.getUsername());
            if (user == null) { 
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            task.setAssignedUser(user); // Gán nhiệm vụ cho người dùng hiện tại
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            Task savedTask = taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

        @PutMapping("/{id}")
        public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody TaskVM taskDetails) {
            Task task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return ResponseEntity.notFound().build();
            }
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setPriority(taskDetails.getPriority());
            task.setUpdatedAt(LocalDateTime.now());
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(updatedTask);
        }
        @PutMapping("/{taskId}/assign/{userId}")
        @PreAuthorize("hasRole('MANAGER')")
        public ResponseEntity<Void> assignUserToTask(@PathVariable int taskId, @PathVariable int userId) {
            if(taskRepository.findById(taskId).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            taskRepository.assignUserToTask(taskId, userId);
            return ResponseEntity.ok().build();
        }
    }

