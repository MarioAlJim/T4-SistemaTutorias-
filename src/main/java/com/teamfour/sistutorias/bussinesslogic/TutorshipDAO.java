package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class TutorshipDAO implements ITutorshipDAO {

    @Override
    public Tutorship getLatestTutorship() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        Tutorship tutorship = new Tutorship();
        String query = "SELECT * FROM tutorship ORDER BY tutorship_id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
            tutorship.setStart(resultSet.getString("start"));
            tutorship.setEnd(resultSet.getString("end"));
            tutorship.setPeriodId(resultSet.getInt("period_id"));
        }
        db.closeConection();
        return tutorship;
    }

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
        dataBaseConnection.closeConection();
        return tutorships;
    }

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
        if (!openingDay.isEmpty() && !closingDay.isEmpty() && period != 0) {
            int result;
            statement.setString(1, openingDay);
            statement.setString(2, closingDay);
            statement.setInt(3, period);
            result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No se pudo registrar la tutoría");
            } else {
                flag = true;
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
        if (!openingDay.isEmpty() && !closingDay.isEmpty() && period != 0) {
            int result;
            statement.setString(1, openingDay);
            statement.setString(2, closingDay);
            statement.setInt(3, period);
            statement.setInt(4, tutorshipId);
            result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No se pudo modififcar la tutoría");
            } else {
                flag = true;
            }
        }
        dataBaseConnection.closeConection();
        return flag;
    }

    @Override
    public ArrayList<Tutorship> getTutorships() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        ArrayList<Tutorship> tutorships = new ArrayList<>();
        String query = "SELECT * FROM tutorship";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            Tutorship tutorship = new Tutorship();
            tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
            tutorship.setStart(resultSet.getString("start"));
            tutorship.setEnd(resultSet.getString("end"));
            tutorship.setPeriodId(resultSet.getInt("period_id"));
            tutorships.add(tutorship);
        }
        db.closeConection();
        return tutorships;
    }

    public List<Tutorship> getTutorship(int periodId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM tutorship WHERE period_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, periodId);
        ResultSet resultSet = statement.executeQuery();
        List<Tutorship> listTutorships = new ArrayList<>();
        if (resultSet.next()) {
            do {
                Tutorship tutorship = new Tutorship();
                tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
                tutorship.setStart(resultSet.getString("start"));
                tutorship.setEnd(resultSet.getString("end"));
                tutorship.setPeriodId(resultSet.getInt("period_id"));
                listTutorships.add(tutorship);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return listTutorships;
    }


    @Override
    public Tutorship getCurrentTutorship(int period_id) throws SQLException {
        Tutorship tutorship = new Tutorship();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM tutorship T WHERE T.period_id = ? AND CURDATE() BETWEEN T.start AND T.end";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, period_id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            tutorship.setIdTutorShip(resultSet.getInt("tutorship_id"));
            tutorship.setStart(resultSet.getString("start"));
            tutorship.setEnd(resultSet.getString("end"));
            tutorship.setPeriodId(resultSet.getInt("period_id"));
        }
        return tutorship;
    }

}
