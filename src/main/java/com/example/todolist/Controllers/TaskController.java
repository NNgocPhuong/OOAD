package com.example.todolist.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.todolist.Models.Task;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.ViewModels.TaskVM;

import java.time.LocalDateTime;
import java.util.List;



    @RestController
    @RequestMapping("api/tasks")
    public class TaskController {

        @Autowired
        private TaskRepository taskRepository;

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

        @PostMapping
        public ResponseEntity<Task> createTask(@RequestBody TaskVM task) {
            if(task == null) {
                return ResponseEntity.badRequest().build();
            }
            Task newTask = new Task();
            newTask.setTaskId(task.getTaskId());
            newTask.setTitle(task.getTitle());
            newTask.setDescription(task.getDescription());
            newTask.setStatus(task.getStatus());
            newTask.setPriority(task.getPriority());
            newTask.setCreatedAt(LocalDateTime.now());
            newTask.setUpdatedAt(task.getUpdatedAt());
            
            Task savedTask = taskRepository.save(newTask);
            return ResponseEntity.ok(savedTask);
        }

        // @PutMapping("/{id}")
        // public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        //     Task updatedTask = taskRepository.updateTask(id, taskDetails);
        //     if (updatedTask != null) {
        //         return ResponseEntity.ok(updatedTask);
        //     } else {
        //         return ResponseEntity.notFound().build();
        //     }
        // }

        // @DeleteMapping("/{id}")
        // public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        //     boolean isDeleted = taskRepository.deleteTask(id);
        //     if (isDeleted) {
        //         return ResponseEntity.noContent().build();
        //     } else {
        //         return ResponseEntity.notFound().build();
        //     }
        // }
    }

