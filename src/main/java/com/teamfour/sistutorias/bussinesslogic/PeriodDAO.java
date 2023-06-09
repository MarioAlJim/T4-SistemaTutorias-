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
        String query = "SELECT * FROM period";
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

    @Override
    public Period getCurrentPeriod() throws SQLException {
        Period currentPeriod = new Period();
        String query = "SELECT * " +
                "FROM sistematutorias.period P " +
                "WHERE CURDATE() " +
                "BETWEEN P.start AND P.end";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            currentPeriod = getPeriod(resultSet);
        }
        dataBaseConnection.closeConection();
        return currentPeriod;
    }

    private Period getPeriod(ResultSet resultSet) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Period period = new Period();
        int idPeriod = resultSet.getInt("period_id");
        period.setIdPeriod(idPeriod);
        Date start = resultSet.getDate("start");
        String startWithFormat = dateFormat.format(start);
        period.setStart(startWithFormat);
        Date end = resultSet.getDate("end");
        String endWithFormat = dateFormat.format(end);
        period.setEnd(endWithFormat);
        return period;
    }
}
