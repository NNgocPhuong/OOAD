package com.example.todolist.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.todolist.Models.Group;
import com.example.todolist.Models.GroupTask;
import com.example.todolist.Models.Task;
import com.example.todolist.Models.User;
import com.example.todolist.Models.UserGroup;
import com.example.todolist.Models.UserGroupId;
import com.example.todolist.Repository.GroupRepository;
import com.example.todolist.Repository.GroupTaskRepository;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.Repository.UserGroupRepository;
import com.example.todolist.Repository.UserRepository;
import com.example.todolist.ViewModels.GroupVM;
import com.example.todolist.ViewModels.TaskVM;
import com.example.todolist.ViewModels.UserVM;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupTaskRepository groupTaskRepository;

    // Tiện ích để tạo danh sách thành viên từ nhóm
    private List<UserVM> mapMembersFromGroup(Group group) {
        return group.getUserGroups().stream()
                .map(userGroup -> new UserVM(
                        userGroup.getUser().getUserId(),
                        userGroup.getUser().getFullName(),
                        userGroup.getUser().getRole(),
                        userGroup.getUser().getUsername(),
                        userGroup.getUser().getPassword(),
                        userGroup.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    // Tiện ích để tạo danh sách công việc từ nhóm
    private List<TaskVM> mapTasksFromGroup(Group group) {
        return group.getGroupTasks().stream()
                .map(groupTask -> new TaskVM(
                        groupTask.getTask().getTaskId(),
                        groupTask.getTask().getTitle(),
                        groupTask.getTask().getDescription(),
                        groupTask.getTask().getStatus(),
                        groupTask.getTask().getPriority(),
                        groupTask.getTask().getCreatedAt(),
                        groupTask.getTask().getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public ResponseEntity<List<GroupVM>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        List<GroupVM> groupVMs = groups.stream()
                .map(group -> new GroupVM(
                        group.getGroupId(),
                        group.getGroupName(),
                        mapMembersFromGroup(group),
                        mapTasksFromGroup(group)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupVMs);
    }

    @GetMapping("/my-groups")
    public ResponseEntity<List<GroupVM>> getUserGroups(@AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<UserGroup> userGroups = userGroupRepository.findByUserId(user.getUserId());
        List<GroupVM> groupVMs = userGroups.stream()
                .map(ug -> new GroupVM(
                        ug.getGroup().getGroupId(),
                        ug.getGroup().getGroupName(),
                        mapMembersFromGroup(ug.getGroup()),
                        mapTasksFromGroup(ug.getGroup())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupVMs);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        group = groupRepository.save(group);

        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(user.getUserId());
        userGroup.setGroupId(group.getGroupId());
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroup.setRole("manager");
        userGroupRepository.save(userGroup);

        user.setRole("manager");
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<Void> addMemberToGroup(
            @PathVariable int groupId,
            @RequestBody User user,
            @AuthenticationPrincipal UserDetails currentUser) {
        User manager = userRepository.findByUsername(currentUser.getUsername());
        if (manager == null || !userGroupRepository.existsByUserAndGroupAndRole(manager, groupId, "manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(user.getUserId());
        userGroup.setGroupId(group.getGroupId());
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroup.setRole("member");
        userGroupRepository.save(userGroup);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> removeMemberFromGroup(@PathVariable int groupId, @PathVariable int userId) {
        UserGroup userGroup = userGroupRepository.findById(new UserGroupId(userId, groupId)).orElse(null);
        if (userGroup == null) {
            return ResponseEntity.notFound().build();
        }

        userGroupRepository.delete(userGroup);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/tasks")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> assignTaskToGroup(@PathVariable int groupId, @RequestBody Task task) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }

        task = taskRepository.save(task);

        GroupTask groupTask = new GroupTask();
        groupTask.setGroupId(group.getGroupId());
        groupTask.setTaskId(task.getTaskId());
        groupTask.setGroup(group);
        groupTask.setTask(task);
        groupTaskRepository.save(groupTask);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}