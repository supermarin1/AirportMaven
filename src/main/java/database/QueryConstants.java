package database;


class QueryConstants {
    static final String TABLE_AIRLINES = "airlines";
    static final String AIRLINE_ID = "_id";
    static final String AIRLINE_NAME = "name";

    static final String TABLE_FLIGHTS = "flights";
    static final String FLIGHT_ID = "_id";
    static final String AIRPORT_HOST = "KYIV";
    static final String FLIGHT_NUMBER = "number";
    static final String FLIGHT_AIRLINE_ID = "airline_id";
    static final String FLIGHT_AIRLINE = "airline";
    static final String FLIGHT_DEPARTURE = "origin";
    static final String FLIGHT_DEP_TIME = "departure_time";
    static final String FLIGHT_DEP_TERMINAL = "terminal_origin";
    static final String FLIGHT_ARRIVAL = "destination";
    static final String FLIGHT_ARR_TIME = "arrival_time";
    static final String FLIGHT_ARR_TERMINAL = "terminal_destination";
    static final String FLIGHT_DURATION = "duration";
    static final String FLIGHT_STATUS = "status";

    static final String TABLE_PAXS = "passengers";
    static final String PAX_FLIGHT_ID = "flight_id";
    static final String PAX_FIRST_NAME = "first_name";
    static final String PAX_SECOND_NAME = "second_name";
    static final String PAX_GENDER = "gender";
    static final String PAX_NATIONALITY = "nationality";
    static final String PAX_PASSPORT = "passport";
    static final String PAX_BIRTHDAY = "date_of_birth";
    static final String PAX_PRICE_ID = "price_id";

    static final String TABLE_PRICES = "prices";
    static final String PRICE_FLIGHT_ID = "flight_id";
    static final String PRICE_CABIN = "cabin";
    static final String PRICE_FARE = "fare";
    static final String PRICE_ID = "_id";

    static final String TABLE_USERS = "users";
    static final String USER_USERNAME = "username";
    static final String USER_PASSWORD = "password";
    static final String USER_PERMISSION = "permission";

    static final String QUERY_AIRLINE_ID_PREP = " SELECT " + AIRLINE_ID + " FROM " +
            TABLE_AIRLINES + " WHERE " + AIRLINE_NAME + " = ?";
    static final String QUERY_FLIGHT_ID_PREP = " SELECT " + FLIGHT_ID + " FROM " +
            TABLE_FLIGHTS + " WHERE " + FLIGHT_NUMBER + " = ?";
    static final String QUERY_PRICE_ID_PREP = " SELECT " + PRICE_ID + " FROM " +
            TABLE_PRICES + " WHERE " + PRICE_FLIGHT_ID + " = ? AND " +
            PRICE_CABIN + " = ?";

    static final String QUERY_INNER_JOIN_AIRLINES_TO_FLIGHTS =
            " INNER JOIN " + TABLE_AIRLINES + " ON " + TABLE_FLIGHTS + "." + FLIGHT_AIRLINE_ID +
                    " = " + TABLE_AIRLINES + "." + AIRLINE_ID;
    static final String QUERY_INNER_JOIN_FLIGHTS_TO_PASSENGERS =
            " INNER JOIN " + TABLE_FLIGHTS + " ON " +
                    TABLE_PAXS + "." + PAX_FLIGHT_ID + " = " + TABLE_FLIGHTS + "." + FLIGHT_ID;
    static final String QUERY_INNER_JOIN_FLIGHTS_TO_PRICES =
            " INNER JOIN " + TABLE_FLIGHTS + " ON " +
                    TABLE_PRICES + "." + PRICE_FLIGHT_ID + " = " + TABLE_FLIGHTS + "." + FLIGHT_ID;
    static final String QUERY_INNER_JOIN_PRICES_TO_PASSENGERS =
            " INNER JOIN " + TABLE_PRICES + " ON " +
                    TABLE_PAXS + "." + PAX_PRICE_ID + " = " + TABLE_PRICES + "." + PRICE_ID;

    static final String QUERY_SEARCH_FLIGHT_START = "SELECT " +
            TABLE_FLIGHTS + "." + FLIGHT_NUMBER + ", " +
            TABLE_AIRLINES + "." + AIRLINE_NAME + " AS " + FLIGHT_AIRLINE + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_DEPARTURE + ", " + TABLE_FLIGHTS + "." + FLIGHT_DEP_TERMINAL + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_DEP_TIME + ", " + TABLE_FLIGHTS + "." + FLIGHT_ARR_TERMINAL + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_ARRIVAL + ", " + TABLE_FLIGHTS + "." + FLIGHT_ARR_TIME + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_ARR_TERMINAL + ", " + TABLE_FLIGHTS + "." + FLIGHT_DURATION + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_STATUS +
            " FROM " + TABLE_FLIGHTS;

    static final String PREP_SEARCH_FLIGHT_BY_FLIGHT_NUMBER = QUERY_SEARCH_FLIGHT_START +
                    QUERY_INNER_JOIN_AIRLINES_TO_FLIGHTS +
                    " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER + "=?";

