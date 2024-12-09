package com.example.todolist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.Models.TaskShare;

public interface TaskShareRepository extends JpaRepository<TaskShare, Integer> {

}
