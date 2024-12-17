package com.example.todolist.Repository;

import com.example.todolist.Models.GroupTask;
import com.example.todolist.Models.GroupTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupTaskRepository extends JpaRepository<GroupTask, GroupTaskId> {
    // Các truy vấn tùy chỉnh nếu cần
}