package com.example.todolist.Models;

import java.io.Serializable;
import java.util.Objects;

public class GroupTaskId implements Serializable {
    private Integer groupId;
    private Integer taskId;

    public GroupTaskId() {}

    public GroupTaskId(Integer groupId, Integer taskId) {
        this.groupId = groupId;
        this.taskId = taskId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTaskId that = (GroupTaskId) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, taskId);
    }
}
