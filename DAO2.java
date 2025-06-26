import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DAO2 {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Fibo$#incmsacompssLMN**_**@secure..c@m";
    private static final String URL = "jdbc:mysql://localhost:3306/fbms";
    
    private final Connection connection;
    private String Query;
    private UserInfo user;

    public DAO2() throws Exception {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


// public boolean addFlight(Flight flight) throws SQLException {
//     // String userCheck = "SELECT id FROM users WHERE id = ?";
//     // try (PreparedStatement checkStmt = connection.prepareStatement(userCheck)) {
//     //     checkStmt.setInt(1, userId);
//     //     try (ResultSet rs = checkStmt.executeQuery()) {
//     //         if (!rs.next()) {
//     //             System.err.println("User with ID " + userId + " does not exist");
//     //             return false;
//     //         }
//     //     }
//     // }

//     String query = "INSERT INTO Flights (user_id, flightNumber, airline, origin, destination, " +
//                   "departureTime, arrivalTime, totalSeats, availableSeats, status, " +
//                   "economyPrice, businessPrice, firstClassPrice, flightType) " +
//                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setInt(1, flight.getId());
//         stmt.setInt(2, flight.getFlightNumber());
//         stmt.setString(3, flight.getAirline());
//         stmt.setString(4, flight.getOrigin());
//         stmt.setString(5, flight.getDestination());
//         stmt.setTimestamp(6, Timestamp.valueOf(flight.getDepartureTime()));
//         stmt.setTimestamp(7, Timestamp.valueOf(flight.getArrivalTime()));
//         stmt.setInt(8, flight.getTotalSeats());
//         stmt.setInt(9, flight.getAvailableSeats());
//         stmt.setString(10, flight.getFlightStatus());
//         stmt.setDouble(11, flight.getEconomyPrice());
//         stmt.setDouble(12, flight.getBusinessPrice());
//         stmt.setDouble(13, flight.getFirstClassPrice());
//         stmt.setString(14, flight.getFlightType());

//         return stmt.executeUpdate() > 0;
//     } catch (SQLException e) {
//         System.err.println("Error adding flight: " + e.getMessage());
//         return false;
//     }
// }
// // public int getUserId(String username, String password) throws SQLException {
// //     String query = "SELECT id FROM users WHERE username = ? AND password = ? AND role = 'AGENT'";
    
// //     try (PreparedStatement stmt = connection.prepareStatement(query)) {
// //         stmt.setString(1, username);
// //         stmt.setString(2, password); // Note: In production, you should use hashed passwords
        
// //         try (ResultSet rs = stmt.executeQuery()) {
// //             if (rs.next()) {
// //                 return rs.getInt("id");
// //             } else {
// //                 System.err.println("Invalid credentials or user is not an agent");
// //                 return -1;
// //             }
// //         }
// //     }
// // }

    // Flight Operations
    public boolean addFlight(Flight flight) throws SQLException {
    // First check if user exists
    if (!userExists(flight.getId())) {
        System.err.println("User with ID " + flight.getId() + " does not exist");
        return false;
    }

    String query = "INSERT INTO Flights (user_id, flightNumber, airline, origin, destination, " +
                  "departureTime, arrivalTime, totalSeats, availableSeats, status, " +
                  "economyPrice, businessPrice, firstClassPrice, flightType) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, flight.getId());
        stmt.setString(2, String.valueOf(flight.getFlightNumber()));
        stmt.setString(3, flight.getAirline());
        stmt.setString(4, flight.getOrigin());
        stmt.setString(5, flight.getDestination());
        stmt.setTimestamp(6, Timestamp.valueOf(flight.getDepartureTime()));
        stmt.setTimestamp(7, Timestamp.valueOf(flight.getArrivalTime()));
        stmt.setInt(8, flight.getTotalSeats());
        stmt.setInt(9, flight.getAvailableSeats());
        stmt.setString(10, flight.getFlightStatus());
        stmt.setDouble(11, flight.getEconomyPrice());
        stmt.setDouble(12, flight.getBusinessPrice());
        stmt.setDouble(13, flight.getFirstClassPrice());
        stmt.setString(14, flight.getFlightType());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error adding flight: " + e.getMessage());
        return false;
    }
}
public List<UserInfo> getAllUsers() throws SQLException {
    List<UserInfo> users = new ArrayList<>();
    String query = "SELECT id, Fname, Lname FROM Users";
    
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            UserInfo user = new UserInfo();
            user.setId(rs.getInt("id"));
            
            // Handle potential null values
            String fname = rs.getString("Fname");
            String lname = rs.getString("Lname");
            
            user.setFname(fname != null ? fname : "");
            user.setLname(lname != null ? lname : "");
            
            users.add(user);
        }
    }
    return users;
}
    public List<Flight> getAllFlights() throws SQLException {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM Flights";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Flight flight;
                if (rs.getString("flightType").equalsIgnoreCase("DOMESTIC")) {
                    flight = new DomesticFlight();
                } else {
                    flight = new InternationalFlight();
                }
                
                flight.setId(rs.getInt("id"));
                flight.setFlightNumber(rs.getInt("flightNumber"));
                flight.setAirline(rs.getString("airline"));
                flight.setOrigin(rs.getString("origin"));
                flight.setDestination(rs.getString("destination"));
                flight.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime().toString());
                flight.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime().toString());
                flight.setTotalSeats(rs.getInt("totalSeats"));
                flight.setAvailableSeats(rs.getInt("availableSeats"));
                flight.setFlightStatus(rs.getString("status"));
                flight.setEconomyPrice(rs.getDouble("economyPrice"));
                flight.setBusinessPrice(rs.getDouble("businessPrice"));
                flight.setFirstClassPrice(rs.getDouble("firstClassPrice"));
                flight.setFlightType(rs.getString("flightType"));
                
                flights.add(flight);
            }
        }
        return flights;
    }

    public Flight getFlightById(int flightId) throws SQLException {
        String query = "SELECT * FROM Flights WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Flight flight;
                    if (rs.getString("flightType").equalsIgnoreCase("DOMESTIC")) {
                        flight = new DomesticFlight();
                    } else {
                        flight = new InternationalFlight();
                    }
                    
                    flight.setId(rs.getInt("id"));
                    flight.setFlightNumber(rs.getInt("flightNumber"));
                    flight.setAirline(rs.getString("airline"));
                    flight.setOrigin(rs.getString("origin"));
                    flight.setDestination(rs.getString("destination"));
                    flight.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime().toString());
                    flight.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime().toString());
                    flight.setTotalSeats(rs.getInt("totalSeats"));
                    flight.setAvailableSeats(rs.getInt("availableSeats"));
                    flight.setFlightStatus(rs.getString("status"));
                    flight.setEconomyPrice(rs.getDouble("economyPrice"));
                    flight.setBusinessPrice(rs.getDouble("businessPrice"));
                    flight.setFirstClassPrice(rs.getDouble("firstClassPrice"));
                    flight.setFlightType(rs.getString("flightType"));
                    
                    return flight;
                }
            }
        }
        return null;
    }

    public boolean updateFlight(Flight flight) throws SQLException {
    String query = "UPDATE Flights SET flightNumber = ?, airline = ?, origin = ?, destination = ?, " +
                  "departureTime = ?, arrivalTime = ?, totalSeats = ?, availableSeats = ?, " +
                  "status = ?, economyPrice = ?, businessPrice = ?, firstClassPrice = ?, " +
                  "flightType = ? WHERE id = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        // Ensure timestamps are properly formatted
        Timestamp departureTimestamp = parseTimestamp(flight.getDepartureTime());
        Timestamp arrivalTimestamp = parseTimestamp(flight.getArrivalTime());
        
        if (departureTimestamp == null || arrivalTimestamp == null) {
            return false;
        }

        stmt.setString(1, String.valueOf(flight.getFlightNumber()));
        stmt.setString(2, flight.getAirline());
        stmt.setString(3, flight.getOrigin());
        stmt.setString(4, flight.getDestination());
        stmt.setTimestamp(5, departureTimestamp);
        stmt.setTimestamp(6, arrivalTimestamp);
        stmt.setInt(7, flight.getTotalSeats());
        stmt.setInt(8, flight.getAvailableSeats());
        stmt.setString(9, flight.getFlightStatus());
        stmt.setDouble(10, flight.getEconomyPrice());
        stmt.setDouble(11, flight.getBusinessPrice());
        stmt.setDouble(12, flight.getFirstClassPrice());
        stmt.setString(13, flight.getFlightType());
        stmt.setInt(14, flight.getId());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error updating flight: " + e.getMessage());
        return false;
    }
}

