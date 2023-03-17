package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TutoradoDAO implements ITutoradoDAO{
    @Override
    public int register(Tutorado tutorado) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = db.getConnection();
        String registrationNumber = tutorado.getRegistrationNumber();
        String name = tutorado.getName();
        String paternalSurname = tutorado.getPaternalSurname();
        String maternalSurname = tutorado.getMaternalSurname();
        int programId = tutorado.getProgramId();
        String query = "INSERT INTO tutorado (registration_number, name, paternal_surname, maternal_surname, program_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, registrationNumber);
            statement.setString(2, name);
            statement.setString(3, paternalSurname);
            statement.setString(4, maternalSurname);
            statement.setInt(5, programId);
        insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int update(Tutorado tutorado) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Tutorado tutorado) throws SQLException {
        return 0;
    }

    @Override
    public ArrayList<Tutorado> getTutoradosOfTutor(String tutor_id) throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
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
                Tutorado tutorado = new Tutorado(registrationNumber, name, paternalSurname, maternalSurname, programId);
                for (Tutorado value : tutorados) {
                    if (value.getRegistrationNumber().equals(tutorado.getRegistrationNumber())) {
                        tutorado = null;
                        break;
                    }
                }
                if (tutorado != null) {
                    tutorados.add(tutorado);
                }
            }
        return tutorados;
    }
}
