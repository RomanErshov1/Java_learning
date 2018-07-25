package com.era.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    static Connection getConnection(){
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" +
                    "localhost:" +
                    "3306/" +
                    "my_database?" +
                    "user=root&" +
                    "password=LbgjkmUthwf&" +
                    "UseSSL=false";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
