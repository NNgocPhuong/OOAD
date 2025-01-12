package com.example.todolist.Repository;

import com.example.todolist.Models.User;
import com.example.todolist.Models.UserGroup;
import com.example.todolist.Models.UserGroupId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    @Query("SELECT COUNT(ug) > 0 FROM UserGroup ug WHERE ug.user = :user AND ug.group.groupId = :groupId AND ug.role = :role")
    boolean existsByUserAndGroupAndRole(@Param("user") User user, @Param("groupId") int groupId, @Param("role") String role);

    @Query("SELECT ug FROM UserGroup ug WHERE ug.group.groupId = :groupId")
    List<UserGroup> findByGroupId(@Param("groupId") Integer groupId);

    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userId = :userId")
    List<UserGroup> findByUserId(@Param("userId") Integer userId);
}