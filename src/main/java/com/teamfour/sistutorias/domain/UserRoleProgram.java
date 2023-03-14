package com.teamfour.sistutorias.domain;

public class UserRoleProgram extends User{
    private int idRole;
    private int idProgram;

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
}
