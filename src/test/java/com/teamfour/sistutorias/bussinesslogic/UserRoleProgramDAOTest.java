package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import com.teamfour.sistutorias.presentation.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    @Test
    public void getUser() throws SQLException{
        UserRoleProgram userRoleProgram;
        userRoleProgram = userRoleProgramDAO.searchUser("mario14@uv.mx", "majiji0104");
        assertTrue(userRoleProgram.getEmail().equals("mario14@uv.mx"));
    }

    @Test
    public void searchUser() {
        try {
            UserRoleProgram user = userRoleProgramDAO.searchUser("aarenas@uv.mx", "Pl4wRkZLrTcicmr");
            assertTrue(user.getEmail().equals("aarenas@uv.mx"));
        } catch (SQLException sqlException) {
            Logger.getLogger(UserRoleProgramDAOTest.class.getName()).log(Level.SEVERE, null, sqlException);
        }

    }
}