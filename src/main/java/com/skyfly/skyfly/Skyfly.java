package com.skyfly.skyfly;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Skyfly {

    public static void main(String[] args) {
        
        // Clean UP
        DBManager.deleteDatabase();

        // ---------------------------------------------------------
        // 1. CREATE DATABASE AND TABLES
        // ---------------------------------------------------------

        DBManager.migrate();

        System.out.println("=================================");
        System.out.println("SKYFLY DATABASE DEMO");
        System.out.println("=================================");

        // ---------------------------------------------------------
        // 2. CREATE DAOs
        // ---------------------------------------------------------

        UserDAO userDAO = new UserDAO();
        AirportDAO airportDAO = new AirportDAO();
        AircraftDAO aircraftDAO = new AircraftDAO();
        FlightDAO flightDAO = new FlightDAO();
        BookingDAO bookingDAO = new BookingDAO();
        PassengerDAO passengerDAO = new PassengerDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        // ---------------------------------------------------------
        // 3. INSERT USERS
        // ---------------------------------------------------------

        System.out.println("\nAdding users...");

        User user = new User();

        user.setName("Rahul Sharma");
        user.setEmail("rahul@skyfly.com");
        user.setPassword("123456");
        user.setPhone("9876543210");
        user.setRole("USER");

        userDAO.addUser(user);

        User admin = new User();

        admin.setName("SkyFly Admin");
        admin.setEmail("admin@skyfly.com");
        admin.setPassword("admin123");
        admin.setPhone("9999999999");
        admin.setRole("ADMIN");

        userDAO.addUser(admin);

        System.out.println("Users inserted.");

        // ---------------------------------------------------------
        // 4. INSERT AIRPORTS
        // ---------------------------------------------------------

        System.out.println("\nAdding airports...");

        Airport delhi = new Airport(
                "DEL",
                "Indira Gandhi International Airport",
                "Delhi",
                "India"
        );

        Airport mumbai = new Airport(
                "BOM",
                "Chhatrapati Shivaji Maharaj Airport",
                "Mumbai",
                "India"
        );

        Airport ahmedabad = new Airport(
                "AMD",
                "Sardar Vallabhbhai Patel Airport",
                "Ahmedabad",
                "India"
        );

        airportDAO.addAirport(delhi);
        airportDAO.addAirport(mumbai);
        airportDAO.addAirport(ahmedabad);

        System.out.println("Airports inserted.");

        // ---------------------------------------------------------
        // 5. GET AIRPORTS
        // ---------------------------------------------------------

        List<Airport> airports = airportDAO.getAllAirports();

        Long delhiId = null;
        Long mumbaiId = null;

        for (Airport airport : airports) {

            if ("DEL".equals(airport.getAirportCode())) {
                delhiId = airport.getAirportId();
            }

            if ("BOM".equals(airport.getAirportCode())) {
                mumbaiId = airport.getAirportId();
            }
        }

        // ---------------------------------------------------------
        // 6. INSERT AIRCRAFT
        // ---------------------------------------------------------

        System.out.println("\nAdding aircraft...");

        Aircraft aircraft = new Aircraft (
                "SF-A001",
                "Boeing 737",
                180
        );

        aircraftDAO.addAircraft(aircraft);

        System.out.println("Aircraft inserted.");

        // Get aircraft ID
        List<Aircraft> aircraftList =
                aircraftDAO.getAllAircraft();

        Long aircraftId = aircraftList
                .get(aircraftList.size() - 1)
                .getAircraftId();

        // ---------------------------------------------------------
        // 7. INSERT FLIGHT
        // ---------------------------------------------------------

        System.out.println("\nAdding flight...");

        Timestamp departure = Timestamp.valueOf(
                LocalDateTime.now().plusDays(2)
        );

        Timestamp arrival = Timestamp.valueOf(
                LocalDateTime.now().plusDays(2).plusHours(2)
        );

        Flight flight = new Flight(
                "SF101",
                delhiId,
                mumbaiId,
                aircraftId,
                departure,
                arrival,
                new BigDecimal("5500.00"),
                180,
                "SCHEDULED"
        );

        flightDAO.addFlight(flight);

        System.out.println("Flight inserted.");

        // Get flight ID
        List<Flight> flights =
                flightDAO.getAllFlights();

        Long flightId = flights
                .get(flights.size() - 1)
                .getFlightId();

        // ---------------------------------------------------------
        // 8. GET USER ID
        // ---------------------------------------------------------

        List<User> users =
                userDAO.getAllUsers();

        Long userId = null;

        for (User u : users) {

            if ("rahul@skyfly.com".equals(u.getEmail())) {
                userId = u.getUserId();
                break;
            }
        }

        // ---------------------------------------------------------
        // 9. CHECK AVAILABLE SEATS
        // ---------------------------------------------------------

        System.out.println("\nChecking available seats...");

        boolean seatsAvailable =
                flightDAO.hasAvailableSeats(flightId, 2);

        System.out.println(
                "Are 2 seats available? " + seatsAvailable
        );

        // ---------------------------------------------------------
        // 10. CREATE BOOKING
        // ---------------------------------------------------------

        System.out.println("\nCreating booking...");

        Booking booking = new Booking(
                userId,
                flightId,
                new Timestamp(System.currentTimeMillis()),
                new BigDecimal("11000.00"),
                "CONFIRMED",
                "SF" + System.currentTimeMillis()
        );

        bookingDAO.addBooking(booking);

        System.out.println("Booking inserted.");

        // Get booking ID
        Booking savedBooking =
                bookingDAO.getBookingByPNR(booking.getPnr());

        Long bookingId =
                savedBooking.getBookingId();

        // ---------------------------------------------------------
        // 11. REDUCE AVAILABLE SEATS
        // ---------------------------------------------------------

        flightDAO.reduceAvailableSeats(
                flightId,
                2
        );

        System.out.println("2 seats reserved.");

        // ---------------------------------------------------------
        // 12. ADD PASSENGERS
        // ---------------------------------------------------------

        System.out.println("\nAdding passengers...");

        Passenger passenger1 = new Passenger(
                bookingId,
                "Rahul Sharma",
                22,
                "Male",
                "12A"
        );

        Passenger passenger2 = new Passenger(
                bookingId,
                "Priya Sharma",
                21,
                "Female",
                "12B"
        );

        passengerDAO.addPassenger(passenger1);
        passengerDAO.addPassenger(passenger2);

        System.out.println("Passengers inserted.");

        // ---------------------------------------------------------
        // 13. ADD PAYMENT
        // ---------------------------------------------------------

        System.out.println("\nAdding payment...");

        Payment payment = new Payment(
                bookingId,
                new BigDecimal("11000.00"),
                new Timestamp(System.currentTimeMillis()),
                "UPI",
                "SUCCESS",
                "TXN" + System.currentTimeMillis()
        );

        paymentDAO.addPayment(payment);

        System.out.println("Payment inserted.");

        // ---------------------------------------------------------
        // 14. DISPLAY DATA
        // ---------------------------------------------------------

        System.out.println("\n=================================");
        System.out.println("DATABASE DATA");
        System.out.println("=================================");

        System.out.println("\n--- USERS ---");

        for (User u : userDAO.getAllUsers()) {

            System.out.println(
                    u.getUserId() + " | " +
                    u.getName() + " | " +
                    u.getEmail() + " | " +
                    u.getRole()
            );
        }

        System.out.println("\n--- AIRPORTS ---");

        for (Airport a : airportDAO.getAllAirports()) {

            System.out.println(
                    a.getAirportId() + " | " +
                    a.getAirportCode() + " | " +
                    a.getAirportName() + " | " +
                    a.getCity()
            );
        }

        System.out.println("\n--- AIRCRAFT ---");

        for (Aircraft a : aircraftDAO.getAllAircraft()) {

            System.out.println(
                    a.getAircraftId() + " | " +
                    a.getAircraftNumber() + " | " +
                    a.getModel() + " | " +
                    a.getTotalSeats()
            );
        }

        System.out.println("\n--- FLIGHTS ---");

        for (Flight f : flightDAO.getAllFlights()) {

            System.out.println(
                    f.getFlightId() + " | " +
                    f.getFlightNumber() + " | " +
                    "Available seats: " +
                    f.getAvailableSeats() + " | " +
                    f.getStatus()
            );
        }

        System.out.println("\n--- BOOKINGS ---");

        for (Booking b : bookingDAO.getAllBookings()) {

            System.out.println(
                    b.getBookingId() + " | " +
                    "PNR: " + b.getPnr() + " | " +
                    "Status: " + b.getStatus() + " | " +
                    "Amount: " + b.getTotalAmount()
            );
        }

        System.out.println("\n--- PASSENGERS ---");

        for (Passenger p :
                passengerDAO.getPassengersByBookingId(bookingId)) {

            System.out.println(
                    p.getPassengerId() + " | " +
                    p.getName() + " | " +
                    "Seat: " + p.getSeatNumber()
            );
        }

        System.out.println("\n--- PAYMENT ---");

        Payment savedPayment =
                paymentDAO.getPaymentByBookingId(bookingId);

        if (savedPayment != null) {

            System.out.println(
                    savedPayment.getPaymentId() + " | " +
                    "Amount: " + savedPayment.getAmount() + " | " +
                    "Method: " + savedPayment.getPaymentMethod() + " | " +
                    "Status: " + savedPayment.getStatus()
            );
        }

        System.out.println("\n=================================");
        System.out.println("SKYFLY DATABASE DEMO COMPLETED");
        System.out.println("=================================");
    }
}
