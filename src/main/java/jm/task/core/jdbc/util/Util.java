package jm.task.core.jdbc.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String userName = "root";
        String password = "setchatka";

        return DriverManager.getConnection(url, userName, password);
    }
}
