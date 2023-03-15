package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITeacherDAO {
    public ArrayList<Teacher> getTeachers() throws SQLException;
}
