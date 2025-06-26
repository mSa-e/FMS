// import java.util.*;
// import java.sql.*;

// public class AgentDashboard {
//     private DAO2 dao;
//     private TravelAgentInfo agent;
//     private Scanner scanner;

//     public AgentDashboard(TravelAgentInfo agent) throws Exception {
//         this.dao = new DAO2();
//         this.agent = agent;
//         this.scanner = new Scanner(System.in);
//     }

//     // 2. Manage Flights Information
//     public void manageFlights() {
//         try {
//             System.out.println("\n=== MANAGE FLIGHTS ===");
//             System.out.println("1. View All Flights");
//             System.out.println("2. Add New Flight");
//             System.out.println("3. Update Flight");
//             System.out.println("4. Delete Flight");
//             System.out.print("Choice: ");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); // consume newline
            
//             switch (choice) {
//                 case 1 -> viewAllFlights();
//                 case 2 -> addNewFlight();
//                 case 3 -> updateFlight();
//                 case 4 -> deleteFlight();
//                 default -> System.out.println("Invalid choice.");
//             }
//         } catch (Exception e) {
//             System.err.println("Error managing flights: " + e.getMessage());
//         }
//     }

//     private void viewAllFlights() throws SQLException {
//         List<Flight> flights = dao.getAllFlights();
        
//         System.out.println("\n=== ALL FLIGHTS ===");
//         System.out.printf("%-4s %-12s %-15s %-10s %-10s %-20s %-20s %-8s %-8s %-10s\n",
//             "ID", "Flight No", "Airline", "Origin", "Dest", "Departure", "Arrival", "Total", "Avail", "Status");
        
//         for (Flight flight : flights) {
//             System.out.printf("%-4d %-12s %-15s %-10s %-10s %-20s %-20s %-8d %-8d %-10s\n",
//                 flight.getId(),
//                 flight.getFlightNumber(),
//                 flight.getAirline(),
//                 flight.getOrigin(),
//                 flight.getDestination(),
//                 flight.getDepartureTime(),
//                 flight.getArrivalTime(),
//                 flight.getTotalSeats(),
//                 flight.getAvailableSeats(),
//                 flight.getStatus());
//         }
//     }



//     // 3. Create Booking For Customer
//     public void createBookingForCustomer() {
//         try {
//             System.out.println("\n=== CREATE BOOKING FOR CUSTOMER ===");
            
//             System.out.print("Enter customer ID: ");
//             int customerId = scanner.nextInt();
//             scanner.nextLine(); // consume newline
            
//             // Verify customer exists
//             UserInfo customer = dao.getUserById(customerId);
//             if (customer == null) {
//                 System.out.println("Customer not found.");
//                 return;
//             }
            
//             // Rest of booking creation similar to user's createBooking()
//             // but with agent privileges (can override some checks)
            
//         } catch (Exception e) {
//             System.err.println("Error creating booking: " + e.getMessage());
//         }
//     }

//     // 4. Assist customers with bookings
//     public void assistWithBookings() {
//         try {
//             System.out.println("\n=== ASSIST WITH BOOKINGS ===");
//             System.out.println("1. View All Bookings");
//             System.out.println("2. Modify Booking");
//             System.out.println("3. Cancel Booking");
//             System.out.print("Choice: ");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); // consume newline
            
//             switch (choice) {
//                 case 1 -> viewAllBookings();
//                 case 2 -> modifyBooking();
//                 case 3 -> cancelBookingForCustomer();
//                 default -> System.out.println("Invalid choice.");
//             }
//         } catch (Exception e) {
//             System.err.println("Error assisting with bookings: " + e.getMessage());
//         }
//     }

//     private void viewAllBookings() throws SQLException {
//         List<Booking> bookings = dao.getAllBookings();
        
//         System.out.println("\n=== ALL BOOKINGS ===");
//         System.out.printf("%-15s %-10s %-12s %-10s %-15s %-10s %-10s\n",
//             "Booking Ref", "Customer", "Flight", "Passengers", "Status", "Payment", "Total");
        
