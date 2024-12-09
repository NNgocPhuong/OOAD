package com.example.todolist.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TaskStatistics")
@Data
public class TaskStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistic_id")
    private Integer statisticId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_tasks")
    private Integer totalTasks = 0;

    @Column(name = "completed_tasks")
    private Integer completedTasks = 0;

    @Column(name = "pending_tasks")
    private Integer pendingTasks = 0;

    @Column(name = "important_tasks")
    private Integer importantTasks = 0;
}
