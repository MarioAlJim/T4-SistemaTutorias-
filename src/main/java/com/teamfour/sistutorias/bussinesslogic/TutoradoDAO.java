package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorado;
import com.teamfour.sistutorias.domain.UserRoleProgram;

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

    @Override
    public ArrayList<Tutorado> getTutoradosByProgramTutor(int idProgram) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
        String query = ("SELECT t.registration_number, p.name, p.paternal_surname, p.maternal_surname " +
                "FROM tutorado t " +
                "INNER JOIN person p ON p.person_id = t.person_id " +
                "WHERE t.registration_number NOT IN (SELECT registration_number FROM tutorado_tutor) && t.program_id = ?");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            do {
                String registrationNumber = resultSet.getString("registration_number");
                String name = resultSet.getString("name");
                String paternalSurname = resultSet.getString("paternal_surname");
                String maternalSurname = resultSet.getString("maternal_surname");

                Tutorado tutorado = new Tutorado();
                tutorado.setRegistrationNumber(registrationNumber);
                tutorado.setName(name);
                tutorado.setPaternalSurname(paternalSurname);
                tutorado.setMaternalSurname(maternalSurname);
                tutorado.setFullName(name.trim() + " " + paternalSurname.trim() + " " + maternalSurname.trim());
                tutorados.add(tutorado);
            } while (resultSet.next());
        }
        return tutorados;
    }

    @Override
    public ArrayList<Tutorado> getTutoradosByNameProgramTutor(String searchedName, int idProgram) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
        String query = ("SELECT * FROM tutorado " +
                "JOIN person ON tutorado.person_id = person.person_id " +
                "RIGHT JOIN tutorado_tutor ON tutorado.registration_number = tutorado_tutor.registration_number " +
                "WHERE program_id = ? AND person.name = ?;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        statement.setString(2, searchedName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            do {
                String registrationNumber = resultSet.getString("registration_number");
                String name = resultSet.getString("name");
                String paternalSurname = resultSet.getString("paternal_surname");
                String maternalSurname = resultSet.getString("maternal_surname");
                int programId = resultSet.getInt("program_id");
                int tutorTutorado_id = resultSet.getInt("tutorado_tutor_id");
                Tutorado tutorado = new Tutorado(registrationNumber, name, paternalSurname, maternalSurname, programId);
                tutorado.setTutor_tutorado_id(tutorTutorado_id);
                tutorados.add(tutorado);
            } while (resultSet.next());
        }
        return tutorados;
    }

    @Override
    public int updateTutor(Tutorado tutorado, String email) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String registration_number = tutorado.getRegistrationNumber();
        String newEmail = email;
        String query = "UPDATE tutorado_tutor SET user_id = ? WHERE registration_number = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newEmail);
        statement.setString(2, registration_number);
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public boolean assignTutor(Tutorado tutorado, UserRoleProgram tutor) throws SQLException {
        boolean registeredAssignment = false;
        String query = "INSERT INTO tutorado_tutor (registration_number, user_id) VALUES (?,?)";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, tutorado.getRegistrationNumber());
        statement.setString(2, tutor.getEmail());
        int executeUpdate = statement.executeUpdate();
        if (executeUpdate != 0){
            registeredAssignment = true;
        }
        dataBaseConnection.closeConection();
        return registeredAssignment;
    }

    @Override
    public ArrayList<Tutorado> getTutoradosWithTutor(int program_id) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
        String query = ("SELECT * FROM tutorado " +
                "INNER JOIN person ON tutorado.person_id = person.person_id " +
                "RIGHT JOIN tutorado_tutor on tutorado.registration_number = tutorado_tutor.registration_number " +
                "WHERE program_id = ?;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, program_id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            do {
                String registrationNumber = resultSet.getString("registration_number");
                String name = resultSet.getString("name");
                String paternalSurname = resultSet.getString("paternal_surname");
                String maternalSurname = resultSet.getString("maternal_surname");
                Tutorado tutorado = new Tutorado();
                tutorado.setRegistrationNumber(registrationNumber);
                tutorado.setName(name);
                tutorado.setPaternalSurname(paternalSurname);
                tutorado.setMaternalSurname(maternalSurname);
                tutorado.setFullName(name.trim() + " " + paternalSurname.trim() + " " + maternalSurname.trim());
                tutorados.add(tutorado);
            } while (resultSet.next());
        }
        return tutorados;
    }
}