// Helper method to parse timestamps
private Timestamp parseTimestamp(String timestampStr) {
    try {
        if (timestampStr == null || timestampStr.isEmpty()) {
            return null;
        }
        // Ensure the timestamp has seconds
        if (!timestampStr.contains(":")) {
            timestampStr += " 00:00:00";
        } else if (timestampStr.matches(".*:\\d{2}$")) {
            timestampStr += ":00"; // Add seconds if missing
        }
        return Timestamp.valueOf(timestampStr);
    } catch (IllegalArgumentException e) {
        System.err.println("Invalid timestamp format: " + timestampStr);
        return null;
    }
}


public boolean deleteFlight(int flightId) throws SQLException {
    String query = "DELETE FROM Flights WHERE id = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, flightId);
        return stmt.executeUpdate() > 0;
    }
}

    public void deleteFlight() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    displayAvailableFlights();
    
    System.out.print("\nEnter Flight ID to delete: ");
    int flightId = scanner.nextInt();
    scanner.nextLine(); // consume newline
    
    // Check if flight exists first
    if (!flightExists(flightId)) {
        System.out.println("Flight with ID " + flightId + " does not exist.");
        return;
    }
    
    System.out.print("Are you sure you want to delete flight " + flightId + "? This will also delete all related bookings. (Y/N): ");
    String confirm = scanner.nextLine();
    
    if (confirm.equalsIgnoreCase("Y")) {
        try {
            // Start transaction
            connection.setAutoCommit(false);
            
            // 1. First delete all related bookings
            String deleteBookingsSql = "DELETE FROM bookings WHERE flightId = ?";
            try (PreparedStatement deleteBookingsStmt = connection.prepareStatement(deleteBookingsSql)) {
                deleteBookingsStmt.setInt(1, flightId);
                int bookingsDeleted = deleteBookingsStmt.executeUpdate();
                System.out.println("Deleted " + bookingsDeleted + " related bookings.");
            }
            
            // 2. Now delete the flight
            String deleteFlightSql = "DELETE FROM flights WHERE id = ?";
            try (PreparedStatement deleteFlightStmt = connection.prepareStatement(deleteFlightSql)) {
                deleteFlightStmt.setInt(1, flightId);
                int flightsDeleted = deleteFlightStmt.executeUpdate();
                
                if (flightsDeleted > 0) {
                    connection.commit();
                    System.out.println("Flight deleted successfully.");
                } else {
                    connection.rollback();
                    System.out.println("Failed to delete flight.");
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Error during deletion: " + e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    } else {
        System.out.println("Deletion cancelled.");
    }
}

public UserInfo getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserInfo user = new UserInfo();
                    user.setFname(rs.getString("Fname"));
                    user.setMname(rs.getString("Mname"));
                    user.setLname(rs.getString("Lname"));
                    user.setNationality(rs.getString("nationality"));
                    user.setNationalID(rs.getLong("nationalID"));
                    user.setYearOfBirth(rs.getInt("yearOfBirth"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getLong("PhoneNumber"));
                    user.setJob(rs.getString("job"));
                    user.setUsername(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    
                    return user;
                }
            }
        }
        return null;
    }
