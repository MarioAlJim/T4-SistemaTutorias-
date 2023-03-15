package com.teamfour.sistutorias.domain;

public class Group {
    private int nrc;
    private EE ee;
    private Teacher teacher;
    private EducationProgram educationProgram;

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public EE getEe() {
        return ee;
    }

    public void setEe(EE ee) {
        this.ee = ee;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public EducationProgram getEducationProgram() {
        return educationProgram;
    }

    public void setEducationProgram(EducationProgram educationProgram) {
        this.educationProgram = educationProgram;
    }
}
