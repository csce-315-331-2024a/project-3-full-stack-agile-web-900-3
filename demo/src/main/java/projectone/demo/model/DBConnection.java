package projectone.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Provides a centralized method to manage database connections to a PostgreSQL database.
 * This class defines constants for the URL, user, and password to connect to the database
 * and provides a method to get a connection instance.
 */
public class DBConnection {
    private static final String URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce331_900_03_db";
    private static final String USER = "csce331_900_03_user";
    private static final String PASSWORD = "snBjvgg8";
      /**
     * Attempts to establish a connection to the database using predefined credentials.
     * 
     * @return A {@link Connection} object that represents a connection to the database.
     *         Returns {@code null} if the connection fails.
     */
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
