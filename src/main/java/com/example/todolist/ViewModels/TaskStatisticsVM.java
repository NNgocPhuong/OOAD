package com.example.todolist.ViewModels;

public class TaskStatisticsVM {
    private Integer statisticId;
    private Integer totalTasks = 0;
    private Integer completedTasks = 0;
    private Integer pendingTasks = 0;
    private Integer importantTasks = 0;
    
    public TaskStatisticsVM() {
    }
    public TaskStatisticsVM(Integer statisticId, Integer totalTasks, Integer completedTasks, Integer pendingTasks,
            Integer importantTasks) {
        this.statisticId = statisticId;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.importantTasks = importantTasks;
    }
    
    public Integer getStatisticId() {
        return statisticId;
    }
    public void setStatisticId(Integer statisticId) {
        this.statisticId = statisticId;
    }
    public Integer getTotalTasks() {
        return totalTasks;
    }
    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }
    public Integer getCompletedTasks() {
        return completedTasks;
    }
    public void setCompletedTasks(Integer completedTasks) {
        this.completedTasks = completedTasks;
    }
    public Integer getPendingTasks() {
        return pendingTasks;
    }
    public void setPendingTasks(Integer pendingTasks) {
        this.pendingTasks = pendingTasks;
    }
    public Integer getImportantTasks() {
        return importantTasks;
    }
    public void setImportantTasks(Integer importantTasks) {
        this.importantTasks = importantTasks;
    }
}
