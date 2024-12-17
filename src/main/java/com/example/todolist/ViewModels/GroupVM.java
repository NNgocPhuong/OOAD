package com.example.todolist.ViewModels;
import java.util.List;

public class GroupVM {
    private Integer groupId;
    private String groupName;
    private List<UserVM> members;
    private List<TaskVM> groupTasks;

    // Constructors, getters, and setters

    public GroupVM(Integer groupId, String groupName, List<UserVM> members, List<TaskVM> groupTasks) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = members;
        this.groupTasks = groupTasks;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserVM> getMembers() {
        return members;
    }

    public void setMembers(List<UserVM> members) {
        this.members = members;
    }

    public List<TaskVM> getGroupTasks() {
        return groupTasks;
    }

    public void setGroupTasks(List<TaskVM> groupTasks) {
        this.groupTasks = groupTasks;
    }
}
