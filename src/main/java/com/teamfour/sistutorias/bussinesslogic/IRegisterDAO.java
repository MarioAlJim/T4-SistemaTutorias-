package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Register;

import java.sql.SQLException;
import java.util.List;

public interface IRegisterDAO {

    public Register getRegister(Register register) throws SQLException;
    public Register getLatestRegister() throws SQLException;
    public int register(Register register) throws SQLException;
    public int update(Register register) throws SQLException;
    public int delete(Register register) throws SQLException;
    public List<Register> getTutorshipRegister(int tutorshipId) throws SQLException;
}
