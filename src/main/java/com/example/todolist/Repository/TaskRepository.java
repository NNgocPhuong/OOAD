package com.example.todolist.Repository;

import com.example.todolist.Models.*;   
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {}
