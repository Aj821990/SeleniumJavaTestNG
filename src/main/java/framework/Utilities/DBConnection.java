package framework.Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    public static Statement getConnection() {
        String databaseURL = "jdbc:mysql://";
        String user = "abcd";
        String password = "abcd";

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to Database....");
            connection = java.sql.DriverManager.getConnection(databaseURL, user, password);
            if (connection != null) {
                System.out.println("Connected to the Database....");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
