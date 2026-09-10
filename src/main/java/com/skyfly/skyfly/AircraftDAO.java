package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AircraftDAO {

    // CREATE
    public void addAircraft(Aircraft aircraft) {

        String sql = "INSERT INTO aircraft " +
                     "(aircraft_number, model, total_seats) " +
                     "VALUES (?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aircraft.getAircraftNumber());
            ps.setString(2, aircraft.getModel());
            ps.setInt(3, aircraft.getTotalSeats());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all aircraft
    public List<Aircraft> getAllAircraft() {

        List<Aircraft> aircraftList = new ArrayList<>();

        String sql = "SELECT * FROM aircraft";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Aircraft aircraft = new Aircraft();

                aircraft.setAircraftId(rs.getLong("aircraft_id"));
                aircraft.setAircraftNumber(
                        rs.getString("aircraft_number")
                );
                aircraft.setModel(
                        rs.getString("model")
                );
                aircraft.setTotalSeats(
                        rs.getInt("total_seats")
                );

                aircraftList.add(aircraft);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return aircraftList;
    }

    // READ - Get aircraft by ID
    public Aircraft getAircraftById(Long aircraftId) {

        String sql = "SELECT * FROM aircraft WHERE aircraft_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, aircraftId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Aircraft aircraft = new Aircraft();

                aircraft.setAircraftId(rs.getLong("aircraft_id"));
                aircraft.setAircraftNumber(
                        rs.getString("aircraft_number")
                );
                aircraft.setModel(
                        rs.getString("model")
                );
                aircraft.setTotalSeats(
                        rs.getInt("total_seats")
                );

                return aircraft;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * UPDATE is not possbile after a aircraft is made.
     *
     * If an aircraft is no longer in use, it can be deleted.
     * and a new aircraft record can be created instead.
     */

    // DELETE
    public void deleteAircraft(Long aircraftId) {

        String sql = "DELETE FROM aircraft WHERE aircraft_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, aircraftId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
