package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorado;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TutoradoDAOTest {
    TutoradoDAO tutoradoDAO;
    Tutorado tutorado1;
    UserRoleProgram tutor1;

    @BeforeEach
    void setUp() {
        tutoradoDAO = new TutoradoDAO();
        tutorado1 = new Tutorado();
        tutor1 = new UserRoleProgram();
    }

    @Test
    void assignTutor() throws SQLException {
        tutor1.setEmail("maxmillan");
        tutorado1.setRegistrationNumber("S20015728");
        assertTrue(tutoradoDAO.assignTutor(tutorado1, tutor1));
    }
}