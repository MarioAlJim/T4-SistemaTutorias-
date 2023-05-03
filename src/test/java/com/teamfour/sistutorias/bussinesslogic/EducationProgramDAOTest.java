package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EducationProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EducationProgramDAOTest {
    private EducationProgramDAO educationProgramDAO;
    private ArrayList<EducationProgram> educationPrograms;
    private EducationProgram educationProgram;
    private EducationProgram educationProgram1;
    private EducationProgram educationProgram2;
    private ArrayList<EducationProgram> registeredEducationPrograms;
    @BeforeEach
    void setUp() {
        educationProgramDAO = new EducationProgramDAO();
        educationPrograms = new ArrayList<>();
        registeredEducationPrograms = new ArrayList<>();
        educationProgram = new EducationProgram();
        educationProgram1 = new EducationProgram();
        educationProgram2 = new EducationProgram();
    }

    @Test
    public void getEducationPrograms() throws SQLException {
        educationProgram1.setName("Ingenieria de software");
        registeredEducationPrograms.add(educationProgram1);
        educationProgram2.setName("Estadistica");
        registeredEducationPrograms.add(educationProgram2);

        educationPrograms = educationProgramDAO.getEducationPrograms();
        boolean isValid = true;
        int iteration = 0;
        for(EducationProgram educationProgramUV : educationPrograms) {
            if(!educationProgramUV.getName().equals(registeredEducationPrograms.get(iteration).getName())) {
                isValid = false;
            }
            iteration++;
        }
        assertTrue(isValid);
    }

    @Test
    public void register() throws SQLException {
        educationProgram.setName("Ingenieria de software");
        assertTrue(educationProgramDAO.register(educationProgram) != -1);
    }

    @Test
    public void updateEducationProgram() throws SQLException {
        educationProgram.setIdEducationProgram(1);
        educationProgram.setName("Ingenieria de Software");
        assertTrue(educationProgramDAO.updateEducationProgram(educationProgram));
    }
}