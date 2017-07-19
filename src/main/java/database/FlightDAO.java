package database;

import model.flight.Flight;
import model.flight.FlightStatus;
import model.flight.FlightType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.QueryConstants.*;

public class FlightDAO {

    public static List<Flight> queryDepartureFlights() {
        //SELECT * FROM flights WHERE origin = "KYIV" ORDER BY flight_id
        String sql_query = QUERY_FLIGHT_SELECT_START + QUERY_INNER_JOIN_AIRLINES_TO_FLIGHTS +
                " WHERE " + FLIGHT_DEPARTURE + " = \"" + AIRPORT_HOST + "\"";

        List<Flight> departureFlights1 = new ArrayList<>();

        try (Statement statement = DataSource.getConn().createStatement();
             ResultSet results = statement.executeQuery(sql_query);) {
            while (results.next()) {

                Flight flight = new Flight();
                flight.setType(FlightType.getFlightType(results.getString(FLIGHT_ARRIVAL)));
                flight.setNumber(results.getString(FLIGHT_NUMBER));
                flight.setAirline(results.getString(FLIGHT_AIRLINE));
                flight.setArrivalCity(results.getString(FLIGHT_ARRIVAL));
                flight.setDepartureCity(results.getString(FLIGHT_DEPARTURE));
                flight.setTimeOfDeparture(results.getString(FLIGHT_DEP_TIME));
                flight.setTimeOfArrival(results.getString(FLIGHT_ARR_TIME));
                flight.setDepartureTerminal(results.getString(FLIGHT_DEP_TERMINAL));
                flight.setArrivalTerminal(results.getString(FLIGHT_ARR_TERMINAL));
                flight.setStatus(Flight.getFlightStatus(results.getString(FLIGHT_STATUS),
                        flight.getType(),
                        flight));

                departureFlights1.add(flight);
            }
            return departureFlights1;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static List<Flight> queryArrivalFlights() {
        //SELECT * FROM flights WHERE destination = "KYIV" ORDER BY flight_id
        String sqlQuery = QUERY_FLIGHT_SELECT_START + QUERY_INNER_JOIN_AIRLINES_TO_FLIGHTS +
                " WHERE " + FLIGHT_ARRIVAL + " = \"" + AIRPORT_HOST + "\"";

        List<Flight> departureFlights1 = new ArrayList<>();

        try (Statement statement = DataSource.getConn().createStatement();
             ResultSet results = statement.executeQuery(sqlQuery);) {
            while (results.next()) {

                Flight flight = new Flight();

                flight.setType(FlightType.getFlightType(results.getString(FLIGHT_ARRIVAL)));
                flight.setNumber(results.getString(FLIGHT_NUMBER));
                flight.setAirline(results.getString(FLIGHT_AIRLINE));
                flight.setArrivalCity(results.getString(FLIGHT_ARRIVAL));
                flight.setDepartureCity(results.getString(FLIGHT_DEPARTURE));
                flight.setTimeOfDeparture(results.getString(FLIGHT_DEP_TIME));
                flight.setTimeOfArrival(results.getString(FLIGHT_ARR_TIME));
                flight.setDepartureTerminal(results.getString(FLIGHT_DEP_TERMINAL));
                flight.setArrivalTerminal(results.getString(FLIGHT_ARR_TERMINAL));
                flight.setStatus(Flight.getFlightStatus(results.getString(FLIGHT_STATUS),
                        flight.getType(),
                        flight));

                departureFlights1.add(flight);
            }
            return departureFlights1;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Flight queryFlightByFlightNumber(String flightNumber) {
        Flight flight = new Flight();

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_FLIGHT_BY_FLIGHT_NUMBER);
            sqlQuery.setString(1, flightNumber);
            ResultSet results = sqlQuery.executeQuery();

            while (results.next()) {
                flight.setType(FlightType.getFlightType(results.getString(FLIGHT_ARRIVAL)));
                flight.setNumber(results.getString(FLIGHT_NUMBER));
                flight.setAirline(results.getString(FLIGHT_AIRLINE));
                flight.setArrivalCity(results.getString(FLIGHT_ARRIVAL));
                flight.setDepartureCity(results.getString(FLIGHT_DEPARTURE));
                flight.setTimeOfDeparture(results.getString(FLIGHT_DEP_TIME));
                flight.setTimeOfArrival(results.getString(FLIGHT_ARR_TIME));
                flight.setDepartureTerminal(results.getString(FLIGHT_DEP_TERMINAL));
                flight.setArrivalTerminal(results.getString(FLIGHT_ARR_TERMINAL));
                flight.setStatus(Flight.getFlightStatus(results.getString(FLIGHT_STATUS),
                        flight.getType(),
                        flight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public static List<Flight> queryFlightsByCities(String flightNumber, String departure, String arrival) {
        List<Flight> flightsList = new ArrayList<>();

        String flightNumberParam = "%" + flightNumber + "%";
        String departureParam = "%" + departure + "%";
        String arrivalParam = "%" + arrival + "%";
        System.out.println(PREP_SEARCH_FLIGHT);

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(PREP_SEARCH_FLIGHT);
            sqlQuery.setString(1, flightNumberParam);
            sqlQuery.setString(2, departureParam);
            sqlQuery.setString(3, arrivalParam);

            ResultSet results = sqlQuery.executeQuery();

            while (results.next()) {
                Flight flight = new Flight();

                flight.setType(FlightType.getFlightType(results.getString(FLIGHT_ARRIVAL)));
                flight.setAirline(results.getString(FLIGHT_AIRLINE));
                flight.setNumber(results.getString(FLIGHT_NUMBER));
                flight.setDepartureCity(results.getString(FLIGHT_DEPARTURE));
                flight.setArrivalCity(results.getString(FLIGHT_ARRIVAL));
                flight.setTimeOfDeparture(results.getString(FLIGHT_DEP_TIME));
                flight.setDepartureTerminal(results.getString(FLIGHT_DEP_TERMINAL));
                flight.setTimeOfArrival(results.getString(FLIGHT_ARR_TIME));
                flight.setArrivalTerminal(results.getString(FLIGHT_ARR_TERMINAL));
                flight.setStatus(Flight.getFlightStatus(results.getString(FLIGHT_STATUS),
                        flight.getType(),
                        flight));

                flightsList.add(flight);
            }
            return flightsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean flightIsExist(String flightNumber) {
        boolean isExist = false;
        //SELECT EXISTS (SELECT flights.number FROM flights WHERE flights.number = "PS11");
        String sqlQuery = "SELECT EXISTS (SELECT * FROM " +
                TABLE_FLIGHTS + " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER +
                " = \"" + flightNumber + "\")";

        try (Statement statement = DataSource.getConn().createStatement();
             ResultSet results = statement.executeQuery(sqlQuery)) {
            while(results.next()){
            if (results.getInt(1) == 1) {
                isExist = true;
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public static List<String> getFlightNumbersOnly() {
        String sqlQuery = "SELECT " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER +
                " FROM " + TABLE_FLIGHTS +
                " ORDER BY " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER;

        List<String> flightNumbers = new ArrayList<>();
        try (Statement statement = DataSource.getConn().createStatement();
             ResultSet results = statement.executeQuery(sqlQuery)) {
            while (results.next()) {

                flightNumbers.add(results.getString(FLIGHT_NUMBER));
            }
            return flightNumbers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int getFlightId(String flightNumber) {
        int flightId = 0;
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_FLIGHT_ID_PREP);
            sqlQuery.setString(1, flightNumber);
            ResultSet results = sqlQuery.executeQuery();
            while (results.next()) {
                flightId = results.getInt(FLIGHT_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightId;
    }

    public static void addNewFlightToDB(Flight inputFlight) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_ADD_NEW_FLIGHT_PREP);
            sqlQuery.setString(1, inputFlight.getNumber());
            sqlQuery.setInt(2, AirlineDAO.getAirlineId(inputFlight.getAirline()));
            sqlQuery.setString(3, inputFlight.getDepartureCity().toUpperCase());
            sqlQuery.setString(4, inputFlight.getTimeOfDeparture().toString());
            sqlQuery.setString(5, inputFlight.getDepartureTerminal().toUpperCase());
            sqlQuery.setString(6, inputFlight.getArrivalCity().toUpperCase());
            sqlQuery.setString(7, inputFlight.getTimeOfArrival().toString());
            sqlQuery.setString(8, inputFlight.getArrivalTerminal().toUpperCase());

            sqlQuery.setString(9, inputFlight.getNumber());

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

    public static void changeFlightDataInDB(Flight flight) {
        System.out.println(QUERY_CHANGE_FLIGHT_DATA_PREP);

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_CHANGE_FLIGHT_DATA_PREP);
            sqlQuery.setString(1, flight.getDepartureCity());
            sqlQuery.setString(2, String.valueOf(flight.getTimeOfDeparture()));
            sqlQuery.setString(3, flight.getDepartureTerminal());
            sqlQuery.setString(4, flight.getArrivalCity());
            sqlQuery.setString(5, String.valueOf(flight.getTimeOfArrival()));
            sqlQuery.setString(6, flight.getArrivalTerminal());

            if (flight.getStatus().equals(FlightStatus.SCHEDULED)) {
                sqlQuery.setString(7, null);
            } else {
                sqlQuery.setString(7, String.valueOf(flight.getStatus()));
            }

            sqlQuery.setString(8, flight.getNumber());

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

    public static void deleteFlightFromDB(String flightNumber) {

        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_DELETE_FLIGHT_PREP);
            sqlQuery.setInt(1, getFlightId(flightNumber));
            sqlQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
