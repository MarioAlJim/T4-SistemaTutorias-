package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.AcademicProblem;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AcademicProblemDAO implements IAcademicProblemDAO {

    @Override
    public ArrayList<AcademicProblem> consultAcademicProblemsByTutor(int idTutorship, int idProgram, String uvAcount) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT ap.academic_problems_id, ap.title, gp.nrc, ap.description, ap.number_tutorados, r.register_id, " +
                "ee.name AS nameee, concat(p.name, ' ', p.paternal_surname, ' ', maternal_surname) AS teacher, s.description AS solution " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN group_program gp on gp.group_id = ap.group_id " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "LEFT JOIN problem_solution ps on ps.academic_problem_id " +
                "LEFT JOIN solution s on s.solution_id = ps.solution_id " +
                "WHERE r.tutorship_id = ? AND r.email = ? AND r.educative_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idTutorship);
        statement.setString(2, uvAcount);
        statement.setInt(3, idProgram);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            do {
                AcademicProblem academicProblem = new AcademicProblem();
                academicProblem.setIdAcademicProblem( resultSet.getInt("academic_problems_id"));
                academicProblem.setTitle(resultSet.getString("title"));
                academicProblem.setDescription(resultSet.getString("description"));
                academicProblem.setNumberTutorados(resultSet.getInt("number_tutorados"));
                academicProblem.setGroup(resultSet.getInt("nrc"));
                academicProblem.setEe(resultSet.getString("nameee"));
                academicProblem.setTeacher(resultSet.getString("teacher"));
                academicProblem.setRegister(resultSet.getInt("register_id"));
                academicProblem.setSolution(resultSet.getString("solution"));
                academicProblems.add(academicProblem);
            } while (resultSet.next());
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }

    @Override
    public int registerAcademicProblem(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO academic_problems (title, description, number_tutorados, group_id, register_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, academicProblem.getTitle());
            statement.setString(2, academicProblem.getDescription());
            statement.setInt(3, academicProblem.getNumberTutorados());
            statement.setInt(4, academicProblem.getGroup());
            statement.setInt(5, academicProblem.getRegister());
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int updateAcademicProblem(AcademicProblem academicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE academic_problems SET title = ?, description = ?, number_tutorados = ?, group_id = ? WHERE academic_problems_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, academicProblem.getTitle());
        statement.setString(2, academicProblem.getDescription());
        statement.setInt(3, academicProblem.getNumberTutorados());
        statement.setInt(4, academicProblem.getGroup());
        statement.setInt(5, academicProblem.getIdAcademicProblem());
        insertedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return insertedFiles;
    }

    @Override
    public int deleteAcademicProblem(int idAcademicProblem) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int modifiedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM academic_problems WHERE (academic_problems_id = ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idAcademicProblem);
        modifiedFiles = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return modifiedFiles;
    }

    @Override
    public ArrayList<AcademicProblem> getAcademicProblemsWithoutSolutionByProgram(int idProgram) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT ap.academic_problems_id, ap.title, gp.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "pd.period_id, pd.start, pd.end " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN tutorship ts on ts.tutorship_id = r.tutorship_id " +
                "INNER JOIN period pd on pd.period_id = ts.period_id " +
                "INNER JOIN group_program gp on gp.group_id = ap.group_id " +
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
    public boolean linkSolutionToProblems(AcademicProblem academicProblem, int solutionId) throws SQLException {
        boolean solutionLinked = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO problem_solution (academic_problem_id, solution_id) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, academicProblem.getIdAcademicProblem());
        statement.setInt(2, solutionId);
        if(statement.executeUpdate() != 0)
            solutionLinked = true;
        dataBaseConnection.closeConection();
        return solutionLinked;
    }

    public ArrayList<AcademicProblem> getAcademicProblemsWithSolutionByProgram(int idProgram) throws SQLException {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT ap.academic_problems_id, ap.title, gp.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "pd.period_id, pd.start, pd.end, s.solution_id, s.description AS solution_description " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN tutorship ts on ts.tutorship_id = r.tutorship_id " +
                "INNER JOIN period pd on pd.period_id = ts.period_id " +
                "INNER JOIN group_program gp on gp.group_id = ap.group_id " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "INNER JOIN problem_solution ps ON ps.academic_problem_id = ap.academic_problems_id " +
                "INNER JOIN solution s ON s.solution_id = ps.solution_id " +
                "WHERE r.educative_program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProgram);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            academicProblems.add(getAcademicProblemWithSolutionAndPeriod(resultSet));
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }

    public boolean deleteSolution(int idSolution) throws SQLException {
        boolean solutionDeleted = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM solution WHERE solution_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idSolution);
        if(statement.executeUpdate() != 0)
            solutionDeleted = true;
        dataBaseConnection.closeConection();
        return solutionDeleted;
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
        int idPeriod = resultSet.getInt("period_id");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Date start = resultSet.getDate("start");
        String startWithFormat = dateFormat.format(start);
        Date end = resultSet.getDate("end");
        String endWithFormat = dateFormat.format(end);
        academicProblem.getPeriod().setIdPeriod(idPeriod);
        academicProblem.getPeriod().setStart(startWithFormat);
        academicProblem.getPeriod().setEnd(endWithFormat);
        return academicProblem;
    }

    private AcademicProblem getAcademicProblemWithSolutionAndPeriod(ResultSet resultSet) throws SQLException {
        AcademicProblem academicProblem = getAcademicProblemWithPeriod(resultSet);
        int idSolution = resultSet.getInt("solution_id");
        String solutionDescription = resultSet.getString("solution_description");
        academicProblem.setIdSolution(idSolution);
        academicProblem.setSolution(solutionDescription);
        return academicProblem;
    }

    @Override
    public ArrayList<AcademicProblem> getAcademicProblemsFromRegister(int registerId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        String query = "SELECT ap.title, ap.description, ap.number_tutorados, p.name, p.paternal_surname, " +
                "p.maternal_surname, ee.name AS eename, gp.nrc FROM academic_problems ap " +
                "INNER JOIN group_program gp on gp.group_id = ap.group_id " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "WHERE ap.register_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            AcademicProblem academicProblem = new AcademicProblem();
            academicProblem.setTitle(resultSet.getString("title"));
            academicProblem.setDescription(resultSet.getString("description"));
            academicProblem.setNumberTutorados(resultSet.getInt("number_tutorados"));
            String teachername = resultSet.getString("name") + " " +
                    resultSet.getString("paternal_surname") + " " +
                    resultSet.getString("maternal_surname");
            academicProblem.setTeacher(teachername);
            academicProblem.setEe(resultSet.getString("eename"));
            academicProblem.setGroup(resultSet.getInt("nrc"));
            academicProblems.add(academicProblem);
        }
        dataBaseConnection.closeConection();
        return academicProblems;
    }


    @Override
    public AcademicProblem getAcademicProblemById(int academicProblemId) throws SQLException {
        AcademicProblem academicProblem = new AcademicProblem();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT ap.academic_problems_id, ap.title, gp.nrc, ap.description, ap.number_tutorados, " +
                "ee.name as nameee, concat(p.name, ' ', p.paternal_surname, ' ', p.maternal_surname) as teacher, " +
                "pd.period_id, pd.start, pd.end " +
                "FROM academic_problems ap " +
                "INNER JOIN register r on ap.register_id = r.register_id " +
                "INNER JOIN tutorship ts on ts.tutorship_id = r.tutorship_id " +
                "INNER JOIN period pd on pd.period_id = ts.period_id " +
                "INNER JOIN group_program gp on gp.group_id = ap.group_id " +
                "INNER JOIN ee on ee.ee_id = gp.ee_id " +
                "INNER JOIN teacher t on t.personal_number = gp.personal_number " +
                "INNER JOIN person p on p.person_id = t.person_id " +
                "WHERE ap.academic_problems_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, academicProblemId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            academicProblem = getAcademicProblemWithPeriod(resultSet);
        dataBaseConnection.closeConection();
        return academicProblem;
    }

    @Override
    public String getSolutionById(int solutionId) throws SQLException {
        String solution = "";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT s.description FROM solution s " +
                "WHERE solution_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, solutionId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            solution = resultSet.getString("description");
        dataBaseConnection.closeConection();
        return solution;
    }

    @Override
    public int updateSolution(int solutionId, String solution) throws SQLException {
        int solutionUpdated = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE solution " +
                "SET description = ? " +
                "WHERE solution_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, solution);
        statement.setInt(2, solutionId);
        solutionUpdated = statement.executeUpdate();
        dataBaseConnection.closeConection();
        return solutionUpdated;
    }

    @Override
    public boolean unlinkSolutionToProblems(int academicProblemId, int solutionId) throws SQLException {
        boolean unlinkedSolution = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM problem_solution " +
                "WHERE academic_problem_id = ? && solution_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, academicProblemId);
        statement.setInt(2, solutionId);
        if(statement.executeUpdate() != 0)
            unlinkedSolution = true;
        dataBaseConnection.closeConection();
        return unlinkedSolution;
    }


}
