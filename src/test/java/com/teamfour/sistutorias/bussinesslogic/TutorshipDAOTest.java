package com.teamfour.sistutorias.bussinesslogic;

import org.junit.jupiter.api.BeforeEach;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorship;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TutorshipDAOTest {

    TutorshipDAO tutorshipDAO = new TutorshipDAO();
    Tutorship tutorship = new Tutorship();
    Period period = new Period();

    @Test
    void addTutorship() {
        period.setIdPeriod(3);
        tutorship.setStart("2020-01-01");
        tutorship.setEnd("2020-01-17");
        tutorship.setPeriodId(period.getIdPeriod());
        boolean flag = false;
        try {
            flag = tutorshipDAO.addTutorship(tutorship);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(flag);
    }

    @Test
    void updateTutorship() {
        period.setIdPeriod(3);

        tutorship.setIdTutorShip(1);

        tutorship.setStart("2020-01-01");
        tutorship.setEnd("2020-01-17");
        tutorship.setPeriodId(period.getIdPeriod());
        boolean flag = false;
        try {
            flag = tutorshipDAO.updateTutorship(tutorship);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}