package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Tutorado;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITutoradoDAO {

    public int register(Tutorado tutorado) throws SQLException;

    public int update(Tutorado tutorado) throws SQLException;

    public int delete(Tutorado tutorado) throws SQLException;

    public ArrayList<Tutorado> getTutoradosOfTutor(String tutor_id) throws SQLException;

}
