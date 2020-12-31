package com.lab.todolist.Models;

import java.util.ArrayList;

public class TODOList {
    String Id;
    String Title;
    ArrayList<TODOTask> Tasks = new ArrayList<>();

    public TODOList() {
    }

    public TODOList(String id, String title) {
        Id = id;
        Title = title;
    }

    public TODOList(String id, String title, ArrayList<TODOTask> tasks) {
        Id = id;
        Title = title;
        Tasks = tasks;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<TODOTask> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<TODOTask> tasks) {
        Tasks = tasks;
    }
}
