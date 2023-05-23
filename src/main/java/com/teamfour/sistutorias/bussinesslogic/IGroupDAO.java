package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Group;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGroupDAO {
    ArrayList<Group> getGroupsList(int idProgram, int idPeriod) throws SQLException;

    public int registerGroup(Group newGroup) throws SQLException;

    int deleteGroup(int nrc) throws SQLException;

    int modifyGroup(Group newGroup, int newNrc) throws SQLException;
    public ArrayList<Group> getGroupsByEducationProgram(int idProgram) throws SQLException;
}
