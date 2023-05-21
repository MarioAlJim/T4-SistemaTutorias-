package com.teamfour.sistutorias.domain;

public class EducativeProgram {
    private int idEducativeProgram;
    private String name;

    public EducativeProgram() {
    }

    public EducativeProgram(int idEducativeProgram, String name) {
        this.idEducativeProgram = idEducativeProgram;
        this.name = name;
    }

    public int getIdEducativeProgram() {
        return idEducativeProgram;
    }

    public void setIdEducativeProgram(int idEducativeProgram) {
        this.idEducativeProgram = idEducativeProgram;
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
