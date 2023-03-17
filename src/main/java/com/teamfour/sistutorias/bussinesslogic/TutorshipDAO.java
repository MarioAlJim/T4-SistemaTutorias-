package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorship;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TutorshipDAO implements ITutorshipDAO {
    @Override
    public boolean addTutorship(Tutorship tutorship) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        boolean flag = false;
        String openingDay = tutorship.getStart();
        String closingDay = tutorship.getEnd();
        int period = tutorship.getPeriodId();
        String query = "INSERT INTO tutorship (start, end, period_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        if(!openingDay.isEmpty() && !closingDay.isEmpty() && period != 0){
            int result;
            statement.setString(1, openingDay);
            statement.setString(2, closingDay);
            statement.setInt(3, period);
            result = statement.executeUpdate();
            if(result == 0){
                throw new SQLException("No se pudo registrar la tutoría");
            } else {
                flag= true;
            }
        }
        dataBaseConnection.closeConection();
        return flag;
    }
    @Override
    public boolean updateTutorship(Tutorship tutorship) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        boolean flag = false;
        String openingDay = tutorship.getStart();
        String closingDay = tutorship.getEnd();
        int period = tutorship.getPeriodId();
        int tutorshipId = tutorship.getIdTutorShip();
        String query = "UPDATE tutorship SET start = ?, end = ?, period_id = ? WHERE tutorship_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        if(!openingDay.isEmpty() && !closingDay.isEmpty() && period != 0){
            int result;
            statement.setString(1, openingDay);
            statement.setString(2, closingDay);
            statement.setInt(3, period);
            statement.setInt(4, tutorshipId);
            result = statement.executeUpdate();
            if(result == 0){
                throw new SQLException("No se pudo modififcar la tutoría");
            } else {
                flag= true;
            }
        }
        dataBaseConnection.closeConection();
        return flag;
    }

    public boolean isValidDate(String date){
        return true;
    }
}