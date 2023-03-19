package com.teamfour.sistutorias.domain;

public class Teacher extends Person{
    private int personalNumber;

    public Teacher() {
        this.setPersonalNumber(0);
        this.setIdPerson(0);
        this.setName("");
        this.setPaternalSurname("");
        this.setMaternalSurname("");
    }

    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "personalNumber=" + personalNumber +
                '}';
    }
}
