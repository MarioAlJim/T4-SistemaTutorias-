package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TutorshipDAO implements ITutorshipDAO {
    @Override
    public int addTutorship(Tutorship tutorship) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int addedRows = 0;
        Connection connection = dataBaseConnection.getConnection();
        String openingDay = tutorship.getStart();
        String closingDay = tutorship.getEnd();
        String query = "INSERT INTO tutorship (start, end) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        if(!openingDay.isEmpty() && !closingDay.isEmpty()){
            statement.setString(1, openingDay);
            statement.setString(2, closingDay);
            addedRows = statement.executeUpdate();
        }
        return addedRows;
    }
}