//         for (Booking booking : bookings) {
//             System.out.printf("%-15s %-10d %-12s %-10s %-15s %-10s $%-10.2f\n",
//                 booking.getBookingReference(),
//                 booking.getCustomerId(),
//                 booking.getFlightId(),
//                 booking.getPassengers(),
//                 booking.getStatus(),
//                 booking.getPaymentStatus(),
//                 booking.getTotalPrice());
//         }
//     }

//     private void modifyBooking() throws Exception {
//         viewAllBookings();
        
//         System.out.print("\nEnter booking reference to modify: ");
//         String bookingRef = scanner.nextLine();
        
//         Booking booking = dao.getBookingByReference(bookingRef);
//         if (booking == null) {
//             System.out.println("Booking not found.");
//             return;
//         }
        
//         System.out.println("Leave blank to keep current value");
        
//         System.out.print("New Flight ID (" + booking.getFlightId() + "): ");
//         String flightIdStr = scanner.nextLine();
//         if (!flightIdStr.isEmpty()) {
//             booking.setFlightId(Integer.parseInt(flightIdStr));
//         }
        
//         System.out.print("New Status (" + booking.getStatus() + "): ");
//         String status = scanner.nextLine();
//         if (!status.isEmpty()) booking.setStatus(status.toUpperCase());
        
//         System.out.print("New Payment Status (" + booking.getPaymentStatus() + "): ");
//         String paymentStatus = scanner.nextLine();
//         if (!paymentStatus.isEmpty()) booking.setPaymentStatus(paymentStatus.toUpperCase());
        
//         if (dao.updateBooking(booking)) {
//             System.out.println("Booking updated successfully!");
//         } else {
//             System.out.println("Failed to update booking.");
//         }
//     }

//     private void cancelBookingForCustomer() throws Exception {
//         viewAllBookings();
        
//         System.out.print("\nEnter booking reference to cancel: ");
//         String bookingRef = scanner.nextLine();
        
//         Booking booking = dao.getBookingByReference(bookingRef);
//         if (booking == null) {
//             System.out.println("Booking not found.");
//             return;
//         }
        
//         System.out.print("Are you sure you want to cancel this booking? (Y/N): ");
//         String confirm = scanner.nextLine();
        
//         if (confirm.equalsIgnoreCase("Y")) {
//             booking.setStatus("CANCELLED");
//             if (dao.updateBooking(booking)) {
//                 // Release seats back to flight
//                 Flight flight = dao.getFlightById(booking.getFlightId());
//                 flight.setAvailableSeats(flight.getAvailableSeats() + 
//                     booking.getPassengers().split(",").length);
//                 dao.updateFlight(flight);
                
//                 System.out.println("Booking cancelled successfully.");
//             } else {
//                 System.out.println("Failed to cancel booking.");
//             }
//         }
//     }

//     // 5. Generate Reports
//     public void generateReports() {
//         try {
//             System.out.println("\n=== GENERATE REPORTS ===");
//             System.out.println("1. Flight Occupancy Report");
//             System.out.println("2. Revenue Report");
//             System.out.println("3. Customer Booking History");
//             System.out.print("Choice: ");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); // consume newline
            
//             switch (choice) {
//                 case 1 -> generateFlightOccupancyReport();
//                 case 2 -> generateRevenueReport();
//                 case 3 -> generateCustomerBookingReport();
//                 default -> System.out.println("Invalid choice.");
//             }
//         } catch (Exception e) {
//             System.err.println("Error generating reports: " + e.getMessage());
//         }
//     }

//     private void generateFlightOccupancyReport() throws SQLException {
//         List<Flight> flights = dao.getAllFlights();
        
//         System.out.println("\n=== FLIGHT OCCUPANCY REPORT ===");
//         System.out.printf("%-12s %-15s %-10s %-10s %-8s %-8s %-8s %-10s\n",
//             "Flight No", "Airline", "Origin", "Dest", "Total", "Booked", "Avail", "Occupancy");
        
