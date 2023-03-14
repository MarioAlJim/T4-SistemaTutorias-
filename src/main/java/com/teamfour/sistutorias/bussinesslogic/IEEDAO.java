package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EE;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEEDAO {
    public ArrayList<EE> getEEs() throws SQLException;
}
