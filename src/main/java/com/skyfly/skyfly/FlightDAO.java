package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    // CREATE
    public void addFlight(Flight flight) {

        String sql = "INSERT INTO flights " +
                "(flight_number, source_airport_id, destination_airport_id, " +
                "aircraft_id, departure_time, arrival_time, price, " +
                "available_seats, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, flight.getFlightNumber());
            ps.setLong(2, flight.getSourceAirportId());
            ps.setLong(3, flight.getDestinationAirportId());
            ps.setLong(4, flight.getAircraftId());
            ps.setTimestamp(5, flight.getDepartureTime());
            ps.setTimestamp(6, flight.getArrivalTime());
            ps.setBigDecimal(7, flight.getPrice());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setString(9, flight.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all flights
    public List<Flight> getAllFlights() {

        List<Flight> flights = new ArrayList<>();

        String sql = "SELECT * FROM flights";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Flight flight = new Flight();

                flight.setFlightId(rs.getLong("flight_id"));
                flight.setFlightNumber(rs.getString("flight_number"));
                flight.setSourceAirportId(rs.getLong("source_airport_id"));
                flight.setDestinationAirportId(
                        rs.getLong("destination_airport_id")
                );
                flight.setAircraftId(rs.getLong("aircraft_id"));
                flight.setDepartureTime(
                        rs.getTimestamp("departure_time")
                );
                flight.setArrivalTime(
                        rs.getTimestamp("arrival_time")
                );
                flight.setPrice(
                        rs.getBigDecimal("price")
                );
                flight.setAvailableSeats(
                        rs.getInt("available_seats")
                );
                flight.setStatus(
                        rs.getString("status")
                );

                flights.add(flight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flights;
    }

    // READ - Get flight by ID
    public Flight getFlightById(Long flightId) {

        String sql = "SELECT * FROM flights WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, flightId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Flight flight = new Flight();

                flight.setFlightId(rs.getLong("flight_id"));
                flight.setFlightNumber(rs.getString("flight_number"));
                flight.setSourceAirportId(
                        rs.getLong("source_airport_id")
                );
                flight.setDestinationAirportId(
                        rs.getLong("destination_airport_id")
                );
                flight.setAircraftId(
                        rs.getLong("aircraft_id")
                );
                flight.setDepartureTime(
                        rs.getTimestamp("departure_time")
                );
                flight.setArrivalTime(
                        rs.getTimestamp("arrival_time")
                );
                flight.setPrice(
                        rs.getBigDecimal("price")
                );
                flight.setAvailableSeats(
                        rs.getInt("available_seats")
                );
                flight.setStatus(
                        rs.getString("status")
                );

                return flight;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    // Flight ID is passed separately from the DTO
    public void updateFlight(Long flightId, Flight flight) {

        String sql = "UPDATE flights SET " +
                "flight_number = ?, " +
                "source_airport_id = ?, " +
                "destination_airport_id = ?, " +
                "aircraft_id = ?, " +
                "departure_time = ?, " +
                "arrival_time = ?, " +
                "price = ?, " +
                "available_seats = ?, " +
                "status = ? " +
                "WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, flight.getFlightNumber());
            ps.setLong(2, flight.getSourceAirportId());
            ps.setLong(3, flight.getDestinationAirportId());
            ps.setLong(4, flight.getAircraftId());
            ps.setTimestamp(5, flight.getDepartureTime());
            ps.setTimestamp(6, flight.getArrivalTime());
            ps.setBigDecimal(7, flight.getPrice());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setString(9, flight.getStatus());

            ps.setLong(10, flightId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteFlight(Long flightId) {

        String sql = "DELETE FROM flights WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, flightId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK - Does the flight have at least one available seat?
    public boolean hasAvailableSeats(Long flightId) {

        String sql = "SELECT available_seats FROM flights WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, flightId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("available_seats") > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // CHECK - Does the flight have the requested number of seats?
    public boolean hasAvailableSeats(Long flightId, int requiredSeats) {

        String sql = "SELECT available_seats FROM flights WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, flightId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("available_seats") >= requiredSeats;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // UPDATE - Reduce available seats after booking
    public void reduceAvailableSeats(Long flightId, int seats) {

        String sql = "UPDATE flights " +
                "SET available_seats = available_seats - ? " +
                "WHERE flight_id = ? AND available_seats >= ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seats);
            ps.setLong(2, flightId);
            ps.setInt(3, seats);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE - Increase available seats after cancellation
    public void increaseAvailableSeats(Long flightId, int seats) {

        String sql = "UPDATE flights " +
                "SET available_seats = available_seats + ? " +
                "WHERE flight_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seats);
            ps.setLong(2, flightId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
