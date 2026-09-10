package com.skyfly.skyfly;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Flight {

    private Long flightId;
    private String flightNumber;
    private Long sourceAirportId;
    private Long destinationAirportId;
    private Long aircraftId;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private BigDecimal price;
    private int availableSeats;
    private String status;

    public Flight() {
    }

    public Flight(Long flightId, String flightNumber,
                     Long sourceAirportId, Long destinationAirportId,
                     Long aircraftId, Timestamp departureTime,
                     Timestamp arrivalTime, BigDecimal price,
                     int availableSeats, String status) {

        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.sourceAirportId = sourceAirportId;
        this.destinationAirportId = destinationAirportId;
        this.aircraftId = aircraftId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
        this.status = status;
    }

    public Flight(String flightNumber,
                     Long sourceAirportId, Long destinationAirportId,
                     Long aircraftId, Timestamp departureTime,
                     Timestamp arrivalTime, BigDecimal price,
                     int availableSeats, String status) {

        this.flightNumber = flightNumber;
        this.sourceAirportId = sourceAirportId;
        this.destinationAirportId = destinationAirportId;
        this.aircraftId = aircraftId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
        this.status = status;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Long getSourceAirportId() {
        return sourceAirportId;
    }

    public void setSourceAirportId(Long sourceAirportId) {
        this.sourceAirportId = sourceAirportId;
    }

    public Long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
