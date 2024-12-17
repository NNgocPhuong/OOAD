package com.example.todolist.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "group_task")
@Data
@IdClass(GroupTaskId.class) // Chỉ định lớp đại diện khóa chính tổng hợp
public class GroupTask {

    @Id
    @Column(name = "group_id")
    private Integer groupId;

    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    // Các thuộc tính khác nếu cần
}
