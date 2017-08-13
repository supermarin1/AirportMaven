package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.QueryConstants.*;

public class AirlineDAO {

    public static void addNewAirline(String airline) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_ADD_NEW_AIRLINE_PREP);
            sqlQuery.setString(1, airline);
            sqlQuery.setString(2, airline);
            sqlQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSource.getConn().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static List<String> getAirlines() {
        String sqlQuery = "SELECT " + TABLE_AIRLINES + "." + AIRLINE_NAME +
                " FROM " + TABLE_AIRLINES +
                " ORDER BY " + TABLE_AIRLINES + "." + AIRLINE_NAME;

        List<String> airlines = new ArrayList<>();
        try (Statement statement = DataSource.getConn().createStatement();
             ResultSet results = statement.executeQuery(sqlQuery)) {
            while (results.next()) {
                airlines.add(results.getString(AIRLINE_NAME));
            }
            return airlines;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int getAirlineId(String airline) {
        int airlineId = 0;
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_AIRLINE_ID_PREP);
            sqlQuery.setString(1, airline);
            ResultSet results = sqlQuery.executeQuery();
            while (results.next()) {
                airlineId = results.getInt(AIRLINE_ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlineId;
    }
}
