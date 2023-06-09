package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class AcademicProblemDAOTest {

    AcademicProblemDAO academicProblemDAO;
    ArrayList<AcademicProblem> academicProblems;
    ArrayList<AcademicProblem> registeredAcademicProblems;
    AcademicProblem academicProblem;
    AcademicProblem academicProblem1;
    AcademicProblem academicProblem2;

    @BeforeEach
    void setUp() {
        academicProblemDAO = new AcademicProblemDAO();
        academicProblems = new ArrayList<>();
        registeredAcademicProblems = new ArrayList<>();
        academicProblem = new AcademicProblem();
        academicProblem1 = new AcademicProblem();
        academicProblem2 = new AcademicProblem();
    }

    @Test
    void consultAcademicProblemsByProgram() {
    }

    @Test
    void consultAcademicProblemsByTutor() {
    }

    @Test
    void register() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAcademicProblemsWithoutSolutionByProgram() throws SQLException {
        academicProblem1.setIdAcademicProblem(1);
        registeredAcademicProblems.add(academicProblem1);

        academicProblems = academicProblemDAO.getAcademicProblemsWithoutSolutionByProgram(1);

        for(AcademicProblem academicProblemUV : academicProblems) {
            System.out.println(academicProblemUV.getIdAcademicProblem());
        }
/*
        boolean isValid = true;
        int iterator = 0;
        for(AcademicProblem academicProblemUV : academicProblems) {
            if(academicProblemUV.getIdAcademicProblem() != registeredAcademicProblems.get(iterator).getIdAcademicProblem()) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);

 */
    }

    @Test
    public void registerSolutionToAcademicProblem() throws SQLException {
        boolean isValid = false;
        String solution = "El maestro dedicará tiempo a resolver dudas de los alumnos los martes a las 9am";
        int solutionId = academicProblemDAO.registerSolutionToAcademicProblem(solution);
        if(solutionId != -1)
            isValid = true;
        assertTrue(isValid);
    }

    @Test
    public void linkSolutionToProblems() throws SQLException {
        academicProblem1.setIdAcademicProblem(1);
        assertTrue(academicProblemDAO.linkSolutionToProblems(academicProblem1,1));
    }

    @Test
    public void getAcademicProblemsWithSolutionByProgram() throws SQLException {
        academicProblem1.setIdAcademicProblem(1);
        registeredAcademicProblems.add(academicProblem1);

        academicProblems = academicProblemDAO.getAcademicProblemsWithSolutionByProgram(1);

        boolean isValid = true;
        int iterator = 0;
        for(AcademicProblem academicProblemUV : academicProblems) {
            if(academicProblemUV.getIdAcademicProblem() != registeredAcademicProblems.get(iterator).getIdAcademicProblem()) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }

    @Test
    public void deleteSolution() throws SQLException {
        assertTrue(academicProblemDAO.deleteSolution(2));
    }


    @Test
    void updateAcademicProblem() {
    }

    @Test
    void deleteAcademicProblem() {
    }

    @Test
    public void getAcademicProblemById() throws SQLException {
        academicProblem = academicProblemDAO.getAcademicProblemById(1);
        assertTrue(academicProblem != null);
    }

    @Test
    public void getSolutionById() throws SQLException {
        String solution = academicProblemDAO.getSolutionById(10);
        assertTrue(!solution.equals(""));
    }

    @Test
    void getAcademicProblemsFromRegister() {
    }

    @Test
    public void updateSolution() throws SQLException {
        int updatedSolution = academicProblemDAO.updateSolution(10, "El profesor dedicara sesiones para resolver dudas");
        assertTrue(updatedSolution == 1);
    }

    @Test
    public void unlinkSolutionToProblems() throws SQLException {
        boolean unlinkedSolution = academicProblemDAO.unlinkSolutionToProblems(1,10);
        assertTrue(unlinkedSolution);
    }
}