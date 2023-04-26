package com.teamfour.sistutorias.domain;

public class EducationProgram {
    private int idEducationProgram;
    private String name;

    public EducationProgram() {
    }

    public EducationProgram(int idEducationProgram, String name) {
        this.idEducationProgram = idEducationProgram;
        this.name = name;
    }

    public int getIdEducationProgram() {
        return idEducationProgram;
    }

    public void setIdEducationProgram(int idEducationProgram) {
        this.idEducationProgram = idEducationProgram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
