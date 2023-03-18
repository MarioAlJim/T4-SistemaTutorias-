package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorship;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITutorshipDAO {
    public Tutorship getLatestTutorship() throws SQLException;
    public ArrayList<Tutorship> getTutorshipByPeriod (int idPeriod) throws SQLException;
    public boolean addTutorship(Tutorship tutorship) throws SQLException;
    public boolean updateTutorship(Tutorship tutorship) throws SQLException;
}
