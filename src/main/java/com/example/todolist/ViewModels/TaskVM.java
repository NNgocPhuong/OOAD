package com.example.todolist.ViewModels;

import java.time.LocalDateTime;

public class TaskVM {
    private Integer taskId;
    private String title;
    private String description;
    private String status;
    private String priority;
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    private LocalDateTime createdAt; // cmt1
    private LocalDateTime updatedAt; // cmt2

    public TaskVM() {
    }

    public TaskVM(Integer taskId, String title, String description, String status, String priority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.taskId = taskId; // cmt3
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
