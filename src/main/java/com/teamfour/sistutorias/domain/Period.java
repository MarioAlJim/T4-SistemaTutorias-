package com.teamfour.sistutorias.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Date getStartDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(start);
        return date;
    }

    public Date getEndDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(end);
        return date;
    }
}
