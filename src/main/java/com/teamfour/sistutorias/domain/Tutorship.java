package com.teamfour.sistutorias.domain;

import java.sql.Date;

public class Tutorship {
    private int idTutorShip;
    private Date start;
    private Date end;
    private int periodId;

    public int getIdTutorShip() {
        return idTutorShip;
    }

    public void setIdTutorShip(int idTutorShip) {
        this.idTutorShip = idTutorShip;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPeriodId() {return periodId;}

    public void setPeriodId(int periodId) {this.periodId = periodId;}

    @Override
    public String toString() {
        return start;
    }
}
