package com.teamfour.sistutorias.domain;

public class UserRoleProgram extends User{
    private int idRole;
    private int idProgram;

    private String program;

    public UserRoleProgram() {
        this.setIdRole(0);
        this.setIdProgram(0);
        this.setProgram("");
        this.setEmail("");
        this.setPassword("");
        this.setName("");
        this.setIdPerson(0);
        this.setName("");
        this.setPaternalSurname("");
        this.setMaternalSurname("");
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Role:" + idRole +"- Programa=" + program;
    }
}
