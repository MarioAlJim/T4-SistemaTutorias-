package com.teamfour.sistutorias.domain;

public class RoleProgram {

    private String nameProgram = "";
    private int idRoleProgram;
    private int role;
    private String nameRole = "";
    private int educationProgram;

    public RoleProgram(){}
    public RoleProgram(int role, int educationProgram, int idRoleProgram, String nameProgram) {
        this.role = role;
        this.educationProgram = educationProgram;
        this.idRoleProgram = idRoleProgram;
        this.nameProgram = nameProgram;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
        switch (role) {
            case 1:
                nameRole = "Tutor";
                break;
            case 2:
                nameRole ="Coodrinador";
                break;
            case 3:
                nameRole = "Jefe de carrera";
                break;
        }
    }

    public int getEducationProgram() {
        return educationProgram;
    }

    public void setEducationProgram(int educationProgram) {
        this.educationProgram = educationProgram;
    }

    public int getIdRoleProgram() {
        return idRoleProgram;
    }

    public void setIdRoleProgram(int idRoleProgram) {
        this.idRoleProgram = idRoleProgram;
    }

    public String getNameProgram() {
        return nameProgram;
    }

    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }

    @Override
    public String toString() {
        return "PE: " + nameProgram  +
                ", Rol:" + nameRole;
    }
}
