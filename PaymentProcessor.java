import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class PaymentProcessor {
    private final DAO2 dao;
    private final Scanner scanner;

    public PaymentProcessor(DAO2 dao) {
        this.dao = dao;
        this.scanner = new Scanner(System.in);
    }

    public boolean processPayment(String bookingReference) throws Exception {
        // Get booking details
        Booking booking = dao.getBookingByReference(bookingReference);
        if (booking == null) {
            System.out.println("Booking not found!");
            return false;
        }

        // Display payment options
        System.out.println("\n=== PAYMENT OPTIONS ===");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. PayPal");
        System.out.println("4. Bank Transfer");
        System.out.print("Select payment method (1-4): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Payment.PaymentMethod method;
        switch (choice) {
            case 1 -> method = Payment.PaymentMethod.CREDIT_CARD;
            case 2 -> method = Payment.PaymentMethod.DEBIT_CARD;
            case 3 -> method = Payment.PaymentMethod.PAYPAL;
            case 4 -> method = Payment.PaymentMethod.BANK_TRANSFER;
            default -> {
                System.out.println("Invalid payment method selected");
                return false;
            }
        }

        // Simulate payment processing
        System.out.println("\nProcessing payment...");
        
        // Create payment record
        Payment payment = new Payment();
        payment.setBookingReference(bookingReference);
        payment.setAmount(booking.getTotalPrice());
        payment.setCurrency("USD"); // Default currency
        payment.setMethod(method);
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        payment.setTransactionDate(LocalDateTime.now());

        // Save payment to database
        boolean paymentSaved = dao.recordPayment(payment);
        if (!paymentSaved) {
            System.out.println("Failed to record payment");
            return false;
        }

        // Update booking status
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setPaymentStatus(Booking.PaymentStatus.PAID);
        dao.updateBookingStatus(bookingReference, Booking.BookingStatus.CONFIRMED);

        // Generate e-ticket
        generateETicket(booking);

        System.out.println("Payment processed successfully!");
        return true;
    }
    private void generateETicket(Booking booking) throws Exception {
        System.out.println("\n=== E-TICKET ===");
        System.out.println("Booking Reference: " + booking.getBookingReference());
        System.out.println("Status: " + booking.getStatus());
        
        // Get flight details
        Flight flight = dao.getFlightById(booking.getFlightId());
        if (flight != null) {
            System.out.println("\nFlight Details:");
            System.out.println("Flight Number: " + flight.getFlightNumber());
            System.out.println("Airline: " + flight.getAirline());
            System.out.println("From: " + flight.getOrigin());
            System.out.println("To: " + flight.getDestination());
            System.out.println("Departure: " + flight.getDepartureTime());
            System.out.println("Arrival: " + flight.getArrivalTime());
        }
        
        // Get passengers
        System.out.println("\nPassengers:");
        for (Passenger passenger : booking.getPassengers()) {
            System.out.println("- " + passenger.getName() + 
                             " (Passport: " + passenger.getPassportNumber() + ")");
        }
        
        System.out.println("\nTotal Paid: $" + booking.getTotalPrice());
        System.out.println("\nThank you for flying with us!");

            // Now generate text file
    generateETicketFile(booking, flight);
    }

private void generateETicketFile(Booking booking, Flight flight) {
    String fileName = "Ticket_" + booking.getBookingReference() + ".txt";
    
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("=============================================");
        writer.println("               E-TICKET RECEIPT              ");
        writer.println("=============================================");
        writer.println();
        writer.println("BOOKING INFORMATION:");
        writer.println("---------------------------------------------");
        writer.println("Booking Reference: " + booking.getBookingReference());
        writer.println("Booking Status: " + booking.getStatus());
        writer.println("Booking Date: " + LocalDateTime.now());
        writer.println();
        

        if (flight != null) {
            writer.println("FLIGHT INFORMATION:");
            writer.println("----------------------------------------");
            writer.println("Flight Number: " + flight.getFlightNumber());
            writer.println("Airline: " + flight.getAirline());
            writer.println("Route: " + flight.getOrigin() + " to " + flight.getDestination());
            writer.println("Departure: " + flight.getDepartureTime());
            writer.println("Arrival: " + flight.getArrivalTime());
            writer.println();
        }
        
        writer.println("PASSENGER(S) INFORMATION:");
        writer.println("----------------------------------------");
        for (Passenger passenger : booking.getPassengers()) {
            writer.println("Name: " + passenger.getName());
            writer.println("Passport: " + passenger.getPassportNumber());
            writer.println("Seat: " + getSeatForPassenger(booking, passenger)); // Need to implement
            writer.println();
            writer.println("----------------------------------------");
        }
        
        writer.println("PAYMENT INFORMATION:");
        writer.println("----------------------------------------");
        writer.println("Total Amount: $" + booking.getTotalPrice());
        writer.println("Payment Status: " + booking.getPaymentStatus());
        writer.println();
        
        writer.println("ADDITIONAL INFORMATION:");
        writer.println("----------------------------------------");
        writer.println("• Please arrive at least 2 hours before departure");
        writer.println("• Bring this ticket and your passport for check-in");
        writer.println("• Baggage allowance: 1 cabin bag + 1 checked bag (23kg)");
        writer.println("- For any changes, contact customer service");
        writer.println();
        
                
        writer.println("=============================================");
        writer.println("         Thank you for choosing us!          ");
        writer.println("=============================================");
        

        System.out.println("\nE-ticket generated successfully: " + fileName);
    } catch (IOException e) {
        System.err.println("Error generating ticket file: " + e.getMessage());
    }
}

    void generateDailyFlightReport() throws Exception {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        String fileName = "FlightReport_" + date + ".txt";
        List<Flight> flights = dao.getAllFlights();
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== DAILY FLIGHT REPORT ===");
            writer.println("Date: " + date);
            writer.println("Generated on: " + LocalDateTime.now());
            writer.println("====================================");
            
            int totalFlights = 0;
            int totalAvailableSeats = 0;
            
            for (Flight flight : flights) {
                if (flight.getDepartureTime().startsWith(date)) {
                    writer.println("Flight #: " + flight.getFlightNumber());
                    writer.println("Airline: " + flight.getAirline());
                    writer.println("Route: " + flight.getOrigin() + " to " + flight.getDestination());
                    writer.println("Departure: " + flight.getDepartureTime());
                    writer.println("Available Seats: " + flight.getAvailableSeats());
                    writer.println("Status: " + flight.getFlightStatus());
                    writer.println("------------------------------------");
                    
                    totalFlights++;
                    totalAvailableSeats += flight.getAvailableSeats();
                }
            }
            
            writer.println("\nSUMMARY:");
            writer.println("Total Flights: " + totalFlights);
            writer.println("Total Available Seats: " + totalAvailableSeats);
            
            System.out.println("Report generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    void generateBookingSummaryReport() throws Exception {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        
        String fileName = "BookingSummary_" + startDate + "_to_" + endDate + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== BOOKING SUMMARY REPORT ===");
            writer.println("Period: " + startDate + " to " + endDate);
            writer.println("Generated on: " + LocalDateTime.now());
            writer.println("====================================");
            
            // Get bookings in date range
            String sql = "SELECT b.*, f.flightNumber, f.origin, f.destination " +
                        "FROM bookings b JOIN flights f ON b.flightId = f.id " +
                        "WHERE b.created_at BETWEEN ? AND ?";
            
            try (PreparedStatement stmt = dao.connection.prepareStatement(sql)) {
                stmt.setString(1, startDate + " 00:00:00");
                stmt.setString(2, endDate + " 23:59:59");
                
                ResultSet rs = stmt.executeQuery();
                int totalBookings = 0;
                double totalRevenue = 0;
                
                while (rs.next()) {
                    writer.println("Booking Ref: " + rs.getString("bookingReference"));
                    writer.println("Flight: " + rs.getString("flightNumber") + " (" + 
                                 rs.getString("origin") + " to " + rs.getString("destination") + ")");
                    writer.println("Status: " + rs.getString("status"));
                    writer.println("Payment Status: " + rs.getString("payment_status"));
                    writer.println("Amount: $" + rs.getDouble("totalPrice"));
                    writer.println("------------------------------------");
                    
                    totalBookings++;
                    if ("PAID".equalsIgnoreCase(rs.getString("payment_status"))) {
                        totalRevenue += rs.getDouble("totalPrice");
                    }
                }
                
                writer.println("\nSUMMARY:");
                writer.println("Total Bookings: " + totalBookings);
                writer.println("Total Revenue: $" + totalRevenue);
                
                System.out.println("Report generated: " + fileName);
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    void generateRevenueReport() throws SQLException {
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        
        String fileName = "RevenueReport_" + month + "_" + year + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== REVENUE REPORT ===");
            writer.println("Month: " + month + "/" + year);
            writer.println("Generated on: " + LocalDateTime.now());
            writer.println("====================================");
            
            // Get payments in month/year
            String sql = "SELECT p.*, f.flightNumber, f.origin, f.destination " +
                        "FROM payments p " +
                        "JOIN bookings b ON p.bookingReference = b.bookingReference " +
                        "JOIN flights f ON b.flightId = f.id " +
                        "WHERE MONTH(p.transactionDate) = ? AND YEAR(p.transactionDate) = ? " +
                        "AND p.status = 'COMPLETED'";
            
            try (PreparedStatement stmt = dao.connection.prepareStatement(sql)) {
                stmt.setInt(1, month);
                stmt.setInt(2, year);
                
                ResultSet rs = stmt.executeQuery();
                double totalRevenue = 0;
                int transactionCount = 0;
                
                writer.printf("%-15s %-10s %-20s %-15s %-10s%n", 
                    "Booking Ref", "Amount", "Flight", "Method", "Date");
                writer.println("------------------------------------------------------------");
                
                while (rs.next()) {
                    writer.printf("%-15s $%-9.2f %-20s %-15s %-10s%n",
                        rs.getString("bookingReference"),
                        rs.getDouble("amount"),
                        rs.getString("flightNumber") + " (" + rs.getString("origin") + "-" + 
                        rs.getString("destination") + ")",
                        rs.getString("method"),
                        rs.getTimestamp("transactionDate").toLocalDateTime().toLocalDate());
                    
                    totalRevenue += rs.getDouble("amount");
                    transactionCount++;
                }
                
                writer.println("\nSUMMARY:");
                writer.println("Total Transactions: " + transactionCount);
                writer.println("Total Revenue: $" + totalRevenue);
                
                System.out.println("Report generated: " + fileName);
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
    public boolean processRefund(String bookingReference) throws Exception {
        Booking booking = dao.getBookingByReference(bookingReference);
        if (booking == null) {
            System.out.println("Booking not found!");
            return false;
        }

        System.out.println("\nProcessing refund for booking: " + bookingReference);
        System.out.println("Amount to refund: $" + booking.getTotalPrice());
        
        // Update payment status to REFUNDED
        Payment payment = dao.getPaymentByBooking(bookingReference);
        if (payment != null) {
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            dao.recordPayment(payment); // Update existing payment
        }
        
        System.out.println("Refund processed successfully!");
        return true;
    }

private String getSeatForPassenger(Booking booking, Passenger passenger) {
    // Implement logic to match passenger with their seat
    // This is a simplified version - you might need a more robust implementation
    if (booking.getPassengers().size() == booking.getSeatSelections().size()) {
        int index = booking.getPassengers().indexOf(passenger);
        if (index >= 0 && index < booking.getSeatSelections().size()) {
            return booking.getSeatSelections().get(index);
        }
    }
    return "Not Assigned";
}
}
/*
generateRevenueReport()
 generateBookingSummaryReport()
 generateDailyFlightReport()
 getSeatForPassenger(Booking booking, Passenger passenger)
 generateETicketFile(Booking booking, Flight flight)
 generateETicket(Booking booking)

 */