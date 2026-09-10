package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "skyfly";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private static final String URL = SERVER_URL + DATABASE_NAME;

    // Get connection to SkyFly database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Create database if it does not already exist
    public static void createDatabase() {

        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;

        try (Connection con = DriverManager.getConnection(
                    SERVER_URL,
                    USERNAME,
                    PASSWORD);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);

            System.out.println("Database checked/created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Delete database if it does already exist
    public static void deleteDatabase() {

        String sql = "DROP DATABASE IF EXISTS " + DATABASE_NAME;

        try (Connection con = DriverManager.getConnection(
                    SERVER_URL,
                    USERNAME,
                    PASSWORD);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);

            System.out.println("Database deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create/Migrate all database tables
    public static void migrate() {

        // Make sure database exists first
        createDatabase();

        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {

            // ---------------------------------------------------------
            // USERS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "user_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "phone VARCHAR(15), " +
                "role VARCHAR(20) NOT NULL DEFAULT 'USER'" +
                ")"
            );

            // ---------------------------------------------------------
            // AIRPORTS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS airports (" +
                "airport_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "airport_code VARCHAR(10) NOT NULL UNIQUE, " +
                "airport_name VARCHAR(100) NOT NULL, " +
                "city VARCHAR(100) NOT NULL, " +
                "country VARCHAR(100) NOT NULL" +
                ")"
            );

            // ---------------------------------------------------------
            // AIRCRAFT TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS aircraft (" +
                "aircraft_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "aircraft_number VARCHAR(50) NOT NULL UNIQUE, " +
                "model VARCHAR(50) NOT NULL, " +
                "total_seats INT NOT NULL" +
                ")"
            );

            // ---------------------------------------------------------
            // FLIGHTS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS flights (" +
                "flight_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "flight_number VARCHAR(20) NOT NULL UNIQUE, " +
                "source_airport_id BIGINT NOT NULL, " +
                "destination_airport_id BIGINT NOT NULL, " +
                "aircraft_id BIGINT NOT NULL, " +
                "departure_time DATETIME NOT NULL, " +
                "arrival_time DATETIME NOT NULL, " +
                "price DECIMAL(10,2) NOT NULL, " +
                "available_seats INT NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +

                "FOREIGN KEY (source_airport_id) " +
                "REFERENCES airports(airport_id), " +

                "FOREIGN KEY (destination_airport_id) " +
                "REFERENCES airports(airport_id), " +

                "FOREIGN KEY (aircraft_id) " +
                "REFERENCES aircraft(aircraft_id)" +

                ")"
            );

            // ---------------------------------------------------------
            // BOOKINGS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id BIGINT NOT NULL, " +
                "flight_id BIGINT NOT NULL, " +
                "booking_date DATETIME NOT NULL, " +
                "total_amount DECIMAL(10,2) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "pnr VARCHAR(20) NOT NULL UNIQUE, " +

                "FOREIGN KEY (user_id) " +
                "REFERENCES users(user_id), " +

                "FOREIGN KEY (flight_id) " +
                "REFERENCES flights(flight_id)" +

                ")"
            );

            // ---------------------------------------------------------
            // PASSENGERS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS passengers (" +
                "passenger_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "booking_id BIGINT NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL, " +
                "gender VARCHAR(20), " +
                "seat_number VARCHAR(10), " +

                "FOREIGN KEY (booking_id) " +
                "REFERENCES bookings(booking_id)" +

                ")"
            );

            // ---------------------------------------------------------
            // PAYMENTS TABLE
            // ---------------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS payments (" +
                "payment_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "booking_id BIGINT NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, " +
                "payment_date DATETIME NOT NULL, " +
                "payment_method VARCHAR(30), " +
                "status VARCHAR(20) NOT NULL, " +
                "transaction_id VARCHAR(100) UNIQUE, " +

                "FOREIGN KEY (booking_id) " +
                "REFERENCES bookings(booking_id)" +

                ")"
            );

            System.out.println("Database migration completed successfully.");

        } catch (SQLException e) {
            System.out.println("Database migration failed.");
            e.printStackTrace();
        }
    }
}
