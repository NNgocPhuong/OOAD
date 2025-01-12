package com.example.todolist.Controllers;

import com.example.todolist.Models.Task;
import com.example.todolist.Service.TaskService;
import com.example.todolist.ViewModels.TaskVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<TaskVM>>> getAllTasks() {
        return taskService.getAllTasks()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<TaskVM>> getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id)
                .thenApply(task -> task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build());
    }

    @GetMapping("/personal")
    public CompletableFuture<ResponseEntity<List<TaskVM>>> getPersonalTasks(@AuthenticationPrincipal UserDetails currentUser) {
        return taskService.getPersonalTasks(currentUser.getUsername())
                .thenApply(tasks -> tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks));
    }

    @GetMapping("/group/{groupId}")
    public CompletableFuture<ResponseEntity<List<TaskVM>>> getGroupTasks(@PathVariable int groupId, @AuthenticationPrincipal UserDetails currentUser) {
        return taskService.getGroupTasks(currentUser.getUsername(), groupId)
                .thenApply(tasks -> tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks));
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Task>> createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails currentUser) {
        return taskService.createTask(task, currentUser.getUsername())
                .thenApply(savedTask -> ResponseEntity.status(HttpStatus.CREATED).body(savedTask));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Task>> updateTask(@PathVariable int id, @RequestBody TaskVM taskDetails) {
        return taskService.updateTask(id, taskDetails)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public CompletableFuture<ResponseEntity<Void>> assignUserToTask(@PathVariable int taskId, @PathVariable int userId) {
        return taskService.assignUserToTask(taskId, userId)
                .thenApply(v -> ResponseEntity.ok().build());
    }
}

