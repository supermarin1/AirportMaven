package database;


import model.flight.Cabin;
import model.price.Price;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static database.QueryConstants.*;

public class PriceDAO {
    public static List<Price> queryPriceListByFlightNumber(String flightNumber) {
        List<Price> priceList = new ArrayList<>();

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_PRICE_LIST_BY_FLIGHT_NUMBER);
            sqlQuery.setString(1, flightNumber);
            ResultSet results = sqlQuery.executeQuery();
            while (results.next()) {
                Price price = new Price();

                price.setCabin(results.getString(PRICE_CABIN));
                price.setFare((results.getInt(PRICE_FARE)));

                priceList.add(price);
            }
            return priceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int getPriceId(String flightNumber, Cabin cabin) {
        int priceId = 0;
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_PRICE_ID_PREP);
            sqlQuery.setInt(1, FlightDAO.getFlightId(flightNumber));
            sqlQuery.setString(2, cabin.toString());
            ResultSet results = sqlQuery.executeQuery();
            if (results.next()) {
                priceId = results.getInt(PRICE_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return priceId;
    }

    public static Integer getPrice(String flightNumber, Cabin cabin) {
        Integer price = 0;
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_PRICE_BY_FLIGHT_NUMBER_AND_CABIN);
            sqlQuery.setInt(1, FlightDAO.getFlightId(flightNumber));
            sqlQuery.setString(2, cabin.toString());
            ResultSet results = sqlQuery.executeQuery();
            if (results.next()) {
                price = results.getInt(PRICE_FARE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static void addNewPriceListToDB(Price inputPrice) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_ADD_PRICE_LIST_PREP);
            int flightID = FlightDAO.getFlightId(inputPrice.getFlightNumber());
            sqlQuery.setInt(1, flightID);
            sqlQuery.setString(2, inputPrice.getCabin().toString());
            sqlQuery.setInt(3, inputPrice.getFare());

            sqlQuery.setInt(4, flightID);
            sqlQuery.setString(5, inputPrice.getCabin().toString());

            sqlQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSource.getConn().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void changePriceInDB(Price inputPrice) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_CHANGE_PRICE_PREP);

            int flightID = FlightDAO.getFlightId(inputPrice.getFlightNumber());
            sqlQuery.setInt(1,inputPrice.getFare());
            sqlQuery.setInt(2,flightID);
            sqlQuery.setString(3,inputPrice.getCabin().toString());
            sqlQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSource.getConn().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void deletePriceFromDB(String flightNumber) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_DELETE_PRICE_PREP);
            int flightID = FlightDAO.getFlightId(flightNumber);
            sqlQuery.setInt(1, flightID);
            sqlQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DataSource.getConn().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}