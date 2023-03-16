package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Assistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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

    @Override
    public ArrayList<Assistance> getAssistanceTutor(String tutor_id) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        ArrayList<Assistance> tutoradosAsistencia = new ArrayList<>();
        String query = "SELECT * FROM tutorado\n" +
                "JOIN person ON tutorado.person_id = person.person_id\n" +
                "JOIN tutorado_tutor WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, tutor_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String registrationNumber = resultSet.getString("registration_number");
            String name = resultSet.getString("name");
            String paternalSurname = resultSet.getString("paternal_surname");
            String maternalSurname = resultSet.getString("maternal_surname");
            int programId = resultSet.getInt("program_id");
            Assistance tutorado = new Assistance(registrationNumber, name, paternalSurname, maternalSurname, programId);
            for (Assistance assistance : tutoradosAsistencia) {
                if (assistance.getRegistrationNumber().equals(tutorado.getRegistrationNumber())) {
                    tutorado = null;
                    break;
                }
            }
            if (tutorado != null) {
                tutoradosAsistencia.add(tutorado);
            }
        }
        return tutoradosAsistencia;
    }
}
