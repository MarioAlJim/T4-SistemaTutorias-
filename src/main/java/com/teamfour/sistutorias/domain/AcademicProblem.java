package com.teamfour.sistutorias.domain;

public class AcademicProblem {

    private String title;
    private String description;
    private String solution;
    private Group group;
    private int register;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }
}
