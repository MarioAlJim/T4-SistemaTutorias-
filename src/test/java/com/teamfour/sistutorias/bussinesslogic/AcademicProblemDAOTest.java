package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void registerSolutionToAcademicProblem() throws SQLException {
        boolean isValid = false;
        String solution = "El maestro dedicará tiempo a resolver dudas de los alumnos los martes a las 9am";
        int solutionId = academicProblemDAO.registerSolutionToAcademicProblem(solution);
        if(solutionId != -1)
            isValid = true;
        assertTrue(isValid);
    }

    @Test
    void linkSolutionToProblems() throws SQLException {
        boolean isValid = false;
        academicProblem1.setIdAcademicProblem(1);
        int solutionId = academicProblemDAO.linkSolutionToProblems(academicProblem1,1);
        if(solutionId != -1)
            isValid = true;
        assertTrue(isValid);
    }
}