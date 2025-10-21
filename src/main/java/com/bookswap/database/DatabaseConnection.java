package com.bookswap.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana";
    private static final String USER = "tads24_ana";
    private static final String PASSWORD = "tads24_ana";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}   