public boolean createBooking(Booking booking) throws SQLException {
    String query = "INSERT INTO Bookings (bookingReference, customerId, flightId, passengers, " +
                  "seatSelections, status, payment_status, totalPrice) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, booking.getBookingReference());
        stmt.setInt(2, booking.getCustomerId());
        stmt.setInt(3, booking.getFlightId());
        stmt.setString(4, booking.getPassengersAsString());
        stmt.setString(5, booking.getSeatSelectionsAsString());
        stmt.setString(6, booking.getStatus().toString());
        stmt.setString(7, booking.getPaymentStatus().toString());
        stmt.setDouble(8, booking.getTotalPrice());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error creating booking: " + e.getMessage());
        return false;
    }
}
public boolean userExists(int userId) throws SQLException {
    String sql = "SELECT 1 FROM users WHERE id = ? LIMIT 1";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Returns true if a record was found
        }
    }
}
public void displayAvailableFlights() {
    String sql = "SELECT id, flightNumber, airline, origin, destination, " +
               "departureTime, arrivalTime, availableSeats, status, " +
               "economyPrice, businessPrice, firstClassPrice " +
               "FROM flights ORDER BY departureTime ASC";  // Sort by departure time

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        System.out.println("\n=== ALL FLIGHTS ===");
        System.out.printf("%-5s %-10s %-15s %-10s %-15s %-15s %-20s %-20s %-10s %-10s %-10s %-10s%n",
                "ID", "Flight #", "Airline", "From", "To", "Departure", "Arrival", 
                "Available", "Status", "Economy", "Business", "First");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String flightNumber = rs.getString("flightNumber");
            String airline = rs.getString("airline");
            String origin = rs.getString("origin");
            String destination = rs.getString("destination");
            
            // Handle potential null timestamps
            Timestamp depTime = rs.getTimestamp("departureTime");
            Timestamp arrTime = rs.getTimestamp("arrivalTime");
            
            LocalDateTime departureTime = depTime != null ? depTime.toLocalDateTime() : null;
            LocalDateTime arrivalTime = arrTime != null ? arrTime.toLocalDateTime() : null;
            
            int availableSeats = rs.getInt("availableSeats");
            String status = rs.getString("status");
            double economyPrice = rs.getDouble("economyPrice");
            double businessPrice = rs.getDouble("businessPrice");
            double firstClassPrice = rs.getDouble("firstClassPrice");

            // Format display values
            String formattedDeparture = departureTime != null ? 
                departureTime.format(formatter) : "N/A";
            String formattedArrival = arrivalTime != null ? 
                arrivalTime.format(formatter) : "N/A";
            String seatsStatus = availableSeats > 0 ? 
                String.valueOf(availableSeats) : "FULL";

            System.out.printf("%-5d %-10s %-15s %-10s %-15s %-15s %-20s %-10s %-10s $%-9.2f $%-9.2f $%-9.2f%n",
                    id, flightNumber, airline, origin, destination,
                    formattedDeparture, formattedArrival, seatsStatus,
                    status, economyPrice, businessPrice, firstClassPrice);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching flights: " + e.getMessage());
    }
}
        public boolean flightExists(int flightId) {
        String sql = "SELECT COUNT(*) FROM flights WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, flightId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count > 0
            }
        } catch (SQLException e) {
            System.err.println("Error checking flight existence: " + e.getMessage());
        }
        return false;
    }
    
