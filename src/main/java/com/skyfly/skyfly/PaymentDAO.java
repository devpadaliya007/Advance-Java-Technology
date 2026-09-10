package com.skyfly.skyfly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // CREATE
    public void addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                "(booking_id, amount, payment_date, payment_method, " +
                "status, transaction_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, payment.getBookingId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setTimestamp(3, payment.getPaymentDate());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getStatus());
            ps.setString(6, payment.getTransactionId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ - Get all payments
    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        rs.getLong("payment_id")
                );

                payment.setBookingId(
                        rs.getLong("booking_id")
                );

                payment.setAmount(
                        rs.getBigDecimal("amount")
                );

                payment.setPaymentDate(
                        rs.getTimestamp("payment_date")
                );

                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                payment.setStatus(
                        rs.getString("status")
                );

                payment.setTransactionId(
                        rs.getString("transaction_id")
                );

                payments.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    // READ - Get payment by ID
    public Payment getPaymentById(Long paymentId) {

        String sql = "SELECT * FROM payments WHERE payment_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        rs.getLong("payment_id")
                );

                payment.setBookingId(
                        rs.getLong("booking_id")
                );

                payment.setAmount(
                        rs.getBigDecimal("amount")
                );

                payment.setPaymentDate(
                        rs.getTimestamp("payment_date")
                );

                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                payment.setStatus(
                        rs.getString("status")
                );

                payment.setTransactionId(
                        rs.getString("transaction_id")
                );

                return payment;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // READ - Get payment for a particular booking
    public Payment getPaymentByBookingId(Long bookingId) {

        String sql = "SELECT * FROM payments WHERE booking_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        rs.getLong("payment_id")
                );

                payment.setBookingId(
                        rs.getLong("booking_id")
                );

                payment.setAmount(
                        rs.getBigDecimal("amount")
                );

                payment.setPaymentDate(
                        rs.getTimestamp("payment_date")
                );

                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );

                payment.setStatus(
                        rs.getString("status")
                );

                payment.setTransactionId(
                        rs.getString("transaction_id")
                );

                return payment;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    // Payment ID is passed separately from the DTO.
    public void updatePayment(Long paymentId, Payment payment) {

        String sql = "UPDATE payments SET " +
                "booking_id = ?, " +
                "amount = ?, " +
                "payment_date = ?, " +
                "payment_method = ?, " +
                "status = ?, " +
                "transaction_id = ? " +
                "WHERE payment_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, payment.getBookingId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setTimestamp(3, payment.getPaymentDate());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getStatus());
            ps.setString(6, payment.getTransactionId());

            ps.setLong(7, paymentId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK PAYMENT AS SUCCESSFUL
    public void markPaymentSuccessful(Long paymentId) {

        String sql = "UPDATE payments SET status = 'SUCCESS' " +
                     "WHERE payment_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK PAYMENT AS FAILED
    public void markPaymentFailed(Long paymentId) {

        String sql = "UPDATE payments SET status = 'FAILED' " +
                     "WHERE payment_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK - Has this transaction already been recorded?
    public boolean transactionExists(String transactionId) {

        String sql = "SELECT payment_id FROM payments " +
                     "WHERE transaction_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, transactionId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    public void deletePayment(Long paymentId) {

        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
