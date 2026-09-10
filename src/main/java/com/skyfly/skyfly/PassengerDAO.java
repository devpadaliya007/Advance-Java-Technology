package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    // CREATE
    public void addPassenger(Passenger passenger) {

        String sql = "INSERT INTO passengers " +
                "(booking_id, name, age, gender, seat_number) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, passenger.getBookingId());
            ps.setString(2, passenger.getName());
            ps.setInt(3, passenger.getAge());
            ps.setString(4, passenger.getGender());
            ps.setString(5, passenger.getSeatNumber());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all passengers
    public List<Passenger> getAllPassengers() {

        List<Passenger> passengers = new ArrayList<>();

        String sql = "SELECT * FROM passengers";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(
                        rs.getLong("passenger_id")
                );

                passenger.setBookingId(
                        rs.getLong("booking_id")
                );

                passenger.setName(
                        rs.getString("name")
                );

                passenger.setAge(
                        rs.getInt("age")
                );

                passenger.setGender(
                        rs.getString("gender")
                );

                passenger.setSeatNumber(
                        rs.getString("seat_number")
                );

                passengers.add(passenger);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passengers;
    }

    // READ - Get passenger by ID
    public Passenger getPassengerById(Long passengerId) {

        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, passengerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(
                        rs.getLong("passenger_id")
                );

                passenger.setBookingId(
                        rs.getLong("booking_id")
                );

                passenger.setName(
                        rs.getString("name")
                );

                passenger.setAge(
                        rs.getInt("age")
                );

                passenger.setGender(
                        rs.getString("gender")
                );

                passenger.setSeatNumber(
                        rs.getString("seat_number")
                );

                return passenger;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // READ - Get passengers belonging to a booking
    public List<Passenger> getPassengersByBookingId(Long bookingId) {

        List<Passenger> passengers = new ArrayList<>();

        String sql = "SELECT * FROM passengers WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(
                        rs.getLong("passenger_id")
                );

                passenger.setBookingId(
                        rs.getLong("booking_id")
                );

                passenger.setName(
                        rs.getString("name")
                );

                passenger.setAge(
                        rs.getInt("age")
                );

                passenger.setGender(
                        rs.getString("gender")
                );

                passenger.setSeatNumber(
                        rs.getString("seat_number")
                );

                passengers.add(passenger);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passengers;
    }

    // UPDATE
    // Passenger ID is passed separately from the DTO.
    public void updatePassenger(Long passengerId,
                                Passenger passenger) {

        String sql = "UPDATE passengers SET " +
                "booking_id = ?, " +
                "name = ?, " +
                "age = ?, " +
                "gender = ?, " +
                "seat_number = ? " +
                "WHERE passenger_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, passenger.getBookingId());
            ps.setString(2, passenger.getName());
            ps.setInt(3, passenger.getAge());
            ps.setString(4, passenger.getGender());
            ps.setString(5, passenger.getSeatNumber());

            ps.setLong(6, passengerId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletePassenger(Long passengerId) {

        String sql = "DELETE FROM passengers WHERE passenger_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, passengerId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK - Is a seat already assigned?
    public boolean seatExists(Long bookingId, String seatNumber) {

        String sql = "SELECT passenger_id FROM passengers " +
                     "WHERE booking_id = ? AND seat_number = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);
            ps.setString(2, seatNumber);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
