package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Register;

import java.sql.SQLException;

public interface IRegisterDAO {

    public Register getRegister(Register register) throws SQLException;
    public Register getLatestRegister() throws SQLException;
    public int register(Register register) throws SQLException;
    public int update(Register register) throws SQLException;
    public int delete(Register register) throws SQLException;
    public Register getTutorshipRegister(int tutorshipId) throws SQLException;
}
