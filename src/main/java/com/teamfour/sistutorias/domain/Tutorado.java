package com.teamfour.sistutorias.domain;

public class Tutorado extends Person{
    private String registrationNumber;
    private String fullName;
    private int programId;
    private int tutor_tutorado_id;

    public Tutorado(String registrationNumber, String name, String paternalSurname, String maternalSurname, int programId) {
        this.registrationNumber = registrationNumber;
        this.setName(name);
        this.setPaternalSurname(paternalSurname);
        this.setMaternalSurname(maternalSurname);
        this.programId = programId;
        this.fullName = name + " " + paternalSurname + " " + maternalSurname;
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

    @Override
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getTutor_tutorado_id() {
        return tutor_tutorado_id;
    }

    public void setTutor_tutorado_id(int tutor_tutorado_id) {
        this.tutor_tutorado_id = tutor_tutorado_id;
    }
}
