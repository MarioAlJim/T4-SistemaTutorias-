package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Group;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGroupDAO {

    public ArrayList<Group> groupsList (int idProgram) throws SQLException;
}