//         for (Flight flight : flights) {
//             int booked = flight.getTotalSeats() - flight.getAvailableSeats();
//             double occupancy = (double) booked / flight.getTotalSeats() * 100;
            
//             System.out.printf("%-12s %-15s %-10s %-10s %-8d %-8d %-8d %-10.2f%%\n",
//                 flight.getFlightNumber(),
//                 flight.getAirline(),
//                 flight.getOrigin(),
//                 flight.getDestination(),
//                 flight.getTotalSeats(),
//                 booked,
//                 flight.getAvailableSeats(),
//                 occupancy);
//         }
//     }

//     private void generateRevenueReport() throws SQLException {
//         List<Booking> bookings = dao.getAllBookings();
//         double totalRevenue = 0;
        
//         System.out.println("\n=== REVENUE REPORT ===");
//         System.out.printf("%-15s %-10s %-12s %-10s %-10s\n",
//             "Booking Ref", "Customer", "Flight", "Status", "Amount");
        
//         for (Booking booking : bookings) {
//             if (booking.getPaymentStatus().equalsIgnoreCase("PAID")) {
//                 System.out.printf("%-15s %-10d %-12s %-10s $%-10.2f\n",
//                     booking.getBookingReference(),
//                     booking.getCustomerId(),
//                     booking.getFlightId(),
//                     booking.getStatus(),
//                     booking.getTotalPrice());
//                 totalRevenue += booking.getTotalPrice();
//             }
//         }
        
//         System.out.println("\nTOTAL REVENUE: $" + totalRevenue);
//     }

//     private void generateCustomerBookingReport() throws SQLException {
//         System.out.print("Enter customer ID: ");
//         int customerId = scanner.nextInt();
//         scanner.nextLine(); // consume newline
        
//         UserInfo customer = dao.getUserById(customerId);
//         if (customer == null) {
//             System.out.println("Customer not found.");
//             return;
//         }
        
//         List<Booking> bookings = dao.getBookingsByCustomer(customerId);
        
//         System.out.println("\n=== BOOKING HISTORY FOR: " + customer.getUsername() + " ===");
//         System.out.printf("%-15s %-12s %-10s %-15s %-10s %-10s\n",
//             "Booking Ref", "Flight", "Passengers", "Status", "Payment", "Amount");
        
//         for (Booking booking : bookings) {
//             System.out.printf("%-15s %-12s %-10s %-15s %-10s $%-10.2f\n",
//                 booking.getBookingReference(),
//                 booking.getFlightId(),
//                 booking.getPassengers(),
//                 booking.getStatus(),
//                 booking.getPaymentStatus(),
//                 booking.getTotalPrice());
//         }
//     }
//        public List<Flight> searchFlights(String origin, String destination, String date) throws SQLException {
//         List<Flight> flights = new ArrayList<>();
//         String query = "SELECT * FROM Flights WHERE origin LIKE ? AND destination LIKE ? " +
//                      "AND DATE(departureTime) = ? AND availableSeats > 0";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, "%" + origin + "%");
//             stmt.setString(2, "%" + destination + "%");
//             stmt.setString(3, date);
            
//             try (ResultSet rs = stmt.executeQuery()) {
//                 while (rs.next()) {
//                     flights.add(mapFlightFromResultSet(rs));
//                 }
//             }
//         }
//         return flights;
//     }

//     private Flight mapFlightFromResultSet(ResultSet rs) throws SQLException {
//         Flight flight = new Flight();
//         flight.setId(rs.getInt("id"));
//         flight.setFlightNumber(rs.getString("flightNumber"));
//         flight.setAirline(rs.getString("airline"));
//         flight.setOrigin(rs.getString("origin"));
//         flight.setDestination(rs.getString("destination"));
//         flight.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime().toString());
//         flight.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime().toString());
//         flight.setTotalSeats(rs.getInt("totalSeats"));
//         flight.setAvailableSeats(rs.getInt("availableSeats"));
//         flight.setStatus(rs.getString("status"));
//         flight.setEconomyPrice(rs.getDouble("economyPrice"));
//         flight.setBusinessPrice(rs.getDouble("businessPrice"));
//         flight.setFirstClassPrice(rs.getDouble("firstClassPrice"));
//         return flight;
//     }