    static final String PREP_SEARCH_FLIGHT_BY_CITIES = QUERY_SEARCH_FLIGHT_START +
            QUERY_INNER_JOIN_AIRLINES_TO_FLIGHTS +
            " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER + " LIKE ? AND "
            + TABLE_FLIGHTS + "." + FLIGHT_DEPARTURE + " LIKE ? AND " +
            TABLE_FLIGHTS + "." + FLIGHT_ARRIVAL + " LIKE ?";


    static final String QUERY_SEARCH_PASSENGERS_BY_FLIGHT_NUMBER_START = "SELECT " +
            TABLE_FLIGHTS + "." + FLIGHT_NUMBER + ", " +
            TABLE_PAXS + "." + PAX_FIRST_NAME + ", " +
            TABLE_PAXS + "." + PAX_SECOND_NAME + ", " +
            TABLE_PAXS + "." + PAX_GENDER + ", " +
            TABLE_PAXS + "." + PAX_NATIONALITY + ", " +
            TABLE_PAXS + "." + PAX_PASSPORT + ", " +
            TABLE_PAXS + "." + PAX_BIRTHDAY + ", " +
            TABLE_PRICES + "." + PRICE_CABIN +
            " FROM " + TABLE_PAXS;

    static final String PREP_PASSENGERS_BY_FLIGHT_NUMBER = QUERY_SEARCH_PASSENGERS_BY_FLIGHT_NUMBER_START +
            QUERY_INNER_JOIN_FLIGHTS_TO_PASSENGERS +
            QUERY_INNER_JOIN_PRICES_TO_PASSENGERS +
            " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER + " = ?";

    static final String QUERY_SEARCH_PASSENGERS_BY_NAME_START = " SELECT " +
            TABLE_FLIGHTS + "." + FLIGHT_NUMBER + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_DEPARTURE + ", " +
            TABLE_FLIGHTS + "." + FLIGHT_ARRIVAL + ", " +
            TABLE_PAXS + "." + PAX_FIRST_NAME + ", " +
            TABLE_PAXS + "." + PAX_SECOND_NAME + ", " +
            TABLE_PAXS + "." + PAX_GENDER + ", " +
            TABLE_PAXS + "." + PAX_NATIONALITY + ", " +
            TABLE_PAXS + "." + PAX_PASSPORT + ", " +
            TABLE_PAXS + "." + PAX_BIRTHDAY + ", " +
            TABLE_PRICES + "." + PRICE_CABIN + ", " +
            TABLE_PRICES + "." + PRICE_FARE +
            " FROM " + TABLE_PAXS;

    static final String PREP_SEARCH_PASSENGERS_BY_NAME = QUERY_SEARCH_PASSENGERS_BY_NAME_START +
            QUERY_INNER_JOIN_FLIGHTS_TO_PASSENGERS +
            QUERY_INNER_JOIN_PRICES_TO_PASSENGERS +
            " WHERE " + TABLE_PAXS + "." + PAX_FIRST_NAME + " LIKE ? AND " +
            TABLE_PAXS + "." + PAX_SECOND_NAME + " LIKE ?";

    static final String PREP_SEARCH_PASSENGERS_BY_PASSPORT = QUERY_SEARCH_PASSENGERS_BY_NAME_START +
            QUERY_INNER_JOIN_FLIGHTS_TO_PASSENGERS +
            QUERY_INNER_JOIN_PRICES_TO_PASSENGERS +
            " WHERE " + TABLE_PAXS + "." + PAX_PASSPORT + " LIKE ?";

    static final String QUERY_SEARCH_PRICE_LIST_BY_FLIGHT_NUMBER_START = "SELECT DISTINCT " +
            TABLE_PRICES + "." + PRICE_CABIN + ", " +
            TABLE_PRICES + "." + PRICE_FARE +
            " FROM " + TABLE_PRICES;

    static final String PREP_SEARCH_PRICE_LIST_BY_FLIGHT_NUMBER = QUERY_SEARCH_PRICE_LIST_BY_FLIGHT_NUMBER_START +
            QUERY_INNER_JOIN_FLIGHTS_TO_PRICES +
            " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER + " = ?";

    static final String PREP_SEARCH_PRICE_BY_FLIGHT_NUMBER_AND_CABIN = "SELECT " +
            TABLE_PRICES + "." + PRICE_FARE +
            " FROM " + TABLE_PRICES +
            " WHERE " + TABLE_PRICES + "." + PRICE_FLIGHT_ID + " = ? AND " +
            TABLE_PRICES + "." + PRICE_CABIN + " = ?";

    static final String QUERY_ADD_NEW_AIRLINE_PREP = "INSERT INTO " + TABLE_AIRLINES +
            "(" + AIRLINE_NAME + ")" +
            " SELECT * FROM (SELECT ? ) AS tmp" +
            " WHERE NOT EXISTS (SELECT * FROM " + TABLE_AIRLINES +
            " WHERE " + TABLE_AIRLINES + "." + AIRLINE_NAME + " = ?)";

