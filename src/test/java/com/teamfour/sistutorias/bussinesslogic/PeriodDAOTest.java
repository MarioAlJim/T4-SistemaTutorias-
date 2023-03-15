package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PeriodDAOTest {
    PeriodDAO periodDAO;
    ArrayList<Period> periods;
    ArrayList<Period> registeredPeriods;
    Period period;
    Period period1;
    Period period2;
    @BeforeEach
    void setUp() {
        periodDAO = new PeriodDAO();
        periods = new ArrayList<>();
        registeredPeriods = new ArrayList<>();
        period = new Period();
        period1 = new Period();
        period2 = new Period();
    }

    @Test
    public void getPeriods() throws SQLException {
        period1.setStart("February 2022");
        registeredPeriods.add(period1);
        period2.setStart("August 2022");
        registeredPeriods.add(period2);

        periods = periodDAO.getPeriods();
        boolean isValid = true;
        int iterator = 0;
        for(Period periodUV : periods) {
            if(!periodUV.getStart().equals(registeredPeriods.get(iterator).getStart())) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }
}