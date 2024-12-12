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
    @RequestMapping("/tasks")
    public class TaskController {

        @Autowired
        private TaskRepository taskRepository;
        // @Autowired
        // private GroupTaskRepository groupTaskRepository;


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
        @GetMapping("/personal/{userId}")
        public ResponseEntity<List<TaskVM>> getPersonalTasks(@PathVariable int userId) {
            List<TaskVM> tasks = taskRepository.findPersonalTasksByUserId(userId);
            if(tasks.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tasks);
        }

        @GetMapping("/group/{groupId}")
        public ResponseEntity<List<TaskVM>> getGroupTasks(@PathVariable int groupId) {
            List<TaskVM> tasks = taskRepository.findGroupTasksByGroupId(groupId);
            if(tasks.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tasks);
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

        // @DeleteMapping("/{id}")
        // public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        //     Task task = taskRepository.findById(id).orElse(null);
        //     if (task == null) {
        //         return ResponseEntity.notFound().build();
        //     }
        //     try {
        //     // Xóa các bản ghi liên quan trong bảng GroupTask
        //     List<GroupTask> groupTasks = groupTaskRepository.findByTask(task);
        //     groupTaskRepository.deleteAll(groupTasks);

        //     // Xóa Task
        //     taskRepository.delete(task);
        //     return ResponseEntity.noContent().build();
        // } catch (DataIntegrityViolationException e) {
        //     return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        // }
        // }

    }

