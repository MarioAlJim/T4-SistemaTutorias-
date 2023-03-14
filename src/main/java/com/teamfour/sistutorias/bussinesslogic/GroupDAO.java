package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Group;

import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO implements IGroupDAO {
    @Override
    public ArrayList<Group> groupsList(int idProgram) throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();

        return groups;
    }
}
