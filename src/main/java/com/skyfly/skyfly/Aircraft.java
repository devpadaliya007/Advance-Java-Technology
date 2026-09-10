package com.skyfly.skyfly;

public class Aircraft {

    private Long aircraftId;
    private String aircraftNumber;
    private String model;
    private int totalSeats;

    public Aircraft() {
    }

    public Aircraft(Long aircraftId, String aircraftNumber,
                       String model, int totalSeats) {
        this.aircraftId = aircraftId;
        this.aircraftNumber = aircraftNumber;
        this.model = model;
        this.totalSeats = totalSeats;
    }

    public Aircraft(String aircraftNumber,
                       String model, int totalSeats) {
        this.aircraftNumber = aircraftNumber;
        this.model = model;
        this.totalSeats = totalSeats;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }

    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
