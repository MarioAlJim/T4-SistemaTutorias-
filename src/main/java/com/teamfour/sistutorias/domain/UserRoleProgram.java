package com.teamfour.sistutorias.domain;


import java.util.ArrayList;

public class UserRoleProgram extends User{
    ArrayList<RoleProgram> rolesPrograms = new ArrayList<>();

    public UserRoleProgram() {
        this.setEmail("");
        this.setPassword("");
        this.setName("");
        this.setIdPerson(0);
        this.setName("");
        this.setPaternalSurname("");
        this.setMaternalSurname("");
    }

    public String getFullName(){
        return this.getName() + " " + this.getPaternalSurname() + " " + this.getMaternalSurname();
    }

    public ArrayList<RoleProgram> getRolesPrograms() {
        return rolesPrograms;
    }

    public void setRolesPrograms(ArrayList<RoleProgram> rolesPrograms) {
        this.rolesPrograms = rolesPrograms;
    }

    public void addRole(RoleProgram roleProgram) {
        rolesPrograms.add(roleProgram);
    }
}
