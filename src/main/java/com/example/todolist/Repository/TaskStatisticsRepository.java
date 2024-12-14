package com.example.todolist.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.todolist.Models.TaskStatistics;
import com.example.todolist.ViewModels.TaskStatisticsVM;

public interface TaskStatisticsRepository extends JpaRepository<TaskStatistics, Integer> {
    @Query("SELECT new com.example.todolist.ViewModels.TaskStatisticsVM(t.statisticId, t.totalTasks, t.completedTasks, t.pendingTasks, t.importantTasks) FROM TaskStatistics t WHERE t.user.userId = :userId")
    public List<TaskStatisticsVM> findAllTaskStatisticsByUserId(@Param("userId") Integer userId);
}