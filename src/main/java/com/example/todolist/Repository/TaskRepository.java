package com.example.todolist.Repository;

import com.example.todolist.Models.*;
import com.example.todolist.ViewModels.TaskVM;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t")
    List<TaskVM> findAll_task(); // kết quả của query
    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.taskId = :id")
    TaskVM findById_task(int id);
    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.status = :status")
    List<TaskVM> findByStatus_task(String status);
    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.priority = :priority")
    List<TaskVM> findByPriority_task(String priority);
    @Query("DELETE FROM Task t WHERE t.taskId = :id")
    void deleteById_task(int id);
}
