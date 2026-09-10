package com.skyfly.skyfly;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {

    private Long bookingId;
    private Long userId;
    private Long flightId;
    private Timestamp bookingDate;
    private BigDecimal totalAmount;
    private String status;
    private String pnr;

    public Booking() {
    }

    public Booking(Long bookingId, Long userId, Long flightId,
                      Timestamp bookingDate, BigDecimal totalAmount,
                      String status, String pnr) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.pnr = pnr;
    }

    public Booking(Long userId, Long flightId,
                      Timestamp bookingDate, BigDecimal totalAmount,
                      String status, String pnr) {

        this.userId = userId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.pnr = pnr;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
