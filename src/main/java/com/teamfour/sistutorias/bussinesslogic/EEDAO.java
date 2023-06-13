package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EEDAO implements IEEDAO {
    @Override
    public ArrayList<EE> getEEsByProgram(int idProgram) throws SQLException {
        ArrayList<EE> ees = new ArrayList<>();
        String query = "SELECT DISTINCT ee.name, ee.ee_id FROM group_program gp " +
                "INNER JOIN ee ee ON ee.ee_id = gp.ee_id " +
                "WHERE gp.program_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ees.add(getEE(resultSet));
        }
        dataBaseConnection.closeConection();
        return ees;
    }

    @Override
    public ArrayList<EE> getAllEe() throws SQLException {
        ArrayList<EE> ees = new ArrayList<>();
        String query = "SELECT name, ee_id FROM ee ";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ees.add(getEE(resultSet));
        }
        dataBaseConnection.closeConection();
        return ees;
    }

    private EE getEE(ResultSet resultSet) throws SQLException {
        EE ee = new EE();
        int ee_id = resultSet.getInt("ee_id");
        String name = resultSet.getString("name");
        ee.setIdEe(ee_id);
        ee.setName(name);
        int id = resultSet.getInt("ee_id");
        ee.setIdEe(id);
        return ee;
    }
    @Override
    public ArrayList<EE> getEEs() throws SQLException{
        ArrayList<EE> ees = new ArrayList<>();
        String query = "SELECT * FROM ee";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ees.add(getEE(resultSet));
        }
        dataBaseConnection.closeConection();
        return ees;
    }
    @Override
    public boolean register(EE newEE) throws SQLException{
        boolean eeWasRegistered = false;
        String query = "INSERT INTO ee (name) VALUES (?)";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newEE.getName());
        int executeUpdate = statement.executeUpdate();
        if(executeUpdate != 0){
            eeWasRegistered = true;
        }
        dataBaseConnection.closeConection();
        return eeWasRegistered;
    }
    @Override
    public boolean update(EE ee) throws SQLException{
        boolean eeWasUpdated = false;
        String query = "UPDATE ee SET name = ? WHERE ee_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ee.getName());
        statement.setInt(2, ee.getIdEe());
        int executeUpdate = statement.executeUpdate();
        if(executeUpdate != 0){
            eeWasUpdated = true;
        }
        dataBaseConnection.closeConection();
        return eeWasUpdated;
    }
    @Override
    public boolean delete(EE ee) throws SQLException{
        boolean eeWasDeleted = false;
        String query = "DELETE FROM ee WHERE ee_id = ?";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ee.getIdEe());
        int executeUpdate = statement.executeUpdate();
        if(executeUpdate != 0){
            eeWasDeleted = true;
        }
        dataBaseConnection.closeConection();
        return eeWasDeleted;
    }
}
