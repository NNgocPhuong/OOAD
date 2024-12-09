package com.example.todolist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.Models.TaskStatistics;

public interface TaskStatisticsRepository extends JpaRepository<TaskStatistics, Integer> {

}
