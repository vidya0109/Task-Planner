package com.example.planner;

public class Task {


    String Title,Deadline;

    public Task(){

    }

    public Task(String Title,String Deadline){

        this.Title = Title;
        this.Deadline=Deadline;


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



}
