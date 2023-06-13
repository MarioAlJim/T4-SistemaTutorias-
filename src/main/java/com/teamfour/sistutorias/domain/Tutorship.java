package com.teamfour.sistutorias.domain;

public class Tutorship {
    private int idTutorShip;
    private String start;
    private String end;
    private int periodId;

    public Tutorship (){
        idTutorShip = 0;
        start = "";
        end = "";
    }
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

    public int getPeriodId() {return periodId;}

    public void setPeriodId(int periodId) {this.periodId = periodId;}

    @Override
    public String toString() {
        return start;
    }
}
