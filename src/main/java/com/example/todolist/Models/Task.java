package com.example.todolist.Models;
import com.example.todolist.Models.Task;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "Task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status = "Pending"; // Pending, In Progress, Completed

    @Column(name = "priority")
    private String priority; // High, Medium, Low

    @Column(name = "is_important")
    private boolean isImportant;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id") // Liên kết tới cột user_id trong bảng Users
    private User assignedUser;
}