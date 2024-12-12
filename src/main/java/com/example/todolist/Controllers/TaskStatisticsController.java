package com.example.todolist.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.Models.TaskStatistics;
import com.example.todolist.Repository.TaskStatisticsRepository;

@RestController
@RequestMapping("/taskstatistics")
public class TaskStatisticsController {
    @Autowired
    private TaskStatisticsRepository taskStatisticsRepository;

    @GetMapping
    public List<TaskStatistics> getAllTaskStatistics() {
        return taskStatisticsRepository.findAll();
    }
}
