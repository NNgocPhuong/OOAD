package com.example.todolist.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "TaskShare")
@Data
public class TaskShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "shared_with_user_id")
    private User sharedWithUser;

    @ManyToOne
    @JoinColumn(name = "shared_by_user_id")
    private User sharedByUser;

    @Column(name = "share_date")
    private LocalDateTime shareDate = LocalDateTime.now();
}