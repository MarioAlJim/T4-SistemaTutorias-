package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.SQLException;

public interface ITutorshipDAO {
    public int addTutorship(Tutorship tutorship) throws SQLException;
}
