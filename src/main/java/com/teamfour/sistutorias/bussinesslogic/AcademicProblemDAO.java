package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.AcademicProblem;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AcademicProblemDAO implements IAcademicProblemDAO {

    @Override
    public ArrayList<AcademicProblem> consultAcademicProblemsByProgram(int idProgram) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT ap.academic_problems_id, ap.title, ap.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, s.description as solution " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN group_program gp on gp.nrc = ap.nrc " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "LEFT JOIN problem_solution ps on ps.academic_problem_id " +
                "LEFT JOIN solution s on s.solution_id = ps.solution_id " +
                "where r.educative_program_id = ?");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            while (resultSet.next()) {
                academicProblems.add(getAcademicProblem(resultSet));
            }
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }
    @Override
    public ArrayList<AcademicProblem> consultAcademicProblemsByTutor(int idTutorship, int idProgram, String uvAcount) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int tutorship = idTutorship;
        Connection connection = dataBaseConnection.getConnection();
        System.out.println("conexion");
        String query = "SELECT ap.academic_problems_id, ap.title, ap.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', maternal_surname) as teacher, s.description as solution " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN group_program gp on gp.nrc = ap.nrc " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "LEFT JOIN problem_solution ps on ps.academic_problem_id " +
                "LEFT JOIN solution s on s.solution_id = ps.solution_id " +
                "where r.tutorship_id = ? AND r.email = ? AND r.educative_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorship);
        statement.setString(2, uvAcount);
        statement.setInt(3, idProgram);
        ResultSet resultSet = statement.executeQuery();
        System.out.println("ejecutado");
            while (resultSet.next()) {
                System.out.println("encontrado");
                academicProblems.add(getAcademicProblem(resultSet));
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }


    @Override
    public int register(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = academicProblem.getDescription();
        String title = academicProblem.getTitle();
        int group = academicProblem.getGroup();
        int numberTutorados = academicProblem.getNumberTutorados();
        int register = academicProblem.getRegister();
        String query = "INSERT INTO academic_problems (title, description, numbertutorados, nrc, register_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, numberTutorados);
            statement.setInt(4, group);
        statement.setInt(5 , register);
            insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int update(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = academicProblem.getDescription();
        String title = academicProblem.getTitle();
        int group = academicProblem.getGroup();
        int numberTutorados = academicProblem.getNumberTutorados();
        int register = academicProblem.getRegister();
        int id = academicProblem.getIdAcademicProblem();
        String query = "UPDATE academic_problems SET title = ?, description = ?, numbertutorados = ?, nrc = ?, register_id = ? WHERE academic_problems_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setInt(3, numberTutorados);
        statement.setInt(4, group);
        statement.setInt(5 , register);
        statement.setInt(6, id);
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int delete(AcademicProblem academicProblem) throws SQLException {
        return 0;
    }

    @Override
    public ArrayList<AcademicProblem> getAcademicProblemsWithoutSolutionByProgram(int idProgram) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT ap.academic_problems_id, ap.title, ap.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "pd.start, pd.end " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN tutorship ts on ts.tutorship_id = r.tutorship_id " +
                "INNER JOIN period pd on pd.period_id = ts.period_id " +
                "INNER JOIN group_program gp on gp.nrc = ap.nrc " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "WHERE ap.academic_problems_id NOT IN (SELECT academic_problem_id FROM problem_solution) && r.educative_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            academicProblems.add(getAcademicProblemWithPeriod(resultSet));
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }

    @Override
    public int registerSolutionToAcademicProblem(String solution) throws SQLException {
        int solutionRegistered;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO solution (description) VALUES(?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, solution);
        if(statement.executeUpdate() != 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            solutionRegistered = resultSet.getInt(1);
        } else {
            solutionRegistered = -1;
        }
        dataBaseConnection.closeConection();
        return solutionRegistered;
    }

    @Override
    public int linkSolutionToProblems(AcademicProblem academicProblem, int solutionId) throws SQLException {
        int solutionLinked = -1;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO problem_solution (academic_problem_id, solution_id) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, academicProblem.getIdAcademicProblem());
        statement.setInt(2, solutionId);
        solutionLinked = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return solutionLinked;
    }

    private AcademicProblem getAcademicProblem(ResultSet resultSet) throws SQLException{
        int academicProblemsId = resultSet.getInt("academic_problems_id");
        String title = resultSet.getString("title");
        int number_tutorados = resultSet.getInt("number_tutorados");
        String nameee = resultSet.getString("nameee");
        String teacher = resultSet.getString("teacher");
        int nrc = resultSet.getInt("nrc");
        String description = resultSet.getString("description");
        AcademicProblem academicProblem = new AcademicProblem();
        academicProblem.setIdAcademicProblem(academicProblemsId);
        academicProblem.setTitle(title);
        academicProblem.setDescription(description);
        academicProblem.setGroup(nrc);
        academicProblem.setEe(nameee);
        academicProblem.setTeacher(teacher);
        academicProblem.setNumberTutorados(number_tutorados);
        return academicProblem;
    }

    private AcademicProblem getAcademicProblemWithPeriod(ResultSet resultSet) throws SQLException{
        AcademicProblem academicProblem = getAcademicProblem(resultSet);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Date start = resultSet.getDate("start");
        String startWithFormat = dateFormat.format(start);
        Date end = resultSet.getDate("end");
        String endWithFormat = dateFormat.format(end);
        academicProblem.getPeriod().setStart(startWithFormat);
        academicProblem.getPeriod().setEnd(endWithFormat);
        return academicProblem;
    }
}
