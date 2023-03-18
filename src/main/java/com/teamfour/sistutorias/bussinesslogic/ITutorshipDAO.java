package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ITutorshipDAO {

    public ArrayList<Tutorship> getTutorshipByPeriod (int idPeriod) throws SQLException;

    public Tutorship getLatestTutorship() throws SQLException;
}
