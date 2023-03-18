package com.teamfour.sistutorias.domain;

public class UserRoleProgram extends User{
    private int userRoleProgram;
    private int idRole;
    private int idProgram;
    private String program;
    private String descriptioRole;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
        switch (idRole){
            case 1:
                descriptioRole = "Tutor";
                break;
            case 2:
                descriptioRole = "Coordinador";
                break;
            case 3:
                descriptioRole = "Jefe de carrera";
                break;
            case 4:
                descriptioRole = "Admin";
                break;

        }
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

    public int getUserRoleProgram() {
        return userRoleProgram;
    }

    public void setUserRoleProgram(int userRoleProgram) {
        this.userRoleProgram = userRoleProgram;
    }

    public String getDescriptioRole() {
        return descriptioRole;
    }

    @Override
    public String toString() {
        return "Role:" + descriptioRole +"- Programa=" + program;
    }
}
