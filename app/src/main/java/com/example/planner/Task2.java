package com.example.planner;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
public class Task2 implements Serializable{


    String Title,Deadline,Subtask;
    boolean expanded ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Exclude
    private String id;
    public Task2(){

    }

    public Task2(String Title,String Deadline,String Subtask){

        this.Title = Title;
        this.Deadline=Deadline;
        this.Subtask = Subtask;
        this.expanded = false;

    }
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }

    public String getSubtask() {
        return Subtask;
    }

    public void setSubtask(String Subtask) {
        this.Subtask = Subtask;
    }

}