    static final String QUERY_ADD_NEW_FLIGHT_PREP =
            "INSERT INTO " + TABLE_FLIGHTS + "(" + FLIGHT_NUMBER + ", " + FLIGHT_AIRLINE_ID + ", " +
                    FLIGHT_DEPARTURE + ", " + FLIGHT_DEP_TIME + ", " + FLIGHT_DEP_TERMINAL + ", " +
                    FLIGHT_ARRIVAL + ", " + FLIGHT_ARR_TIME + ", " + FLIGHT_ARR_TERMINAL + ")" +
                    " SELECT * FROM (SELECT ?, ?, ?, ?, ?, ?, ?, ?) AS tmp" +
                    " WHERE NOT EXISTS ( SELECT * FROM " + TABLE_FLIGHTS +
                    " WHERE " + TABLE_FLIGHTS + "." + FLIGHT_NUMBER + " = ?)";

    static final String QUERY_ADD_PRICE_LIST_PREP =
            "INSERT INTO " + TABLE_PRICES + "(" +
                    PRICE_FLIGHT_ID + ", " + PRICE_CABIN + ", " + PRICE_FARE + ")" +
                    " SELECT * FROM (SELECT ?, ?, ?) AS tmp" +
                    " WHERE NOT EXISTS (SELECT * FROM " + TABLE_PRICES +
                    " WHERE " + TABLE_PRICES + "." + PRICE_FLIGHT_ID + " = ? " +
                    " AND " + TABLE_PRICES + "." + PRICE_CABIN + " = ?)";

    static final String QUERY_ADD_NEW_PASSENGER_START_PREP =
            "INSERT INTO " + TABLE_PAXS + "(" + PAX_FLIGHT_ID + ", " + PAX_FIRST_NAME + ", " +
                    PAX_SECOND_NAME + ", " + PAX_GENDER + ", " + PAX_NATIONALITY + ", " +
                    PAX_PASSPORT + ", " + PAX_BIRTHDAY + ", " + PAX_PRICE_ID + ")" +
                    " SELECT * FROM ( SELECT ?, ?, ?, ?, ?, ?, ?, ?) AS tmp" +
                    " WHERE NOT EXISTS ( SELECT * FROM " + TABLE_PAXS +
                    " WHERE " + TABLE_PAXS + "." + PAX_FLIGHT_ID + " = ?" +
                    " AND " + TABLE_PAXS + "." + PAX_PASSPORT + " = ?)";

    static final String QUERY_CHANGE_PASSENGER_DATA_PREP = "UPDATE " + TABLE_PAXS + " SET " +
            PAX_FIRST_NAME + " = ?, " + PAX_SECOND_NAME + " = ?, " + PAX_GENDER + " = ?, " +
            PAX_NATIONALITY + " = ?, " + PAX_PASSPORT + " = ?, " + PAX_BIRTHDAY + " = ?, " +
            PAX_PRICE_ID + " = ?" +
            " WHERE " + PAX_PASSPORT + " = ?";

    static final String QUERY_CHANGE_FLIGHT_DATA_PREP = "UPDATE " + TABLE_FLIGHTS + " SET " +
            FLIGHT_DEPARTURE + " = ?, " + FLIGHT_DEP_TIME + " = ?, " + FLIGHT_DEP_TERMINAL  + " = ?, " +
            FLIGHT_ARRIVAL + " = ?, " + FLIGHT_ARR_TIME  + " = ?, " + FLIGHT_ARR_TERMINAL + " = ?, " +
            FLIGHT_STATUS + " = ?" + " WHERE " + FLIGHT_NUMBER  + " = ?";

    static final String QUERY_CHANGE_PRICE_PREP = "UPDATE " + TABLE_PRICES + " SET " +
            PRICE_FARE + " = ?" +
            " WHERE " + PRICE_FLIGHT_ID + " = ? AND " + PRICE_CABIN + " = ?";

    static final String QUERY_DELETE_PASSENGER_PREP = "DELETE FROM " + TABLE_PAXS +
            " WHERE " + PAX_PASSPORT + " = ?";

    static final String QUERY_DELETE_FLIGHT_PREP = "DELETE FROM " + TABLE_FLIGHTS +
            " WHERE " + FLIGHT_ID + " = ?";


    static final String QUERY_USER_CHECK_PASSWORD_PREP = "SELECT EXISTS (SELECT * FROM " +
            TABLE_USERS + " WHERE " + TABLE_USERS + "." + USER_USERNAME +
            " = ? AND " + USER_PASSWORD + " = ?)";

    static final String QUERY_USER_GET_USER_PERMISSION_PREP = "SELECT " + USER_PERMISSION +  " FROM " +
            TABLE_USERS + " WHERE " + USER_USERNAME + " = ? AND " + USER_PASSWORD + " = ?";
}