//     // ==================== BOOKING OPERATIONS ====================
    
//     public boolean createBooking(Booking booking) throws SQLException {
//         String query = "INSERT INTO Bookings (bookingReference, customerId, flightId, " +
//                       "passengers, seatSelections, status, paymentStatus, totalPrice) " +
//                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, booking.getBookingReference());
//             stmt.setInt(2, booking.getCustomerId());
//             stmt.setInt(3, booking.getFlightId());
//             stmt.setString(4, booking.getPassengers());
//             stmt.setString(5, booking.getSeatSelections());
//             stmt.setString(6, booking.getStatus());
//             stmt.setString(7, booking.getPaymentStatus());
//             stmt.setDouble(8, booking.getTotalPrice());
            
//             return stmt.executeUpdate() > 0;
//         }
//     }

//     public List<Booking> getBookingsByCustomer(int customerId) throws SQLException {
//         List<Booking> bookings = new ArrayList<>();
//         String query = "SELECT * FROM Bookings WHERE customerId = ?";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setInt(1, customerId);
            
//             try (ResultSet rs = stmt.executeQuery()) {
//                 while (rs.next()) {
//                     bookings.add(mapBookingFromResultSet(rs));
//                 }
//             }
//         }
//         return bookings;
//     }

//     private Booking mapBookingFromResultSet(ResultSet rs) throws SQLException {
//         Booking booking = new Booking();
//         booking.setBookingReference(rs.getString("bookingReference"));
//         booking.setCustomerId(rs.getInt("customerId"));
//         booking.setFlightId(rs.getInt("flightId"));
//         booking.setPassengers(rs.getString("passengers"));
//         booking.setSeatSelections(rs.getString("seatSelections"));
//         booking.setStatus(rs.getString("status"));
//         booking.setPaymentStatus(rs.getString("paymentStatus"));
//         booking.setTotalPrice(rs.getDouble("totalPrice"));
//         return booking;
//     }

//     // ==================== PASSENGER OPERATIONS ====================
    
//     public boolean addPassenger(Passenger passenger) throws SQLException {
//         String query = "INSERT INTO Passengers (bookingReference, name, passportNumber, " +
//                       "dateOfBirth, specialRequests) VALUES (?, ?, ?, ?, ?)";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, passenger.getBookingReference());
//             stmt.setString(2, passenger.getName());
//             stmt.setLong(3, passenger.getPassportNumber());
//             stmt.setString(4, passenger.getDateOfBirth());
//             stmt.setString(5, passenger.getSpecialRequests());
            
//             return stmt.executeUpdate() > 0;
//         }
//     }

//     public List<Passenger> getPassengersByBooking(String bookingReference) throws SQLException {
//         List<Passenger> passengers = new ArrayList<>();
//         String query = "SELECT * FROM Passengers WHERE bookingReference = ?";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, bookingReference);
            
//             try (ResultSet rs = stmt.executeQuery()) {
//                 while (rs.next()) {
//                     passengers.add(mapPassengerFromResultSet(rs));
//                 }
//             }
//         }
//         return passengers;
//     }

//     private Passenger mapPassengerFromResultSet(ResultSet rs) throws SQLException {
//         Passenger passenger = new Passenger();
//         passenger.setPassengerId(rs.getInt("passengerId"));
//         passenger.setBookingReference(rs.getString("bookingReference"));
//         passenger.setName(rs.getString("name"));
//         passenger.setPassportNumber(rs.getLong("passportNumber"));
//         passenger.setDateOfBirth(rs.getString("dateOfBirth"));
//         passenger.setSpecialRequests(rs.getString("specialRequests"));
//         return passenger;
//     }

//     // ==================== PAYMENT OPERATIONS ====================
    
