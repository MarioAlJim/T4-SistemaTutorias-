package com.teamfour.sistutorias.domain;

public class Period {
    private int idPeriod;
    private String start;
    private String end;

    public Period() {
        this.setIdPeriod(0);
        this.setStart("");
        this.setEnd("");
    }

    public int getIdPeriod() {
        return idPeriod;
    }

    @Override
    public String toString() {
        return "Periodo " + idPeriod +
                ":" + start  +
                "-" + end ;
    }

    public void setIdPeriod(int idPeriod) {
        this.idPeriod = idPeriod;
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

    public String getFullPeriod() {
        String fullPeriod = "";
        String startDate = getStart().replaceAll("\\s", "");
        String endDate = getEnd().replaceAll("\\s", "");
        if(!startDate.isEmpty() && !endDate.isEmpty())
            fullPeriod = getStart() + " - " + getEnd();
        return fullPeriod;
    }
}
