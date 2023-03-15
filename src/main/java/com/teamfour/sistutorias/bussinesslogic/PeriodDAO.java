package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Period;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PeriodDAO implements IPeriodDAO {
    @Override
    public ArrayList<Period> getPeriods() throws SQLException {
        ArrayList<Period> periods = new ArrayList<>();
        String query = "SELECT P.start, P.end FROM period P";
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Period period = new Period();
        Date start = resultSet.getDate("start");
        String startWithFormat = dateFormat.format(start);
        period.setStart(startWithFormat);
        Date end = resultSet.getDate("end");
        String endWithFormat = dateFormat.format(end);
        period.setEnd(endWithFormat);
        return period;
    }
}