//     public boolean processPayment(Payment payment) throws SQLException {
//         String query = "INSERT INTO Payments (bookingReference, amount, method, status, transactionDate) " +
//                       "VALUES (?, ?, ?, ?, ?)";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, payment.getBookingReference());
//             stmt.setDouble(2, payment.getAmount());
//             stmt.setString(3, payment.getMethod());
//             stmt.setString(4, payment.getStatus());
//             stmt.setTimestamp(5, Timestamp.valueOf(payment.getTransactionDate()));
            
//             return stmt.executeUpdate() > 0;
//         }
//     }

//     public Payment getPaymentByBooking(String bookingReference) throws SQLException {
//         String query = "SELECT * FROM Payments WHERE bookingReference = ?";
        
//         try (PreparedStatement stmt = connection.prepareStatement(query)) {
//             stmt.setString(1, bookingReference);
            
//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     return mapPaymentFromResultSet(rs);
//                 }
//             }
//         }
//         return null;
//     }

//     private Payment mapPaymentFromResultSet(ResultSet rs) throws SQLException {
//         Payment payment = new Payment();
//         payment.setPaymentID(rs.getInt("paymentId"));
//         payment.setBookingReference(rs.getString("bookingReference"));
//         payment.setAmount(rs.getDouble("amount"));
//         payment.setMethod(rs.getString("method"));
//         payment.setStatus(rs.getString("status"));
//         payment.setTransactionDate(rs.getTimestamp("transactionDate").toLocalDateTime());
//         return payment;
//     }
//     public Flight getFlightById(int flightId) throws SQLException {
//     String query = "SELECT * FROM Flights WHERE id = ?";
//     Flight flight = null;
    
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setInt(1, flightId);
        
//         try (ResultSet rs = stmt.executeQuery()) {
//             if (rs.next()) {
//                 flight = new InternationalBusiness();
//                 flight.setId(rs.getInt("id"));
//                 flight.setFlightNumber(rs.getString("flightNumber"));
//                 flight.setAirline(rs.getString("airline"));
//                 flight.setOrigin(rs.getString("origin"));
//                 flight.setDestination(rs.getString("destination"));
//                 flight.setDepartureTime(rs.getString("departureTime"));
//                 flight.setArrivalTime(rs.getString("arrivalTime"));
//                 flight.setTotalSeats(rs.getInt("totalSeats"));
//                 flight.setAvailableSeats(rs.getInt("availableSeats"));
//                 flight.setStatus(rs.getString("status"));
//                 flight.setEconomyPrice(rs.getDouble("economyPrice"));
//                 flight.setBusinessPrice(rs.getDouble("businessPrice"));
//                 flight.setFirstClassPrice(rs.getDouble("firstClassPrice"));
//             }
//         }
//     }
//     return flight;
// }
// public Flight getFlightByNumber(String flightNumber) throws SQLException {
//     String query = "SELECT * FROM Flights WHERE flightNumber = ?";
//     Flight flight = null;
    
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setString(1, flightNumber);
        
//         try (ResultSet rs = stmt.executeQuery()) {
//             if (rs.next()) {
//                 flight = new Flight();
//                 flight.setId(rs.getInt("id"));
//                 flight.setFlightNumber(rs.getString("flightNumber"));
//                 flight.setAirline(rs.getString("airline"));
//                 flight.setOrigin(rs.getString("origin"));
//                 flight.setDestination(rs.getString("destination"));
//                 flight.setDepartureTime(rs.getString("departureTime"));
//                 flight.setArrivalTime(rs.getString("arrivalTime"));
//                 flight.setTotalSeats(rs.getInt("totalSeats"));
//                 flight.setAvailableSeats(rs.getInt("availableSeats"));
//                 flight.setStatus(rs.getString("status"));
//                 flight.setEconomyPrice(rs.getDouble("economyPrice"));
//                 flight.setBusinessPrice(rs.getDouble("businessPrice"));
//                 flight.setFirstClassPrice(rs.getDouble("firstClassPrice"));
//             }
//         }
//     }
//     return flight;
// }
// }