package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Period;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPeriodDAO {
    public ArrayList<Period> getPeriods() throws SQLException;
    public Period getCurrentPeriod() throws SQLException;
}
