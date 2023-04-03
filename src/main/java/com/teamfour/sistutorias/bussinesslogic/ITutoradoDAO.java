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

<<<<<<< HEAD
    public ArrayList<Tutorado> getTutoradosWithTutor(int program_id) throws SQLException;
=======
    public ArrayList<Tutorado> getTutoradosWithTutor (int program_id) throws SQLException;
>>>>>>> 5b27d1f5f2da5a91595b9505067e2c628c5c021c
}
