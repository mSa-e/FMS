//     // 2. Search Flights
//     public void searchFlights() {
//         try {
//             System.out.println("\n=== SEARCH FLIGHTS ===");
//             System.out.print("Enter origin: ");
//             String origin = scanner.nextLine();
//             System.out.print("Enter destination: ");
//             String destination = scanner.nextLine();
//             System.out.print("Enter departure date (YYYY-MM-DD): ");
//             String date = scanner.nextLine();

//             List<Flight> flights = dao.searchFlights(origin, destination, date);
            
//             if (flights.isEmpty()) {
//                 System.out.println("No flights found matching your criteria.");
//                 return;
//             }

//             System.out.println("\nAvailable Flights:");
//             System.out.printf("%-12s %-15s %-10s %-10s %-20s %-20s %-8s %-10s %-10s %-10s\n",
//                 "Flight No", "Airline", "Origin", "Dest", "Departure", "Arrival", "Seats", "Economy", "Business", "First");
            
//             for (Flight flight : flights) {
//                 System.out.printf("%-12s %-15s %-10s %-10s %-20s %-20s %-8d $%-9.2f $%-9.2f $%-9.2f\n",
//                     flight.getFlightNumber(),
//                     flight.getAirline(),
//                     flight.getOrigin(),
//                     flight.getDestination(),
//                     flight.getDepartureTime(),
//                     flight.getArrivalTime(),
//                     flight.getAvailableSeats(),
//                     flight.getEconomyPrice(),
//                     flight.getBusinessPrice(),
//                     flight.getFirstClassPrice());
//             }
//         } catch (SQLException e) {
//             System.err.println("Error searching flights: " + e.getMessage());
//         }
//     }

//     // 3. View Booking
//     public void viewBooking() {
//         try {
//             int userId = user.getUserId();
//             List<Booking> bookings = dao.getBookingsByCustomer(userId);
            
//             if (bookings.isEmpty()) {
//                 System.out.println("You have no bookings.");
//                 return;
//             }

//             System.out.println("\n=== YOUR BOOKINGS ===");
//             for (Booking booking : bookings) {
//                 Flight flight = dao.getFlightById(booking.getFlightId());
//                 System.out.println("Booking Ref: " + booking.getBookingReference());
//                 System.out.println("Flight: " + flight.getFlightNumber() + " (" + flight.getOrigin() + " to " + flight.getDestination() + ")");
//                 System.out.println("Status: " + booking.getStatus());
//                 System.out.println("Payment: " + booking.getPaymentStatus());
//                 System.out.println("Total: $" + booking.getTotalPrice());
//                 System.out.println("Passengers: " + booking.getPassengers());
//                 System.out.println("------------------------");
//             }
//         } catch (Exception e) {
//             System.err.println("Error viewing bookings: " + e.getMessage());
//         }
//     }

//     // 4. Create Booking
//     public void createBooking() {
//         try {
//             // First search for available flights
//             searchFlights();
            
//             System.out.print("\nEnter flight number to book: ");
//             String flightNumber = scanner.nextLine();
            
//             Flight selectedFlight = dao.getFlightByNumber(flightNumber);
//             if (selectedFlight == null) {
//                 System.out.println("Flight not found.");
//                 return;
//             }

//             System.out.print("Enter number of passengers: ");
//             int passengerCount = scanner.nextInt();
//             scanner.nextLine(); // consume newline
            
//             if (!selectedFlight.checkAvailability(passengerCount)) {
//                 System.out.println("Not enough seats available.");
//                 return;
//             }

//             // Collect passenger details
//             List<Passenger> passengers = new ArrayList<>();
//             for (int i = 0; i < passengerCount; i++) {
//                 System.out.println("\nPassenger " + (i+1) + " details:");
//                 Passenger p = new Passenger();
//                 System.out.print("Name: ");
//                 p.setName(scanner.nextLine());
//                 System.out.print("Passport Number: ");
//                 p.setPassportNumber(scanner.nextLong());
//                 scanner.nextLine(); // consume newline
//                 System.out.print("Date of Birth (YYYY-MM-DD): ");
//                 p.setDateOfBirth(scanner.nextLine());
//                 System.out.print("Special Requests: ");
//                 p.setSpecialRequests(scanner.nextLine());
//                 passengers.add(p);
//             }

//             System.out.print("Select seat class (ECONOMY/BUSINESS/FIRST_CLASS): ");
//             SeatClass seatClass = SeatClass.valueOf(scanner.nextLine().toUpperCase());
            
