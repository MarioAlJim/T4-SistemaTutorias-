package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Assistance;

import java.sql.SQLException;

public interface IAssistanceDAO {

    public int register(Assistance assistance) throws SQLException;

    public int update(Assistance assistance) throws SQLException;

    public int delete(Assistance assistance) throws SQLException;

}
