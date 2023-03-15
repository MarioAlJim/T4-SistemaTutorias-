package com.teamfour.sistutorias.presentation;

import javafx.scene.control.CheckBox;

public class AcademicProblemsTable {
    private CheckBox checkBox;
    private String academicProblem;
    private String teacher;
    private String ee;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getAcademicProblem() {
        return academicProblem;
    }

    public void setAcademicProblem(String academicProblem) {
        this.academicProblem = academicProblem;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getEE() {
        return ee;
    }

    public void setEE(String ee) {
        this.ee = ee;
    }
}
