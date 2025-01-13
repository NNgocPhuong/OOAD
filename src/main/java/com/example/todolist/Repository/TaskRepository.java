package com.example.todolist.Repository;

import com.example.todolist.Models.Task;
import com.example.todolist.ViewModels.TaskVM;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t")
    List<TaskVM> findAll_task();

    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.taskId = :id")
    TaskVM findById_task(int id);

    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.status = :status")
    List<TaskVM> findByStatus_task(String status);

    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.priority = :priority")
    List<TaskVM> findByPriority_task(String priority);

    @Query("DELETE FROM Task t WHERE t.taskId = :id")
    void deleteById_task(int id);

    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t WHERE t.assignedUser.username = :username")
    List<TaskVM> findPersonalTasksByUserId(String username);

    @Query("SELECT new com.example.todolist.ViewModels.TaskVM(t.taskId, t.title, t.description, t.status, t.priority, t.createdAt, t.updatedAt) FROM Task t JOIN GroupTask gt ON t.taskId = gt.task.taskId WHERE gt.group.groupId = :groupId")
    List<TaskVM> findGroupTasksByGroupId(int groupId);

    @Query("SELECT COUNT(ug) > 0 FROM UserGroup ug JOIN User u ON ug.user.userId = u.userId WHERE ug.group.groupId = :groupId AND u.username = :username")
    boolean isUserInGroup(String username, int groupId);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.assignedUser.userId = :userId WHERE t.taskId = :taskId")
    void assignUserToTask(int taskId, int userId);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.assignedUser.username = :username")
    void deleteTasksByUsername(@Param("username") String username);
}