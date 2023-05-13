package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAcademicProblemDAO {

    public ArrayList<AcademicProblem> consultAcademicProblemsByProgram(int idProgram) throws SQLException;

    public ArrayList<AcademicProblem> consultAcademicProblemsByTutor(int idTutorship, int idProgram, String uvAcount) throws SQLException;

    public int registerAcademicProblem(AcademicProblem academicProblem) throws SQLException;

    public int updateAcademicProblem(AcademicProblem academicProblem)throws SQLException;

    public int deleteAcademicProblem(int idAcademicProblem) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsWithoutSolutionByProgram(int idProgram) throws SQLException;

    public int registerSolutionToAcademicProblem(String solution) throws SQLException;

    public boolean linkSolutionToProblems(AcademicProblem academicProblem, int idSolucion) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsWithSolutionByProgram(int idProgram) throws SQLException;

    public boolean deleteSolution(int idSolution) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsFromRegister(int registerId) throws SQLException;

    public AcademicProblem getAcademicProblemById(int academicProblemId) throws SQLException;

    public String getSolutionById(int solutionId) throws SQLException;

    public int updateSolution(int solutionId, String solution) throws SQLException;

    public boolean unlinkSolutionToProblems(int academicProblemId, int solutionId) throws SQLException;
}
