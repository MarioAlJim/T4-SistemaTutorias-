package com.teamfour.sistutorias.domain;

public class Group {
    private int group_id;
    private int nrc;
    private EE ee;
    private Teacher teacher;
    private EducativeProgram educativeProgram;
    private String teacherName;
    private String experience;
    private int idPeriod;

    public Group() {
        this.nrc = 0;
        this.teacherName = "";
        this.experience = "";
    }

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

    public EducativeProgram getEducationProgram() {
        return educativeProgram;
    }

    public void setEducationProgram(EducativeProgram educativeProgram) {
        this.educativeProgram = educativeProgram;
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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
