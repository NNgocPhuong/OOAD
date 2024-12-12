package com.example.todolist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.Models.GroupTask;

public interface GroupTaskRepository extends JpaRepository<GroupTask, Integer> {
    
}