public boolean cancelBooking(int bookingId, int userId) throws Exception {
    // Check if booking exists and belongs to the user
    String checkSql = "SELECT id FROM bookings WHERE id = ? AND customerId = ?";
    String updateSql = "UPDATE bookings SET status = 'CANCELLED', payment_status = 'REFUNDED' WHERE id = ?";
    String updateSeatsSql = "UPDATE flights f JOIN bookings b ON f.id = b.flightId " +
                          "SET f.availableSeats = f.availableSeats + " +
                          "(SELECT JSON_LENGTH(passengers) FROM bookings WHERE id = ?) " +
                          "WHERE b.id = ?";

    try {
        // Start transaction
        connection.setAutoCommit(false);
        
        // 1. Verify booking belongs to user
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bookingId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Booking not found or doesn't belong to you");
                return false;
            }
        }

        // 2. Update booking status to CANCELLED and mark for refund
        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            updateStmt.setInt(1, bookingId);
            int rowsUpdated = updateStmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                connection.rollback();
                return false;
            }
        }

        // 3. Update available seats in the flight (using JSON_LENGTH for passenger count)
        try (PreparedStatement updateSeatsStmt = connection.prepareStatement(updateSeatsSql)) {
            updateSeatsStmt.setInt(1, bookingId);
            updateSeatsStmt.setInt(2, bookingId);
            updateSeatsStmt.executeUpdate();
        }

        // Commit transaction
        connection.commit();
        System.out.println("Booking #" + bookingId + " cancelled successfully. Payment marked for refund.");
        return true;
        
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            System.err.println("Error during rollback: " + ex.getMessage());
        }
        throw new Exception("Error cancelling booking: " + e.getMessage());
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error resetting auto-commit: " + e.getMessage());
        }
    }
}



