package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Register;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class RegisterDAO implements IRegisterDAO{
    @Override
    public Register getRegister(Register register) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        int registerId = register.getRegister_id();
        String query = "SELECT * FROM register WHERE register_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerId);
        statement.executeQuery();
        dataBaseConnection.closeConection();
        return register;
    }

    @Override
    public Register getLatestRegister() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Register register = new Register();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM register ORDER BY register_id DESC LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            register.setRegister_id(resultSet.getInt("register_id"));
            register.setEmail(resultSet.getString("email"));
            register.setTutorship_id(resultSet.getInt("tutorship_id"));
            register.setEducative_program_id(resultSet.getInt("educative_program_id"));
        }
        dataBaseConnection.closeConection();
        return register;
    }

    @Override
    public int register(Register register) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String email = register.getEmail();
        int tutorshipId = register.getTutorship_id();
        int educativeProgramId = register.getEducative_program_id();
        String query = "INSERT INTO register (email, tutorship_id, educative_program_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setInt(2, tutorshipId);
        statement.setInt(3, educativeProgramId);
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int update(Register register) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int registerId = register.getRegister_id();
        String email = register.getEmail();
        int tutorshipId = register.getTutorship_id();
        int educativeProgramId = register.getEducative_program_id();
        String query = "UPDATE register SET email = ?, tutorship_id = ?, educative_program_id = ? WHERE register_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setInt(2, tutorshipId);
        statement.setInt(3, educativeProgramId);
        statement.setInt(4, registerId);
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int delete(Register register) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int registerId = register.getRegister_id();
        String query = "DELETE FROM register WHERE register_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerId);
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    public List<Register> getTutorshipRegister(int tutorshipId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        List<Register> registerList = new ArrayList<>();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM register WHERE tutorship_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorshipId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Register register = new Register();
            register.setRegister_id(resultSet.getInt("register_id"));
            register.setEmail(resultSet.getString("email"));
            register.setTutorship_id(resultSet.getInt("tutorship_id"));
            register.setEducative_program_id(resultSet.getInt("educative_program_id"));
            registerList.add(register);
        }
        dataBaseConnection.closeConection();
        return registerList;
    }

    public List<Register> getTutorshipRegister(int tutorshipId, String email, int educativeProgramId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        List<Register> registerList = new ArrayList<>();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM register WHERE tutorship_id = ? AND email = ? AND educative_program_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorshipId);
        statement.setString(2, email);
        statement.setInt(3, educativeProgramId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Register register = new Register();
            register.setRegister_id(resultSet.getInt("register_id"));
            register.setEmail(resultSet.getString("email"));
            register.setTutorship_id(resultSet.getInt("tutorship_id"));
            register.setEducative_program_id(resultSet.getInt("educative_program_id"));
            registerList.add(register);
        }
        dataBaseConnection.closeConection();
        return registerList;
    }

    @Override
    public Register getSpecificRegister(Register register) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT register_id FROM register WHERE email = ? AND tutorship_id = ? AND educative_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, register.getEmail());
        statement.setInt(2, register.getTutorship_id());
        statement.setInt(3, register.getEducative_program_id());
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            register.setRegister_id(resultSet.getInt("register_id"));
        }
        dataBaseConnection.closeConection();
        return register;
    }
}
