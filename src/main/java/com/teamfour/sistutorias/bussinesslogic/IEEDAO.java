package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EE;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEEDAO {
    public ArrayList<EE> getEEsByProgram(int idProgram) throws SQLException;
    public boolean register(EE newEE) throws SQLException;
    public boolean update(EE ee) throws SQLException;
    public boolean delete(EE ee) throws SQLException;
    public ArrayList<EE> getEEs() throws SQLException;
    ArrayList<EE> getAllEe() throws SQLException;
}