public Booking getBookingByReference(String reference) throws SQLException {
    String query = "SELECT * FROM Bookings WHERE bookingReference = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, reference);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingReference(rs.getString("bookingReference"));
                booking.setCustomerId(rs.getInt("customerId"));
                booking.setFlightId(rs.getInt("flightId"));
                
                
                String seatsStr = rs.getString("seatSelections");
                if (seatsStr != null && !seatsStr.isEmpty()) {
                    booking.setSeatSelections(Arrays.asList(seatsStr.split(",")));
                }
                
                // Handle enums
                booking.setStatus(Booking.BookingStatus.valueOf(
                    rs.getString("status") != null ? 
                    rs.getString("status").toUpperCase() : 
                    "PENDING"
                ));
                
                booking.setPaymentStatus(Booking.PaymentStatus.valueOf(
                    rs.getString("payment_status") != null ? 
                    rs.getString("payment_status").toUpperCase() : 
                    "PENDING"
                ));
                
                booking.setTotalPrice(rs.getDouble("totalPrice"));
                
                return booking;
            }
        }
    }
    return null;
}
    public boolean updateBookingStatus(String reference, Booking.BookingStatus confirmed) throws SQLException {
        String query = "UPDATE Bookings SET status = ? WHERE bookingReference = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, confirmed.toString());
            stmt.setString(2, reference);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            return false;
        }
    }

    // Passenger Operations
    public boolean addPassenger(Passenger passenger) throws SQLException {
        String query = "INSERT INTO Passengers (bookingReference, name, passportNumber, " +
                      "dateOfBirth, specialRequests) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, passenger.getBookingReference());
            stmt.setString(2, passenger.getName());
            stmt.setLong(3, passenger.getPassportNumber());
            stmt.setDate(4, Date.valueOf(passenger.getDateOfBirth()));
            stmt.setLong(5, passenger.getSpecialRequests());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding passenger: " + e.getMessage());
            return false;
        }
    }

    public List<Passenger> getPassengersByBooking(String bookingReference) throws SQLException {
        List<Passenger> passengers = new ArrayList<>();
        String query = "SELECT * FROM Passengers WHERE bookingReference = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bookingReference);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Passenger passenger = new Passenger();
                    passenger.setPassengerId(rs.getInt("passengerId"));
                    passenger.setBookingReference(rs.getString("bookingReference"));
                    passenger.setName(rs.getString("name"));
                    passenger.setPassportNumber(rs.getLong("passportNumber"));
                    passenger.setDateOfBirth(rs.getDate("dateOfBirth").toString());
                    passenger.setSpecialRequests(rs.getLong("specialRequests"));
                    
                    passengers.add(passenger);
                }
            }
        }
        return passengers;
    }

    // Payment Operations
    public boolean recordPayment(Payment payment) throws SQLException {
        String query = "INSERT INTO Payments (bookingReference, amount, currency, method, " +
                      "status, transactionDate) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, payment.getBookingReference());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getMethod().toString());
            stmt.setString(5, payment.getStatus().toString());
            stmt.setTimestamp(6, Timestamp.valueOf(payment.getTransactionDate()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error recording payment: " + e.getMessage());
            return false;
        }
    }

public Payment getPaymentByBooking(String bookingReference) throws SQLException {
    String query = "SELECT * FROM Payments WHERE bookingReference = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, bookingReference);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("paymentId"));
                payment.setBookingReference(rs.getString("bookingReference"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setCurrency(rs.getString("currency"));
                
                // Safe enum conversion
                payment.setMethod(convertToPaymentMethod(rs.getString("method")));
                payment.setStatus(convertToPaymentStatus(rs.getString("status")));
                
                // Handle possible null timestamp
                java.sql.Timestamp timestamp = rs.getTimestamp("transactionDate");
                payment.setTransactionDate(timestamp != null ? timestamp.toLocalDateTime() : null);
                
                return payment;
            }
        }
    }
    return null;
}

private Payment.PaymentMethod convertToPaymentMethod(String method) {
    if (method == null) return Payment.PaymentMethod.CREDIT_CARD; // Default
    try {
        return Payment.PaymentMethod.valueOf(method.toUpperCase());
    } catch (IllegalArgumentException e) {
        return Payment.PaymentMethod.CREDIT_CARD; // Default if invalid
    }
}

private Payment.PaymentStatus convertToPaymentStatus(String status) {
    if (status == null) return Payment.PaymentStatus.PENDING; // Default
    try {
        return Payment.PaymentStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
        return Payment.PaymentStatus.PENDING; // Default if invalid
    }
}
    public enum BookingStatus {
    PENDING,    // Booking is being processed
    CONFIRMED,  // Booking is confirmed
    CANCELLED,  // Booking was cancelled
    COMPLETED   // Booking was fulfilled (flight completed)
}

public enum PaymentStatus {
    PENDING,    // Payment is being processed
    PAID,       // Payment was successful
    FAILED,     // Payment failed
    REFUNDED,   // Payment was refunded
    PARTIALLY_REFUNDED // Partial refund was issued
}

