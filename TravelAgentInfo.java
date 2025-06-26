//     import java.io.*;
//     import java.sql.*;
// import java.time.LocalDateTime;
import java.util.*;
    public class TravelAgentInfo extends USER {
        public TravelAgentInfo(Boolean T) {
            super("SystemAgent.luftHansa","5534(hJUgVwS_)CK{@K");
        }
                public TravelAgentInfo() {}
                    @Override
                    public void login()throws Exception{
                        try {
                            DAO dao = new DAO();
                            boolean valid = dao.validateUser(getUsername(), getPassword());
                            if (valid) {
                                System.out.println("Agent '" + getUsername()+ "' logged in.");
                            } else {
                                System.out.println("Invalid agent credentials.");
                            }
                        } catch (Exception e) {
                            System.out.println("Agent login error: " + e.getMessage());
                        }
                    }

        public void agentMenu(TravelAgentInfo agent)throws Exception {
            Scanner in = new Scanner(System.in);
            boolean session = true;
            int choice = 0 ;
            while (session) {

                try{
                System.out.println("\n Agent DASHBOARD ");
                System.out.println("1. Update Profile");
                System.out.println("2. Manage Flights Information");
                System.out.println("3. Create Flights For Customer");
                System.out.println("4. Assist customers with bookings"); 
                System.out.println("5. Generate Reports");
                System.out.println("6. View profile Details");
                System.out.println("7. Create a File about Agent");
                System.out.println("8. Log Out");
                System.out.print("Choice: ");
                choice = in.nextInt();
                            }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

                switch (choice) {

                    case 1 ->   updateProfile(); 

                case 2 ->       manageFlightsMenu();


                case 3 -> addNewFlight();

                case 4 -> assistWithBookings();


                case 5 -> generateReportsMenu();

                
                    case 6 ->
                    {
                        viewProfileDetails(1);
                    }
                    case 7 -> 
                    {
                        Scanner scan=new Scanner(System.in);
                                DAO dao = new DAO();
                                System.out.print("Insert ID : ");
                                int identification =scan.nextInt(); 
                                UserInfo user = dao.getUserById(identification);

                                UserInfo userObj = new UserInfo();
                                userObj.createATextFile(user);
                    }
                    case 8 -> {
                        session = false;
                    }
                    default -> System.out.println("Invalid choice.");

                }

            }
            in.close();
        }
    @Override
    public void logOut() throws Exception {
        System.out.println("Travel Agent '" + getUsername() + "' logged out and activity logged.");
    }

private void addNewFlight() throws Exception {
    Scanner scanner = new Scanner(System.in);
    DAO2 dao = new DAO2();
    
    System.out.println("\n=== Add New Flight ===");
    
    // First show available users
    System.out.println("\nAvailable Users:");
    List<UserInfo> users = dao.getAllUsers();
    if (users.isEmpty()) {
        System.out.println("No users found in the system!");
        return;
    }
    
    for (UserInfo user : users) {
        System.out.println(user.getId() + ": " + user.getFname() + " " + user.getLname());
    }
    
    // Choose flight type
    String flightType;
    do {
        System.out.print("\nEnter Flight Type (DOMESTIC/INTERNATIONAL): ");
        flightType = scanner.nextLine().trim().toUpperCase();
        if (!flightType.equals("DOMESTIC") && !flightType.equals("INTERNATIONAL")) {
            System.out.println("Invalid flight type. Please enter DOMESTIC or INTERNATIONAL");
        }
    } while (!flightType.equals("DOMESTIC") && !flightType.equals("INTERNATIONAL"));

    Flight flight = flightType.equals("DOMESTIC") ? new DomesticFlight() : new InternationalFlight();
    flight.setFlightType(flightType);
    
    // Get user ID - fixed version
    boolean validUserId = false;
    int userId ;
    while (!validUserId) {
        try {
            System.out.print("Enter User ID from the list above: ");
            userId = Integer.parseInt(scanner.nextLine());
            
            // Replace the lambda with a traditional loop
            for (UserInfo user : users) {
                if (user.getId() == userId) {
                    validUserId = true;
                    break;
                }
            }
            
            if (!validUserId) {
                System.out.println("Invalid user ID. Please select from the list.");
            } else {
                flight.setId(userId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    System.out.print("Enter Flight Number: ");
    flight.setFlightNumber(scanner.nextInt());
    
    scanner.nextLine(); // consume newline
    
    System.out.print("Enter Airline: ");
    flight.setAirline(scanner.nextLine());
    
    System.out.print("Enter Origin: ");
    flight.setOrigin(scanner.nextLine());
    
    System.out.print("Enter Destination: ");
    flight.setDestination(scanner.nextLine());
    
    System.out.print("Enter Departure Date (YYYY-MM-DD): ");
    String departureDate = scanner.nextLine();
    System.out.print("Enter Departure Time (HH:MM): ");
    String departureTime = scanner.nextLine();
    flight.setDepartureTime(departureDate + " " + departureTime + ":00");
    
    System.out.print("Enter Arrival Date (YYYY-MM-DD): ");
    String arrivalDate = scanner.nextLine();
    System.out.print("Enter Arrival Time (HH:MM): ");
    String arrivalTime = scanner.nextLine();
    flight.setArrivalTime(arrivalDate + " " + arrivalTime + ":00");
    
    System.out.print("Enter Total Seats: ");
    flight.setTotalSeats(scanner.nextInt());
    flight.setAvailableSeats(flight.getTotalSeats());
    scanner.nextLine(); // consume newline
    
    // Get prices for each class
    System.out.print("Enter Economy Class Price: ");
    flight.setEconomyPrice(scanner.nextDouble());
    
    System.out.print("Enter Business Class Price: ");
    flight.setBusinessPrice(scanner.nextDouble());
    
    System.out.print("Enter First Class Price: ");
    flight.setFirstClassPrice(scanner.nextDouble());
    scanner.nextLine(); // consume newline
    
    // Validate status input
    String status;
    while(true) {
        System.out.print("Enter Status (On Time/Delayed/Cancelled): ");
        status = scanner.nextLine().trim();
        if (flight.setFlightStatus(status)) {
            break;
        } else {
            System.out.println("Invalid status. Please enter one of: On Time, Delayed, Cancelled");
        }
    }
    flight.setFlightStatus(status);

    if (dao.addFlight(flight)) {
        System.out.println("Flight added successfully!");
    } else {
        System.out.println("Failed to add flight. Please verify your account.");
    }
}

private void manageFlightsMenu() throws Exception {
    Scanner scanner=new Scanner(System.in);
    DAO2 dao=new DAO2();
        System.out.println("\n=== FLIGHT MANAGEMENT ===");
        System.out.println("1. Add New Flight");
        System.out.println("2. Update Flight");
        System.out.println("3. Delete Flight");
        System.out.println("4. View All Flights");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1 -> addNewFlight();
            case 2 -> dao.updateFlight();
            case 3 -> dao.deleteFlight();
            case 4 -> dao.displayAvailableFlights();
            default -> System.out.println("Invalid choice.");
        }
    }
    private void generateReportsMenu() throws Exception {
        Scanner scanner=new Scanner(System.in);
        DAO2 dao=new DAO2();
        PaymentProcessor pay=new PaymentProcessor(dao);
        System.out.println("\n=== REPORT GENERATION ===");
        System.out.println("1. Daily Flight Report");
        System.out.println("2. Booking Summary Report");
        System.out.println("3. Revenue Report");
        System.out.print("Choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1 -> pay.generateDailyFlightReport();
            case 2 -> pay.generateBookingSummaryReport();
            case 3 -> pay.generateRevenueReport();
            default -> System.out.println("Invalid choice.");
        }
    }
private void assistWithBookings() throws Exception {
    Scanner scanner = new Scanner(System.in);
    DAO2 dao = new DAO2();
    PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
    
    System.out.println("\n=== BOOKING ASSISTANCE ===");
    System.out.println("1. View Booking Details");
    System.out.println("2. Process Payment");
    System.out.println("3. Issue Refund");
    System.out.println("4. Cancel Booking");
    System.out.print("Select option (1-4): ");
    
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume newline
    
    System.out.print("Enter Booking Reference: ");
    String bookingRef = scanner.nextLine();
    
    switch (choice) {
        case 1 -> viewBookingDetails(bookingRef);
        case 2 -> processPaymentForBooking(bookingRef);
        case 3 -> issueRefundForBooking(bookingRef);
        case 4 -> cancelBooking(bookingRef);
        default -> System.out.println("Invalid option selected.");
    }
}
private void viewBookingDetails(String bookingRef) throws Exception {
    DAO2 dao=new DAO2();
    Booking booking = dao.getBookingByReference(bookingRef);
    if (booking == null) {
        System.out.println("Booking not found!");
        return;
    }
    
    System.out.println("\n=== BOOKING DETAILS ===");
    System.out.println("Reference: " + booking.getBookingReference());
    System.out.println("Status: " + booking.getStatus());
    System.out.println("Payment Status: " + booking.getPaymentStatus());
    System.out.println("Total Price: $" + booking.getTotalPrice());
    
    // Get flight details
    Flight flight = dao.getFlightById(booking.getFlightId());
    if (flight != null) {
        System.out.println("\nFlight Information:");
        System.out.println("Flight #: " + flight.getFlightNumber());
        System.out.println("Airline: " + flight.getAirline());
        System.out.println("Route: " + flight.getOrigin() + " to " + flight.getDestination());
        System.out.println("Departure: " + flight.getDepartureTime());
        System.out.println("Arrival: " + flight.getArrivalTime());
    }
    
    // Get passenger details
    System.out.println("\nPassenger(s):");
    List<Passenger> passengers = booking.getPassengers();
    List<String> seatSelections = booking.getSeatSelections();
    
    for (int i = 0; i < passengers.size(); i++) {
        Passenger p = passengers.get(i);
        String seat = i < seatSelections.size() ? seatSelections.get(i) : "Not Assigned";
        
        System.out.println((i+1) + ". " + p.getName());
        System.out.println("   Passport: " + p.getPassportNumber());
        System.out.println("   Seat: " + seat);
    }
}

private void processPaymentForBooking(String bookingRef) throws Exception {
            DAO2 dao=new DAO2();
    PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
    boolean success = paymentProcessor.processPayment(bookingRef);
    
    if (success) {
        System.out.println("Payment processed successfully!");
    } else {
        System.out.println("Failed to process payment.");
    }
}

private void issueRefundForBooking(String bookingRef) throws Exception {
            DAO2 dao=new DAO2();
    PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
    boolean success = paymentProcessor.processRefund(bookingRef);
    
    if (success) {
        System.out.println("Refund issued successfully!");
    } else {
        System.out.println("Failed to issue refund.");
    }
}

private void cancelBooking(String bookingRef) throws Exception {
    DAO2 dao=new DAO2();
    Booking booking = dao.getBookingByReference(bookingRef);
    if (booking == null) {
        System.out.println("Booking not found!");
        return;
    }
    
    System.out.println("\nYou are about to cancel booking: " + bookingRef);
    System.out.println("Flight: " + dao.getFlightById(booking.getFlightId()).getFlightNumber());
    System.out.println("Total Amount: $" + booking.getTotalPrice());
    System.out.print("Are you sure you want to cancel this booking? (Y/N): ");
    
    Scanner scanner = new Scanner(System.in);
    String confirmation = scanner.nextLine();
    
    if (confirmation.equalsIgnoreCase("Y")) {
        // Update booking status to cancelled
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setPaymentStatus(Booking.PaymentStatus.REFUNDED);
        dao.updateBookingStatus(bookingRef, Booking.BookingStatus.CANCELLED);
        
        // Update available seats
        Flight flight = dao.getFlightById(booking.getFlightId());
        if (flight != null) {
            flight.setAvailableSeats(flight.getAvailableSeats() + booking.getPassengers().size());
            dao.updateFlight(flight);
        }
        
        System.out.println("Booking cancelled successfully!");
    } else {
        System.out.println("Cancellation aborted.");
    }
}
    }

// private int getUserId()throws Exception{
//     Scanner scanner = new Scanner(System.in);
//     DAO2 dao = new DAO2();
    
//     System.out.print("Enter your username: ");
//     String username = scanner.nextLine();
    
//     System.out.print("Enter your password: ");
//     String password = scanner.nextLine();
    
//     try {
//         return dao.getUserId(username, password);
//     } catch (SQLException e) {
//         System.err.println("Error retrieving user ID: " + e.getMessage());
//         return -1; // Return -1 to indicate failure
//     }
// }


//     private void createBookingForCustomer() throws Exception {
//         System.out.println("\n=== CREATE BOOKING FOR CUSTOMER ===");
        
//         // Get customer ID
//         System.out.print("Enter Customer ID: ");
//         int customerId = scanner.nextInt();
//         scanner.nextLine();
        
//         if (!dao.userExists(customerId)) {
//             System.out.println("Customer does not exist!");
//             return;
//         }

//         // Show available flights
//         dao.displayAvailableFlights();
        
//         // Get flight ID
//         System.out.print("Enter Flight ID: ");
//         int flightId = scanner.nextInt();
//         scanner.nextLine();
        
//         if (!dao.flightExists(flightId)) {
//             System.out.println("Flight does not exist!");
//             return;
//         }

//         // Create booking
//         Booking booking = new Booking();
//         booking.setCustomerId(customerId);
//         booking.setFlightId(flightId);
//         booking.setBookingReference(generateBookingReference());
        
//         // Get passenger information
//         List<Passenger> passengers = new ArrayList<>();
//         System.out.print("How many passengers? ");
//         int passengerCount = scanner.nextInt();
//         scanner.nextLine();
        
//         for (int i = 0; i < passengerCount; i++) {
//             System.out.println("\nEnter details for passenger " + (i + 1));
//             Passenger passenger = new Passenger();
            
//             System.out.print("Name: ");
//             passenger.setName(scanner.nextLine());
            
//             System.out.print("Passport Number: ");
//             passenger.setPassportNumber(scanner.nextLong());
//             scanner.nextLine();
            
//             System.out.print("Date of Birth (YYYY-MM-DD): ");
//             passenger.setDateOfBirth(scanner.nextLine());
            
//             passengers.add(passenger);
//         }
//         booking.setPassengers(passengers);
        
//         // Get seat selections
//         List<String> seatSelections = new ArrayList<>();
//         System.out.println("\nEnter seat selections:");
//         for (int i = 0; i < passengerCount; i++) {
//             System.out.print("Seat for passenger " + (i + 1) + ": ");
//             seatSelections.add(scanner.nextLine());
//         }
//         booking.setSeatSelections(seatSelections);
        
//         // Set prices and status
//         Flight flight = dao.getFlightById(flightId);
//         System.out.print("Enter ticket class (1. Economy, 2. Business, 3. First): ");
//         int classChoice = scanner.nextInt();
//         double pricePerTicket = 0;
        
//         switch (classChoice) {
//             case 1 -> pricePerTicket = flight.getEconomyPrice();
//             case 2 -> pricePerTicket = flight.getBusinessPrice();
//             case 3 -> pricePerTicket = flight.getFirstClassPrice();
//             default -> {
//                 System.out.println("Invalid choice, defaulting to Economy");
//                 pricePerTicket = flight.getEconomyPrice();
//             }
//         }
        
//         booking.setTotalPrice(pricePerTicket * passengerCount);
//         booking.setStatus(Booking.BookingStatus.PENDING);
//         booking.setPaymentStatus(Booking.PaymentStatus.PENDING);
        
//         // Save booking
//         if (dao.createBooking(booking)) {
//             System.out.println("Booking created successfully!");
//             System.out.println("Booking Reference: " + booking.getBookingReference());
            
//             // Process payment
//             System.out.print("Process payment now? (Y/N): ");
//             String processPayment = scanner.next();
//             if (processPayment.equalsIgnoreCase("Y")) {
//                 PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
//                 paymentProcessor.processPayment(booking.getBookingReference());
//             }
//         } else {
//             System.out.println("Failed to create booking.");
//         }
//     }

//     private void assistWithBookings() throws SQLException {
//         System.out.println("\n=== BOOKING ASSISTANCE ===");
//         System.out.println("1. View Booking Details");
//         System.out.println("2. Cancel Booking");
//         System.out.println("3. Process Payment");
//         System.out.print("Choice: ");
        
//         int choice = scanner.nextInt();
//         scanner.nextLine();
        
//         System.out.print("Enter Booking Reference: ");
//         String bookingRef = scanner.nextLine();
        
//         switch (choice) {
//             case 1 -> viewBookingDetails(bookingRef);
//             case 2 -> cancelBooking(bookingRef);
//             case 3 -> processPayment(bookingRef);
//             default -> System.out.println("Invalid choice.");
//         }
//     }

//     private void viewBookingDetails(String bookingRef) throws SQLException {
//         Booking booking = dao.getBookingByReference(bookingRef);
//         if (booking == null) {
//             System.out.println("Booking not found!");
//             return;
//         }
        
//         System.out.println("\n=== BOOKING DETAILS ===");
//         System.out.println("Reference: " + booking.getBookingReference());
//         System.out.println("Status: " + booking.getStatus());
//         System.out.println("Payment Status: " + booking.getPaymentStatus());
//         System.out.println("Total Price: $" + booking.getTotalPrice());
        
//         Flight flight = dao.getFlightById(booking.getFlightId());
//         if (flight != null) {
//             System.out.println("\nFlight Details:");
//             System.out.println("Flight #: " + flight.getFlightNumber());
//             System.out.println("Route: " + flight.getOrigin() + " to " + flight.getDestination());
//             System.out.println("Departure: " + flight.getDepartureTime());
//         }
        
//         System.out.println("\nPassengers:");
//         for (Passenger passenger : booking.getPassengers()) {
//             System.out.println("- " + passenger.getName() + " (Passport: " + passenger.getPassportNumber() + ")");
//         }
//     }

//     private void cancelBooking(String bookingRef) throws Exception {
//         System.out.print("Enter Customer ID for verification: ");
//         int customerId = scanner.nextInt();
//         scanner.nextLine();
        
//         if (dao.cancelBooking(bookingRef, customerId)) {
//             System.out.println("Booking cancelled successfully.");
//         } else {
//             System.out.println("Failed to cancel booking.");
//         }
//     }

//     private void processPayment(String bookingRef) throws Exception {
//         PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
//         paymentProcessor.processPayment(bookingRef);
//     }

//     private String generateBookingReference() {
//         return "BK" + System.currentTimeMillis() + (int)(Math.random() * 1000);
//     }
