package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // CREATE
    public void addBooking(Booking booking) {

        String sql = "INSERT INTO bookings " +
                "(user_id, flight_id, booking_date, total_amount, status, pnr) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, booking.getUserId());
            ps.setLong(2, booking.getFlightId());
            ps.setTimestamp(3, booking.getBookingDate());
            ps.setBigDecimal(4, booking.getTotalAmount());
            ps.setString(5, booking.getStatus());
            ps.setString(6, booking.getPnr());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all bookings
    public List<Booking> getAllBookings() {

        List<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM bookings";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getLong("booking_id"));
                booking.setUserId(rs.getLong("user_id"));
                booking.setFlightId(rs.getLong("flight_id"));
                booking.setBookingDate(
                        rs.getTimestamp("booking_date")
                );
                booking.setTotalAmount(
                        rs.getBigDecimal("total_amount")
                );
                booking.setStatus(
                        rs.getString("status")
                );
                booking.setPnr(
                        rs.getString("pnr")
                );

                bookings.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }

    // READ - Get booking by ID
    public Booking getBookingById(Long bookingId) {

        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getLong("booking_id"));
                booking.setUserId(rs.getLong("user_id"));
                booking.setFlightId(rs.getLong("flight_id"));
                booking.setBookingDate(
                        rs.getTimestamp("booking_date")
                );
                booking.setTotalAmount(
                        rs.getBigDecimal("total_amount")
                );
                booking.setStatus(
                        rs.getString("status")
                );
                booking.setPnr(
                        rs.getString("pnr")
                );

                return booking;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // READ - Get all bookings made by a particular user
    public List<Booking> getBookingsByUserId(Long userId) {

        List<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getLong("booking_id"));
                booking.setUserId(rs.getLong("user_id"));
                booking.setFlightId(rs.getLong("flight_id"));
                booking.setBookingDate(
                        rs.getTimestamp("booking_date")
                );
                booking.setTotalAmount(
                        rs.getBigDecimal("total_amount")
                );
                booking.setStatus(
                        rs.getString("status")
                );
                booking.setPnr(
                        rs.getString("pnr")
                );

                bookings.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }

    // READ - Find booking using PNR
    public Booking getBookingByPNR(String pnr) {

        String sql = "SELECT * FROM bookings WHERE pnr = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getLong("booking_id"));
                booking.setUserId(rs.getLong("user_id"));
                booking.setFlightId(rs.getLong("flight_id"));
                booking.setBookingDate(
                        rs.getTimestamp("booking_date")
                );
                booking.setTotalAmount(
                        rs.getBigDecimal("total_amount")
                );
                booking.setStatus(
                        rs.getString("status")
                );
                booking.setPnr(
                        rs.getString("pnr")
                );

                return booking;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    // Booking ID is passed separately from the DTO
    public void updateBooking(Long bookingId, Booking booking) {

        String sql = "UPDATE bookings SET " +
                "user_id = ?, " +
                "flight_id = ?, " +
                "booking_date = ?, " +
                "total_amount = ?, " +
                "status = ?, " +
                "pnr = ? " +
                "WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, booking.getUserId());
            ps.setLong(2, booking.getFlightId());
            ps.setTimestamp(3, booking.getBookingDate());
            ps.setBigDecimal(4, booking.getTotalAmount());
            ps.setString(5, booking.getStatus());
            ps.setString(6, booking.getPnr());

            ps.setLong(7, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CANCEL BOOKING
    // We change the status instead of immediately deleting the booking.
    public void cancelBooking(Long bookingId) {

        String sql = "UPDATE bookings SET status = 'CANCELLED' " +
                     "WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK - Is the booking already cancelled?
    public boolean isBookingCancelled(Long bookingId) {

        String sql = "SELECT status FROM bookings WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return "CANCELLED".equalsIgnoreCase(
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // CHECK - Does a PNR already exist?
    public boolean pnrExists(String pnr) {

        String sql = "SELECT booking_id FROM bookings WHERE pnr = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    public void deleteBooking(Long bookingId) {

        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
