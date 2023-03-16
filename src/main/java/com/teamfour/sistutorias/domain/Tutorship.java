package com.teamfour.sistutorias.domain;

public class Tutorship {
    private int idTutorShip;
    private String start;
    private String end;

    public int getIdTutorShip() {
        return idTutorShip;
    }

    public void setIdTutorShip(int idTutorShip) {
        this.idTutorShip = idTutorShip;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return start;
    }
}
