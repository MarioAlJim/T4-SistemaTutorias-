package com.teamfour.sistutorias.domain;

public class RoleProgram {

    private int idRoleProgram;
    private int role;
    private String nameRole = "";
    private EducativeProgram educativeProgram = new EducativeProgram();
    public RoleProgram(){}
    public RoleProgram(int role, EducativeProgram educativeProgram, int idRoleProgram, String nameProgram) {
        this.role = role;
        this.educativeProgram = educativeProgram;
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

    public EducativeProgram getEducationProgram() {
        return educativeProgram;
    }

    public void setEducationProgram(EducativeProgram educativeProgram) {
        this.educativeProgram = educativeProgram;
    }

    public int getIdRoleProgram() {
        return idRoleProgram;
    }

    public void setIdRoleProgram(int idRoleProgram) {
        this.idRoleProgram = idRoleProgram;
    }

}
