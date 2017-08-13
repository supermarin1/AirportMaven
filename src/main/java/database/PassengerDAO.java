package database;

import model.passenger.Passenger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.QueryConstants.*;

public class PassengerDAO {

    public static List<Passenger> queryPassengersByFlightNumber(String flightNumber) {

        List<Passenger> passengerList = new ArrayList<>();

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_PASSENGERS_BY_FLIGHT_NUMBER);
            sqlQuery.setString(1, flightNumber);
            ResultSet results = sqlQuery.executeQuery();

            while (results.next()) {
                Passenger passenger = new Passenger();

                passenger.setFirstName(results.getString(PAX_FIRST_NAME));
                passenger.setSecondName(results.getString(PAX_SECOND_NAME));
                passenger.setGender(results.getString(PAX_GENDER));
                passenger.setNationality(results.getString(PAX_NATIONALITY));
                passenger.setPassport(results.getString(PAX_PASSPORT));
                passenger.setDateOfBirth(results.getString(PAX_BIRTHDAY));
                passenger.setCabin(results.getString(PRICE_CABIN));

                passengerList.add(passenger);
            }
            return passengerList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Passenger> querySearchPassengerByName(String firstName, String secondName) {
        List<Passenger> passengerList = new ArrayList<>();

        String firstNameParam = "%" + firstName + "%";
        String secondNameParam = "%" + secondName + "%";

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_SEARCH_PASSENGERS_BY_NAME);
            sqlQuery.setString(1, firstNameParam);
            sqlQuery.setString(2, secondNameParam);
            ResultSet results = sqlQuery.executeQuery();

            while (results.next()) {
                Passenger passenger = new Passenger();

                passenger.setFlightNumber(results.getString(FLIGHT_NUMBER));
                passenger.setRoute(results.getString(FLIGHT_DEPARTURE), results.getString(FLIGHT_ARRIVAL));
                passenger.setFirstName(results.getString(PAX_FIRST_NAME));
                passenger.setSecondName(results.getString(PAX_SECOND_NAME));
                passenger.setGender(results.getString(PAX_GENDER));
                passenger.setNationality(results.getString(PAX_NATIONALITY));
                passenger.setPassport(results.getString(PAX_PASSPORT));
                passenger.setDateOfBirth(results.getString(PAX_BIRTHDAY));
                passenger.setCabin(results.getString(PRICE_CABIN));
                passenger.setFare(results.getInt(PRICE_FARE));

                passengerList.add(passenger);
            }
            return passengerList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Passenger> querySearchPassengerByPassport(String passport) {
        List<Passenger> passengerList = new ArrayList<>();

        String passportParam = "%" + passport + "%";

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_SEARCH_PASSENGERS_BY_PASSPORT);
            sqlQuery.setString(1, passportParam);
            ResultSet results = sqlQuery.executeQuery();

            while (results.next()) {
                Passenger passenger = new Passenger();

                passenger.setFlightNumber(results.getString(FLIGHT_NUMBER));
                passenger.setRoute(results.getString(FLIGHT_DEPARTURE), results.getString(FLIGHT_ARRIVAL));
                passenger.setFirstName(results.getString(PAX_FIRST_NAME));
                passenger.setSecondName(results.getString(PAX_SECOND_NAME));
                passenger.setGender(results.getString(PAX_GENDER));
                passenger.setNationality(results.getString(PAX_NATIONALITY));
                passenger.setPassport(results.getString(PAX_PASSPORT));
                passenger.setDateOfBirth(results.getString(PAX_BIRTHDAY));
                passenger.setCabin(results.getString(PRICE_CABIN));
                passenger.setFare(results.getInt(PRICE_FARE));

                passengerList.add(passenger);
            }
            return passengerList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addNewPassengerToDB(Passenger passenger) {
        LocalDate bDayDate = passenger.getDateOfBirth();
        String birthday = bDayDate.getDayOfMonth() + "/" + bDayDate.getMonthValue() + "/" + bDayDate.getYear();
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_ADD_NEW_PASSENGER_START_PREP);

            sqlQuery.setInt(1, FlightDAO.getFlightId(passenger.getFlightNumber()));
            sqlQuery.setString(2, passenger.getFirstName());
            sqlQuery.setString(3, passenger.getSecondName());
            sqlQuery.setString(4, passenger.getGender().toString());
            sqlQuery.setString(5, passenger.getNationality());
            sqlQuery.setString(6, passenger.getPassport());
            sqlQuery.setString(7, birthday);
            sqlQuery.setInt(8, PriceDAO.getPriceId(passenger.getFlightNumber(), passenger.getCabin()));

            sqlQuery.setInt(9, FlightDAO.getFlightId(passenger.getFlightNumber()));
            sqlQuery.setString(10, passenger.getPassport());

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

    public static void changePassengerDataInDB(String passport, Passenger passenger) {
        LocalDate bDayDate = passenger.getDateOfBirth();
        String birthday = bDayDate.getDayOfMonth() + "/" + bDayDate.getMonthValue() + "/" + bDayDate.getYear();

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_CHANGE_PASSENGER_DATA_PREP);
            sqlQuery.setString(1, passenger.getFirstName());
            sqlQuery.setString(2, passenger.getSecondName());
            sqlQuery.setString(3, passenger.getGender().toString());
            sqlQuery.setString(4, passenger.getNationality());
            sqlQuery.setString(5, passenger.getPassport());
            sqlQuery.setString(6, birthday);
            sqlQuery.setInt(7, PriceDAO.getPriceId(passenger.getFlightNumber(), passenger.getCabin()));

            sqlQuery.setString(8, passport);

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

    public static void deletePassengerFromDB(String passport) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_DELETE_PASSENGER_PREP);
            sqlQuery.setString(1, passport);
            sqlQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


























