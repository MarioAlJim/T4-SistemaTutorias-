package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.SQLException;

public interface ITutorshipDAO {
    public boolean addTutorship(Tutorship tutorship) throws SQLException;
    public boolean updateTutorship(Tutorship tutorship) throws SQLException;
}
