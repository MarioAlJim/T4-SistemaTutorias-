package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.UserRoleProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleProgramDAOTest {
    UserRoleProgramDAO userRoleProgramDAO;
    ArrayList<UserRoleProgram> tutors;
    ArrayList<UserRoleProgram> registeredTutors;
    UserRoleProgram tutor;
    UserRoleProgram tutor1;

    @BeforeEach
    void setUp() {
        userRoleProgramDAO = new UserRoleProgramDAO();
        tutors = new ArrayList<>();
        registeredTutors = new ArrayList<>();
        tutor = new UserRoleProgram();
        tutor1 = new UserRoleProgram();
    }

    @Test
    public void getTutors() throws SQLException {
        tutor1.setName("Mario");
        tutor1.setPaternalSurname("Jimenez");
        tutor1.setMaternalSurname("Jimenez");
        registeredTutors.add(tutor1);

        tutors = userRoleProgramDAO.getTutors();

        boolean isValid = true;
        int iterator = 0;
        for(UserRoleProgram tutorUV : tutors) {
            if(!tutorUV.getFullName().equals(registeredTutors.get(iterator).getFullName())) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }
}