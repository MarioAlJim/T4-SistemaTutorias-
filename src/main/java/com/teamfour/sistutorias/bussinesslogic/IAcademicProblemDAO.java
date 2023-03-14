package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.AcademicProblem;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAcademicProblemDAO {

    public int registrarProblematicaAcademica(AcademicProblem academicProblem, int register) throws SQLException;

    public int actualizarProblematica(AcademicProblem academicProblem)throws SQLException;

    public int eliminarProblematica(AcademicProblem academicProblem) throws SQLException;
}
