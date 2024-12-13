package com.example.todolist.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.Repository.TaskStatisticsRepository;
import com.example.todolist.ViewModels.TaskStatisticsVM;

@RestController
@RequestMapping("/taskstatistics")
public class TaskStatisticsController {
    @Autowired
    private TaskStatisticsRepository taskStatisticsRepository;

    @GetMapping
    public ResponseEntity<List<TaskStatisticsVM>> getAllTaskStatistics() {
        return ResponseEntity.ok(taskStatisticsRepository.findAllTaskStatistics());
    }
}
