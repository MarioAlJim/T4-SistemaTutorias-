package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAcademicProblemDAO {

    public ArrayList<AcademicProblem> consultAcademicProblemsByProgram(int idProgram) throws SQLException;

    public ArrayList<AcademicProblem> consultAcademicProblemsByTutor(int idTutorship, int idProgram, String uvAcount) throws SQLException;

    public int register(AcademicProblem academicProblem) throws SQLException;

    public int update(AcademicProblem academicProblem)throws SQLException;

    public int delete(AcademicProblem academicProblem) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsWithoutSolutionByProgram(int idProgram) throws SQLException;

    public int registerSolutionToAcademicProblem(String solution) throws SQLException;

    public boolean linkSolutionToProblems(AcademicProblem academicProblem, int idSolucion) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsWithSolutionByProgram(int idProgram) throws SQLException;

    public boolean deleteSolution(int idSolution) throws SQLException;
}
