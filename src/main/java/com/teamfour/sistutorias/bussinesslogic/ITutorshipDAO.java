package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.SQLException;
import java.util.List;

public interface ITutorshipDAO {
    public boolean addTutorship(Tutorship tutorship) throws SQLException;
    public boolean updateTutorship(Tutorship tutorship) throws SQLException;
    public List<Tutorship> getTutorship(int periodId) throws SQLException;
}
