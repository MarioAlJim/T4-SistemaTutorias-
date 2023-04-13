package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorado;
import com.teamfour.sistutorias.domain.UserRoleProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITutoradoDAO {

    public int register(Tutorado tutorado) throws SQLException;

    public int update(Tutorado tutorado) throws SQLException;

    public int delete(Tutorado tutorado) throws SQLException;

    public ArrayList<Tutorado> getTutoradosOfTutor(String tutor_id) throws SQLException;

    public ArrayList<Tutorado> getTutoradosByProgramTutor (int idProgram) throws SQLException;

    public int updateTutor (Tutorado tutorado, String email) throws SQLException;

    public boolean assignTutor(Tutorado tutorado, UserRoleProgram tutor) throws SQLException;

    public ArrayList<Tutorado> getTutoradosWithTutor (int program_id) throws SQLException;
}
