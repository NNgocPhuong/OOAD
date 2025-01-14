package com.example.todolist.Service;

import com.example.todolist.Models.Task;
import com.example.todolist.Models.User;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.TaskVM;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Async("taskExecutor")
    public CompletableFuture<List<TaskVM>> getAllTasks() {
        return CompletableFuture.supplyAsync(() -> taskRepository.findAll_task());
    }

    @Async("taskExecutor")
    public CompletableFuture<TaskVM> getTaskById(int id) {
        return CompletableFuture.supplyAsync(() -> taskRepository.findById_task(id));
    }

    @Async("taskExecutor")
    public CompletableFuture<List<TaskVM>> getPersonalTasks(String username) {
        return CompletableFuture.supplyAsync(() -> taskRepository.findPersonalTasksByUserId(username));
    }

    @Async("taskExecutor")
    public CompletableFuture<List<TaskVM>> getGroupTasks(String username, int groupId) {
        return CompletableFuture.supplyAsync(() -> {
            if (!taskRepository.isUserInGroup(username, groupId)) {
                throw new RuntimeException("User not in group");
            }
            return taskRepository.findGroupTasksByGroupId(groupId);
        });
    }

    @Async("taskExecutor")
    @Transactional
    public CompletableFuture<TaskVM> createTask(Task task, String username) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            task.setAssignedUser(user);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            Task savedTask = taskRepository.save(task);
            return new TaskVM(savedTask.getTaskId(), savedTask.getTitle(), savedTask.getDescription(), savedTask.getStatus(), savedTask.getPriority(), savedTask.getCreatedAt(), savedTask.getUpdatedAt());
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<Task> updateTask(int id, TaskVM taskDetails) {
        return CompletableFuture.supplyAsync(() -> {
            Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setPriority(taskDetails.getPriority());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> assignUserToTask(int taskId, int userId) {
        return CompletableFuture.runAsync(() -> {
            if (taskRepository.findById(taskId).isEmpty()) {
                throw new RuntimeException("Task not found");
            }
            taskRepository.assignUserToTask(taskId, userId);
        });
    }

    @Async("taskExecutor")
    @Transactional
    public CompletableFuture<List<TaskVM>> getAllGroupTasks(String username) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            return user.getUserGroups().stream()
                    .flatMap(userGroup -> taskRepository.findGroupTasksByGroupId(userGroup.getGroup().getGroupId()).stream())
                    .collect(Collectors.toList());
        });
    }


    @Async("taskExecutor")
    public CompletableFuture<Void> deleteTask(int id) {
        return CompletableFuture.runAsync(() -> {
            if (taskRepository.findById(id).isEmpty()) {
                throw new RuntimeException("Task not found");
            }
            taskRepository.deleteById(id);
        });
    }
}

