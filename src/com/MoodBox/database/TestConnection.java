package com.MoodBox.database;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Connessione al database riuscita!");
            DatabaseConnection.closeConnection(conn);
        } else {
            System.out.println("Connessione al database FALLITA.");
        }
    }
}
