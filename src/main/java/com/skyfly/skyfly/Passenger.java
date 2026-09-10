package com.skyfly.skyfly;

public class Passenger {

    private Long passengerId;
    private Long bookingId;
    private String name;
    private int age;
    private String gender;
    private String seatNumber;

    public Passenger() {
    }

    public Passenger(Long passengerId, Long bookingId,
                        String name, int age,
                        String gender, String seatNumber) {

        this.passengerId = passengerId;
        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }

    public Passenger(Long bookingId, String name,
                        int age, String gender,
                        String seatNumber) {

        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
