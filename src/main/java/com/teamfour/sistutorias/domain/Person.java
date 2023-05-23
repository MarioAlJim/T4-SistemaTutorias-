package com.teamfour.sistutorias.domain;

public class Person {

    private int idPerson;
    private String name;
    private String paternalSurname;
    private String maternalSurname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getFullName() {
        String fullName;
        if(this.name.equals("") && this.paternalSurname.equals("") && this.maternalSurname.equals(""))
            fullName = "";
        else
            fullName = this.name.trim() + " " + this.paternalSurname.trim() + " " + this.maternalSurname.trim();
        return fullName;
    }
}
