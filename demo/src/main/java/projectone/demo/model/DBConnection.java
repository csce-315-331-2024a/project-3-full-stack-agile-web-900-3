package projectone.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce331_900_03_db";
    private static final String USER = "csce331_900_03_user";
    private static final String PASSWORD = "snBjvgg8";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
