package com.teamfour.sistutorias.domain;

public class RoleProgram {

    private int idRoleProgram;
    private int role;
    private String nameRole = "";
    private EducationProgram educationProgram = new EducationProgram();
    public RoleProgram(){}
    public RoleProgram(int role, EducationProgram educationProgram, int idRoleProgram, String nameProgram) {
        this.role = role;
        this.educationProgram = educationProgram;
        this.idRoleProgram = idRoleProgram;
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

    public EducationProgram getEducationProgram() {
        return educationProgram;
    }

    public void setEducationProgram(EducationProgram educationProgram) {
        this.educationProgram = educationProgram;
    }

    public int getIdRoleProgram() {
        return idRoleProgram;
    }

    public void setIdRoleProgram(int idRoleProgram) {
        this.idRoleProgram = idRoleProgram;
    }

}
