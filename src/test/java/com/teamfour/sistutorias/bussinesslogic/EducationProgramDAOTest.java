package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EducativeProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EducationProgramDAOTest {
    private EducativeProgramDAO educationProgramDAO;
    private ArrayList<EducativeProgram> educativePrograms;
    private EducativeProgram educativeProgram;
    private EducativeProgram educativeProgram1;
    private EducativeProgram educativeProgram2;
    private ArrayList<EducativeProgram> registeredEducativePrograms;
    @BeforeEach
    void setUp() {
        educationProgramDAO = new EducativeProgramDAO();
        educativePrograms = new ArrayList<>();
        registeredEducativePrograms = new ArrayList<>();
        educativeProgram = new EducativeProgram();
        educativeProgram1 = new EducativeProgram();
        educativeProgram2 = new EducativeProgram();
    }

    @Test
    public void getEducationPrograms() throws SQLException {
        educativeProgram1.setName("Ingenieria de software");
        registeredEducativePrograms.add(educativeProgram1);
        educativeProgram2.setName("Estadistica");
        registeredEducativePrograms.add(educativeProgram2);

        educativePrograms = educationProgramDAO.getEducativePrograms();
        boolean isValid = true;
        int iteration = 0;
        for(EducativeProgram educativeProgramUV : educativePrograms) {
            if(!educativeProgramUV.getName().equals(registeredEducativePrograms.get(iteration).getName())) {
                isValid = false;
            }
            iteration++;
        }
        assertTrue(isValid);
    }

    @Test
    public void register() throws SQLException {
        educativeProgram.setName("Ingenieria de software");
        assertTrue(educationProgramDAO.register(educativeProgram) != -1);
    }

    @Test
    public void updateEducationProgram() throws SQLException {
        educativeProgram.setIdEducativeProgram(1);
        educativeProgram.setName("Ingenieria de Software");
        assertTrue(educationProgramDAO.updateEducativeProgram(educativeProgram));
    }
}