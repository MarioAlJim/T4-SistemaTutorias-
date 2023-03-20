package com.teamfour.sistutorias.domain;

public class AcademicProblem {

    private int idAcademicProblem;
    private int numberTutorados;
    private String title;
    private String description;
    private String solution;
    private int group;
    private int register;
    private String teacher;
    private String ee;
    private Period period;

    public AcademicProblem() {
        this.setIdAcademicProblem(0);
        this.setNumberTutorados(0);
        this.setTitle("");
        this.setDescription("");
        this.setSolution("");
        this.setGroup(0);
        this.setRegister(0);
        this.setTeacher("");
        this.setEe("");
        this.setPeriod(new Period());
    }

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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getNumberTutorados() {
        return numberTutorados;
    }

    public void setNumberTutorados(int numberTutorados) {
        this.numberTutorados = numberTutorados;
    }

    public int getIdAcademicProblem() {
        return idAcademicProblem;
    }

    public void setIdAcademicProblem(int idAcademicProblem) {
        this.idAcademicProblem = idAcademicProblem;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
