package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TutorshipDAO implements ITutorshipDAO {
    @Override
    public Tutorship getLatestTutorship() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        Tutorship tutorship = new Tutorship();
        String query = "SELECT T.tutorship_id, T.start, T.end FROM tutorship T ORDER BY T.tutorship_id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
            tutorship.setStart(resultSet.getString("start"));
            tutorship.setEnd(resultSet.getString("end"));
        }
        return tutorship;
    }

    @Override
    public ArrayList<Tutorship> getTutorships() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        ArrayList<Tutorship> tutorships = new ArrayList<>();
        String query = "SELECT T.tutorship_id, T.start, T.end FROM tutorship T";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            Tutorship tutorship = new Tutorship();
            tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
            tutorship.setStart(resultSet.getString("start"));
            tutorship.setEnd(resultSet.getString("end"));
            tutorships.add(tutorship);
        }
        return tutorships;
    }
}
