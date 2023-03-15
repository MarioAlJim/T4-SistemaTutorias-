package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EEDAO implements IEEDAO {
    @Override
    public ArrayList<EE> getEEs() throws SQLException {
        ArrayList<EE> ees = new ArrayList<>();
        String query = "SELECT name FROM EE";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ees.add(getEE(resultSet));
        }
        dataBaseConnection.closeConection();
        return ees;
    }

    private EE getEE(ResultSet resultSet) throws SQLException {
        EE ee = new EE();
        String name = resultSet.getString("name");
        ee.setName(name);
        return ee;
    }
}
