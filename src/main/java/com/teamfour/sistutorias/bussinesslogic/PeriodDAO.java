package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Period;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeriodDAO implements IPeriodDAO {
    @Override
    public ArrayList<Period> getPeriods() throws SQLException {
        ArrayList<Period> periods = new ArrayList<>();
        String query = "SELECT * FROM period P";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            periods.add(getPeriod(resultSet));
        }
        dataBaseConnection.closeConection();
        return periods;
    }

    private Period getPeriod(ResultSet resultSet) throws SQLException {
        Period period = new Period();
        int idPeriod = resultSet.getInt("period_id");
        period.setIdPeriod(idPeriod);
        String start = resultSet.getString("start");
        period.setStart(start);
        String end = resultSet.getString("end");
        period.setEnd(end);
        return period;
    }
}
