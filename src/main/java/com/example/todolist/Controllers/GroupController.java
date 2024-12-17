package com.example.todolist.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.todolist.Models.Group;
import com.example.todolist.Models.User;
import com.example.todolist.Models.UserGroup;
import com.example.todolist.Repository.GroupRepository;
import com.example.todolist.Repository.UserGroupRepository;
import com.example.todolist.Repository.UserRepository;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    /**
     * API: Lấy danh sách tất cả các nhóm
     */
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll(); // Lấy toàn bộ nhóm
        return ResponseEntity.ok(groups); // Trả về danh sách nhóm
    }

    /**
     * API: Lấy danh sách các nhóm mà người dùng hiện tại tham gia
     */
    @GetMapping("/my-groups")
    public ResponseEntity<List<Group>> getUserGroups(@AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername()); // Lấy thông tin người dùng hiện tại

        if (user == null) {
            return ResponseEntity.status(401).build(); // Người dùng không hợp lệ
        }

        // Lấy danh sách các nhóm mà người dùng tham gia
        List<UserGroup> userGroups = userGroupRepository.findByUserId(user.getUserId());
        List<Group> groups = userGroups.stream()
                .map(UserGroup::getGroup) // Lấy đối tượng Group từ UserGroup
                .collect(Collectors.toList());

        return ResponseEntity.ok(groups); // Trả về danh sách nhóm
    }

    // API tạo nhóm mới
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @AuthenticationPrincipal UserDetails currentUser) {
        // Lấy thông tin người dùng hiện tại
        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Trả về lỗi 401 nếu không tìm thấy
        }

        // Lưu thông tin nhóm vào cơ sở dữ liệu
        group = groupRepository.save(group);

        // Tạo bản ghi UserGroup
        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(user.getUserId());
        userGroup.setGroupId(group.getGroupId());
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroup.setRole("manager"); // Người tạo nhóm là quản lý
        userGroupRepository.save(userGroup);

        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    // API thêm thành viên vào nhóm
    @PostMapping("/{groupId}/members")
    public ResponseEntity<Void> addMemberToGroup(
            @PathVariable int groupId,
            @RequestBody User user,
            @AuthenticationPrincipal UserDetails currentUser) {
        // Lấy thông tin người dùng hiện tại
        User manager = userRepository.findByUsername(currentUser.getUsername());
        if (manager == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Kiểm tra xem người dùng hiện tại có quyền quản lý nhóm hay không
        boolean isManager = userGroupRepository.existsByUserAndGroupAndRole(manager, groupId, "manager");
        if (!isManager) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Nếu không phải quản lý, trả về lỗi 403
        }

        // Kiểm tra nhóm có tồn tại hay không
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy nhóm, trả về lỗi 404
        }

        // Tạo bản ghi UserGroup
        Group group = groupOpt.get();
        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(user.getUserId());
        userGroup.setGroupId(group.getGroupId());
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroup.setRole("member"); // Thêm thành viên với vai trò "member"
        userGroupRepository.save(userGroup);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
