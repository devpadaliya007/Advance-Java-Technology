package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO {

    // CREATE
    public void addAirport(Airport airport) {

        String sql = "INSERT INTO airports " +
                     "(airport_code, airport_name, city, country) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, airport.getAirportCode());
            ps.setString(2, airport.getAirportName());
            ps.setString(3, airport.getCity());
            ps.setString(4, airport.getCountry());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all airports
    public List<Airport> getAllAirports() {

        List<Airport> airports = new ArrayList<>();

        String sql = "SELECT * FROM airports";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Airport airport = new Airport();

                airport.setAirportId(rs.getLong("airport_id"));
                airport.setAirportCode(rs.getString("airport_code"));
                airport.setAirportName(rs.getString("airport_name"));
                airport.setCity(rs.getString("city"));
                airport.setCountry(rs.getString("country"));

                airports.add(airport);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return airports;
    }

    // READ - Get airport by ID
    public Airport getAirportById(Long airportId) {

        String sql = "SELECT * FROM airports WHERE airport_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, airportId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Airport airport = new Airport();

                airport.setAirportId(rs.getLong("airport_id"));
                airport.setAirportCode(rs.getString("airport_code"));
                airport.setAirportName(rs.getString("airport_name"));
                airport.setCity(rs.getString("city"));
                airport.setCountry(rs.getString("country"));

                return airport;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    // Airport once create can't be upadted...
    // it is idempotent object.
    // you delete previous airport and create new one, like we do in real world.

    // DELETE
    public void deleteAirport(Long airportId) {

        String sql = "DELETE FROM airports WHERE airport_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, airportId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
