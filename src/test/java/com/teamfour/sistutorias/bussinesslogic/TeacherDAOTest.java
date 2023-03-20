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
    Teacher teacher2;
    @BeforeEach
    void setUp() {
        teacherDAO = new TeacherDAO();
        teachers = new ArrayList<>();
        registeredTeachers = new ArrayList<>();
        teacher = new Teacher();
        teacher1 = new Teacher();
        teacher2 = new Teacher();
    }

    @Test
    public void getTeachers() throws SQLException {
        teacher1.setPersonalNumber(789456);
        registeredTeachers.add(teacher1);
        teacher2.setPersonalNumber(123456);
        registeredTeachers.add(teacher2);

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