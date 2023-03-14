package com.teamfour.sistutorias.domain;

public class AcademicProblem {

    private int idAcademicProblem;
    private int numberTutorados;
    private String title;
    private String description;
    private String solution;
    private int group;
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
}
