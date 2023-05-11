package com.teamfour.sistutorias.domain;

public class Group {
    private int nrc;
    private EE ee;
    private Teacher teacher;
    private EducationProgram educationProgram;
    private String teacherName;
    private String experience;
    private int idPeriod;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "NRC:" + nrc + " - " + teacherName + " - " + experience;
    }

    public int getIdPeriod() {
        return idPeriod;
    }

    public void setIdPeriod(int idPeriod) {
        this.idPeriod = idPeriod;
    }
}
