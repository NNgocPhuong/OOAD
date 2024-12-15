package com.example.todolist.Models;


import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_group")
@Data
@IdClass(UserGroupId.class)
public class UserGroup {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "group_id")
    private Integer groupId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @Column(name = "role")
    private String role = "member"; // member, manager
}