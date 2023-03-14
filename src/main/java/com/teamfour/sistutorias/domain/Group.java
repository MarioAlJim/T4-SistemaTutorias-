package com.teamfour.sistutorias.domain;

public class Group {
    private int personalNumber;
    private int idEe;
    private int nrc;
    private int educativeProgram;

    private String teacher;
    private String ee;

    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public int getIdEe() {
        return idEe;
    }

    public void setIdEe(int idEe) {
        this.idEe = idEe;
    }

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getEducativeProgram() {
        return educativeProgram;
    }

    public void setEducativeProgram(int educativeProgram) {
        this.educativeProgram = educativeProgram;
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
}
