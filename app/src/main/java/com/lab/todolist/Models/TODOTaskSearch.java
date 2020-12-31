package com.lab.todolist.Models;

public class TODOTaskSearch {
    private String listId;
    private String taskId;
    private String listTitle;
    private String taskTitle;
    private boolean isChecked;

    public TODOTaskSearch(String listId, String taskId, String listTitle, String taskTitle, boolean isChecked) {
        this.listId = listId;
        this.taskId = taskId;
        this.listTitle = listTitle;
        this.taskTitle = taskTitle;
        this.isChecked = isChecked;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}