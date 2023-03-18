package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAcademicProblemDAO {

    public int register(AcademicProblem academicProblem) throws SQLException;

    public int update(AcademicProblem academicProblem)throws SQLException;

    public int delete(AcademicProblem academicProblem) throws SQLException;

    public ArrayList<AcademicProblem> getAcademicProblemsFromRegister(int registerId) throws SQLException;
}
