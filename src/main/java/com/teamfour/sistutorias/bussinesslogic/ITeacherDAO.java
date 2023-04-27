package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITeacherDAO {
    public ArrayList<Teacher> getTeachersByProgram(int idProgram) throws SQLException;

    public ArrayList<Teacher> getAllTeachers() throws SQLException;

    public int registerTeacher(Teacher teacher) throws SQLException;

    public int modifyTeacher(Teacher newTeacher, int oldPersonalNumber) throws SQLException;

    public int deleteTeacher(int personalNumber, int preson_id) throws SQLException;
}
