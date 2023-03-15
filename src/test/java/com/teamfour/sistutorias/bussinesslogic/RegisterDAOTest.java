package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Register;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterDAOTest {

    RegisterDAO registerDAO = new RegisterDAO();

    @Test
    void getRegister() {
        Register registerCheck = new Register();
        registerCheck.setEmail("email");
        try {
            registerDAO.register(registerCheck);
            assertEquals(registerCheck.getEmail(), registerDAO.getRegister(registerCheck).getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getLatestRegister() {
        Register registerCheck = new Register();
        registerCheck.setEmail("a-mail");
        try {
            registerDAO.register(registerCheck);
            assertEquals(registerCheck.getEmail(), registerDAO.getLatestRegister().getEmail());
            registerDAO.delete(registerDAO.getLatestRegister());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        Register registerCheck = new Register();
        registerCheck.setEmail("a-mail");
        try {
            registerDAO.register(registerCheck);
            registerCheck.setEmail("a-mail2");
            assertEquals(registerDAO.update(registerCheck), 1);
            registerDAO.delete(registerDAO.getLatestRegister());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        Register registerCheck = new Register();
        registerCheck.setEmail("a-mail");
        try {
            registerDAO.register(registerCheck);
            assertEquals(registerDAO.delete(registerDAO.getLatestRegister()), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}