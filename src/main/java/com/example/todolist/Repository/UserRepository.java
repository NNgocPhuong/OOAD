package com.example.todolist.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.todolist.Models.User;
import com.example.todolist.ViewModels.UserVM;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT new com.example.todolist.ViewModels.UserVM(u.userId, u.fullName, u.role, u.username, u.password, u.email) FROM User u")
    List<UserVM> findAll_user();

    @Query("SELECT new com.example.todolist.ViewModels.UserVM(u.userId, u.fullName, u.role, u.username, u.password, u.email) FROM User u WHERE u.userId = :id")
    UserVM findUserById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.userId = :id")
    User findUserEntityById(@Param("id") Integer id);
}