public void updateFlight() throws Exception {
    Scanner scanner = new Scanner(System.in);
    displayAvailableFlights();
    
    System.out.print("\nEnter Flight ID to update: ");
    int flightId = scanner.nextInt();
    scanner.nextLine(); // consume newline
    
    Flight flight = getFlightById(flightId);
    if (flight == null) {
        System.out.println("Flight not found.");
        return;
    }
    
    System.out.println("\nLeave blank to keep current value");
    
    // Flight Number
    System.out.print("Flight Number (" + flight.getFlightNumber() + "): ");
    String flightNumber = scanner.nextLine();
    if (!flightNumber.isEmpty()) flight.setFlightNumber(Integer.parseInt(flightNumber));
    
    // Airline
    System.out.print("Airline (" + flight.getAirline() + "): ");
    String airline = scanner.nextLine();
    if (!airline.isEmpty()) flight.setAirline(airline);
    
    // Origin
    System.out.print("Origin (" + flight.getOrigin() + "): ");
    String origin = scanner.nextLine();
    if (!origin.isEmpty()) flight.setOrigin(origin);
    
    // Destination
    System.out.print("Destination (" + flight.getDestination() + "): ");
    String destination = scanner.nextLine();
    if (!destination.isEmpty()) flight.setDestination(destination);
    
    // Departure Time
    System.out.print("Departure Date (YYYY-MM-DD) (" + flight.getDepartureTime().substring(0, 10) + "): ");
    String departureDate = scanner.nextLine();
    System.out.print("Departure Time (HH:MM) (" + flight.getDepartureTime().substring(11, 16) + "): ");
    String departureTime = scanner.nextLine();
    if (!departureDate.isEmpty() || !departureTime.isEmpty()) {
        String newDeparture = (!departureDate.isEmpty() ? departureDate : flight.getDepartureTime().substring(0, 10)) + " " +
                            (!departureTime.isEmpty() ? departureTime : flight.getDepartureTime().substring(11, 16)) + ":00";
        flight.setDepartureTime(newDeparture);
    }
    
    // Arrival Time
    System.out.print("Arrival Date (YYYY-MM-DD) (" + flight.getArrivalTime().substring(0, 10) + "): ");
    String arrivalDate = scanner.nextLine();
    System.out.print("Arrival Time (HH:MM) (" + flight.getArrivalTime().substring(11, 16) + "): ");
    String arrivalTime = scanner.nextLine();
    if (!arrivalDate.isEmpty() || !arrivalTime.isEmpty()) {
        String newArrival = (!arrivalDate.isEmpty() ? arrivalDate : flight.getArrivalTime().substring(0, 10)) + " " +
                          (!arrivalTime.isEmpty() ? arrivalTime : flight.getArrivalTime().substring(11, 16)) + ":00";
        flight.setArrivalTime(newArrival);
    }
    
    // Total Seats
    System.out.print("Total Seats (" + flight.getTotalSeats() + "): ");
    String totalSeatsStr = scanner.nextLine();
    if (!totalSeatsStr.isEmpty()) {
        int totalSeats = Integer.parseInt(totalSeatsStr);
        flight.setTotalSeats(totalSeats);
        // Ensure available seats don't exceed total seats
        if (flight.getAvailableSeats() > totalSeats) {
            flight.setAvailableSeats(totalSeats);
        }
    }
    
    // Prices
    System.out.print("Economy Price ($" + flight.getEconomyPrice() + "): ");
    String economyPriceStr = scanner.nextLine();
    if (!economyPriceStr.isEmpty()) flight.setEconomyPrice(Double.parseDouble(economyPriceStr));
    
    System.out.print("Business Price ($" + flight.getBusinessPrice() + "): ");
    String businessPriceStr = scanner.nextLine();
    if (!businessPriceStr.isEmpty()) flight.setBusinessPrice(Double.parseDouble(businessPriceStr));
    
    System.out.print("First Class Price ($" + flight.getFirstClassPrice() + "): ");
    String firstClassPriceStr = scanner.nextLine();
    if (!firstClassPriceStr.isEmpty()) flight.setFirstClassPrice(Double.parseDouble(firstClassPriceStr));
    
    // Status
    System.out.print("Status (" + flight.getFlightStatus() + "): ");
    String status = scanner.nextLine();
    if (!status.isEmpty()) flight.setFlightStatus(status);
    
    // Flight Type
    System.out.print("Flight Type (" + flight.getFlightType() + "): ");
    String flightType = scanner.nextLine();
    if (!flightType.isEmpty()) flight.setFlightType(flightType);
    
    if (updateFlight(flight)) {
        System.out.println("Flight updated successfully!");
    } else {
        System.out.println("Failed to update flight. Please check the timestamp formats.");
    }
}


}