package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Assistance;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAssistanceDAO {

    public int register(Assistance assistance) throws SQLException;

    public int update(Assistance assistance) throws SQLException;

    public int delete(Assistance assistance) throws SQLException;

    public ArrayList<Assistance> getAssistanceTutor(String tutor_id) throws SQLException;
}
