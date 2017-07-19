package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {


    private static DataSource dataSource = new DataSource();
    private DataSource() {}
    public static DataSource getDataSource() {
        return dataSource;
    }

    private static Connection conn;
    static Connection getConn() {
        return conn;
    }

    private static final String DB_NAME_SQLITE = "airport.db";
    private static final String CONNECTION_STRING_SQLITE = "jdbc:sqlite:D:\\IdeaProjects\\Airport\\src\\database\\" + DB_NAME_SQLITE;


    private static final String DB_NAME_MYSQL = "airport";
    private static final String CONNECTION_STRING_MYSQL = "jdbc:mysql://88.198.104.94/" + DB_NAME_MYSQL;
    private static final String CONNECTION_USER = "airport";
    private static final String CONNECTION_PASSWORD = "7Gxcw6fmxnaunmKB";


    public boolean open(){

//        try {
//            conn = DriverManager.getConnection(CONNECTION_STRING_SQLITE);
//            return true;
//        } catch (SQLException e) {
//            System.out.println("Can't connect to database: " + e.getMessage());
//            return false;
//        }
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING_MYSQL,CONNECTION_USER,CONNECTION_PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Can't connect to database: " + e.getMessage());
            return false;
        }


    }

    void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Can't close connection: " + e.getMessage());
        }
    }
}
