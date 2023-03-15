package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDAOTest {
    TeacherDAO teacherDAO;
    ArrayList<Teacher> teachers;
    ArrayList<Teacher> registeredTeachers;
    Teacher teacher;
    Teacher teacher1;
    @BeforeEach
    void setUp() {
        teacherDAO = new TeacherDAO();
        teachers = new ArrayList<>();
        registeredTeachers = new ArrayList<>();
        teacher = new Teacher();
        teacher1 = new Teacher();
    }

    @Test
    public void getTeachers() throws SQLException {
        teacher1.setPersonalNumber(123);
        teacher1.setName("Max William");
        teacher1.setPaternalSurname("Millan");
        teacher1.setMaternalSurname("Martinez");
        registeredTeachers.add(teacher1);

        teachers = teacherDAO.getTeachers();
        boolean isValid = true;
        int iterator = 0;
        for(Teacher teacherUV : teachers) {
            if(teacherUV.getPersonalNumber() != registeredTeachers.get(iterator).getPersonalNumber()) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }
}