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
    public ArrayList<Tutorship> getTutorshipByPeriod(int idPeriod) throws SQLException {
        ArrayList<Tutorship> tutorships = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM tutorship WHERE period_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idPeriod);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int tutorshipid;
            String start;
            String end;
            do {
                tutorshipid = resultSet.getInt("tutorship_id");
                start = resultSet.getString("start");
                end = resultSet.getString("end");
                Tutorship tutorship = new Tutorship();
                tutorship.setIdTutorShip(tutorshipid);
                tutorship.setStart(start);
                tutorship.setEnd(end);
                tutorships.add(tutorship);
            } while (resultSet.next());
        }
        return tutorships;
    }

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

}
