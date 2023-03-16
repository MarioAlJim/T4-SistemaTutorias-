package com.teamfour.sistutorias.domain;

public class Tutorado extends Person{
    private String registrationNumber;
    private int programId;

    public Tutorado(String registrationNumber, String name, String paternalSurname, String maternalSurname, int programId) {
        this.registrationNumber = registrationNumber;
        this.setName(name);
        this.setPaternalSurname(paternalSurname);
        this.setMaternalSurname(maternalSurname);
        this.programId = programId;
    }

    public Tutorado() {
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
}
