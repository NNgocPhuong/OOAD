package com.example.todolist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.Models.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