//             double totalPrice = selectedFlight.calculatePrice(seatClass, passengerCount);
            
//             // Create booking
//             Booking booking = new Booking();
//             booking.setBookingReference(generateBookingRef());
//             booking.setCustomerId(user.getUserId());
//             booking.setFlightId(selectedFlight.getId());
//             booking.setPassengers(String.join(",", passengers.stream().map(Passenger::getName).toList()));
//             booking.setSeatSelections(seatClass.name());
//             booking.setStatus("CONFIRMED");
//             booking.setPaymentStatus("PENDING");
//             booking.setTotalPrice(totalPrice);
            
//             if (dao.createBooking(booking)) {
//                 // Add passengers to database
//                 for (Passenger p : passengers) {
//                     p.setBookingReference(booking.getBookingReference());
//                     dao.addPassenger(p);
//                 }
                
//                 System.out.println("Booking created successfully!");
//                 System.out.println("Booking Reference: " + booking.getBookingReference());
//                 System.out.println("Total Price: $" + totalPrice);
//             } else {
//                 System.out.println("Failed to create booking.");
//             }
//         } catch (Exception e) {
//             System.err.println("Error creating booking: " + e.getMessage());
//         }
//     }

//     private String generateBookingRef() {
//         return "LH-" + System.currentTimeMillis();
//     }
// }

        // // Search flights method
    // public List<Flight> searchFlights(String origin, String destination, Timestamp departureDate) throws SQLException {
    //     List<Flight> flights = new ArrayList<>();
    //     String query = "SELECT * FROM Flights WHERE origin LIKE ? AND destination LIKE ? " +
    //                 "AND DATE(departureTime) = DATE(?) AND availableSeats > 0";
        
    //     try (PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setString(1, "%" + origin + "%");
    //         stmt.setString(2, "%" + destination + "%");
    //         stmt.setTimestamp(3, departureDate);
            
    //         try (ResultSet rs = stmt.executeQuery()) {
    //             while (rs.next()) {
    //                 Flight flight = new Flight();
    //                 flight.setFlightNumber(rs.getString("flightNumber"));
    //                 flight.setAirline(rs.getString("airline"));
    //                 flight.setOrigin(rs.getString("origin"));
    //                 flight.setDestination(rs.getString("destination"));
    //                 flight.setDepartureTime(rs.getTimestamp("departureTime"));
    //                 flight.setArrivalTime(rs.getTimestamp("arrivalTime"));
    //                 flight.setTotalSeats(rs.getInt("totalSeats"));
    //                 flight.setAvailableSeats(rs.getInt("availableSeats"));
    //                 flight.setStatus(rs.getString("status"));
    //                 flights.add(flight);
    //             }
    //         }
    //     }
    //     return flights;
    // }


// Update flight information
// public boolean updateFlight(Flight flight) throws SQLException {
//     String query = "UPDATE Flights SET flightNumber=?, airline=?, origin=?, destination=?, " +
//                    "departureTime=?, arrivalTime=?, totalSeats=?, availableSeats=?, status=? " +
//                    "WHERE id=?";
    
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setString(1, flight.getFlightNumber());
//         stmt.setString(2, flight.getAirline());
//         stmt.setString(3, flight.getOrigin());
//         stmt.setString(4, flight.getDestination());
//         stmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
//         stmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
//         stmt.setInt(7, flight.getTotalSeats());
//         stmt.setInt(8, flight.getAvailableSeats());
//         stmt.setString(9, flight.getStatus());
//         stmt.setInt(10, flight.getId());
        
//         return stmt.executeUpdate() > 0;
//     } catch (SQLException e) {
//         System.err.println("Error updating flight: " + e.getMessage());
//         return false;
//     }
// }

// public boolean deleteFlight(int flightId) throws SQLException {
//     String query = "DELETE FROM Flights WHERE id=?";
    
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setInt(1, flightId);
//         return stmt.executeUpdate() > 0;
//     } catch (SQLException e) {
//         System.err.println("Error deleting flight: " + e.getMessage());
//         return false;
//     }
// }
// public Flight getFlightById(int flightId) throws SQLException {
//     String query = "SELECT * FROM Flights WHERE id=?";
//     try (PreparedStatement stmt = connection.prepareStatement(query)) {
//         stmt.setInt(1, flightId);
//         try (ResultSet rs = stmt.executeQuery()) {
//             if (rs.next()) {
//                 Flight flight = new Flight();
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
//                 return flight;
//             }
//         }
//     }
//     return null;
// }