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

        teachers = teacherDAO.getTeachersByProgram(1);
        boolean isValid = true;
        int iterator = 0;
        for (Teacher teacherUV : teachers) {
            if (teacherUV.getPersonalNumber() != registeredTeachers.get(iterator).getPersonalNumber()) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }

    @Test
    public void deleteTeacherTest() throws SQLException {
        int result ;
        result = teacherDAO.deleteTeacher(28872);
        assertTrue(result == 1);
    }

    @Test
    public void getAllTeachersTest () throws SQLException {
        int result = 10;
        ArrayList<Teacher> teachers1 = teacherDAO.getAllTeachers();
        assertTrue(teachers1.size() == result);
    }

    @Test
    public void modifyTeacherDuplicatedTest () throws  SQLException{
        Teacher teacher3 = new Teacher();
        teacher3.setPersonalNumber(77696);
        teacher3.setIdPerson(41);
        teacher3.setMaternalSurname("Lopez");
        teacher3.setPaternalSurname("Santos");
        teacher3.setName("Ceci");

        int result = teacherDAO.modifyTeacher(teacher3, 33568);

        assertTrue(result == 0);
    }

    @Test
    public void modifyTeacherCorrectTest () throws  SQLException{
        Teacher teacher3 = new Teacher();
        teacher3.setPersonalNumber(33568);
        teacher3.setIdPerson(41);
        teacher3.setMaternalSurname("Lopez");
        teacher3.setPaternalSurname("Santos");
        teacher3.setName("Ceci");

        int result = teacherDAO.modifyTeacher(teacher3, 33568);

        assertTrue(result == 2);
    }

    @Test
    public void modifyTeacherCorrect2Test () throws  SQLException{
        Teacher teacher3 = new Teacher();
        teacher3.setPersonalNumber(52647);
        teacher3.setIdPerson(41);
        teacher3.setMaternalSurname("Castillo");
        teacher3.setPaternalSurname("Tielmans");
        teacher3.setName("Cecilia");

        int result = teacherDAO.modifyTeacher(teacher3, 33568);

        assertTrue(result == 2);
    }

    @Test
    public void registerTeacherDuplicated () throws SQLException {
        Teacher teacher3 = new Teacher();
        teacher3.setPersonalNumber(74128);
        teacher3.setMaternalSurname("Christian");
        teacher3.setPaternalSurname("Sanchez");
        teacher3.setName("Lopez");

        int result = teacherDAO.registerTeacher(teacher3);

        assertTrue(result == 1);
    }
}