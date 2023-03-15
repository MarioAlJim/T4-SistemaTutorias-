package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Assistance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AssistanceDAO implements IAssistanceDAO {
    @Override
    public int register(Assistance assistance) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int registration_id = assistance.getRegistration_id();
        Boolean asiss = assistance.getAsistencia();
        Boolean risk = assistance.getRiesgo();
        int register_id = assistance.getRegister_id();
        String query = "INSERT INTO assistance (registration_id, asistencia, riesgo, register_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, registration_id);
            statement.setBoolean(2, asiss);
            statement.setBoolean(3, risk);
            statement.setInt(4, register_id);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int update(Assistance assistance) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int registration_id = assistance.getRegistration_id();
        Boolean asiss = assistance.getAsistencia();
        Boolean risk = assistance.getRiesgo();
        int register_id = assistance.getRegister_id();
        int id = assistance.getAssistance_id();
        String query = "UPDATE assistance SET registration_id = ?, asistencia = ?, riesgo = ?, register_id = ? WHERE assistance_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, registration_id);
            statement.setBoolean(2, asiss);
            statement.setBoolean(3, risk);
            statement.setInt(4, register_id);
            statement.setInt(5, id);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int delete(Assistance assistance) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int id = assistance.getAssistance_id();
        String query = "DELETE FROM assistance WHERE assistance_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }
}
