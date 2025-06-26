    import java.util.*;

    public final class UserInfo extends USER {
        Scanner in =new Scanner(System.in);
        Scanner INT =new Scanner(System.in);
        Scanner LONG =new Scanner(System.in);
        private  String Fname;
        private  String Mname;
        private String Lname;
        private String nationality;
        private String job;
        private int yearOfBirth;
        private String password;
        private String email;
        private long phoneNumber;
        private long nationalID;
        String username;
        private int id;


        public UserInfo(boolean T)
        {

            insertData();
        }
            public UserInfo()
        {
            
        }
    public void insertData(){
                try{
                System.out.print("Insert First Name:");
                Fname=in.next().trim();
                System.out.print("Insert Middle Name:");
                Mname=in.next().trim();
                System.out.print("Insert Last Name :");
                Lname=in.next().trim();
                System.out.print("Insert nationality :");
                nationality=in.next().trim();
                System.out.print("Insert national ID :");
                nationalID=LONG.nextLong();
                System.out.print("Insert year of birth :");
                yearOfBirth=INT.nextInt();
                System.out.print("Insert your email :");
                email=in.next().trim();
                System.out.print("Insert your phone number :");
                phoneNumber=LONG.nextLong();
                System.out.print("Insert Job :");
                job=in.next().trim();
                username=Fname+Lname+".luftHansa";
                password=generateID();
                }
                catch(Exception e){
                    System.out.println("The Error id = "+e);
                    System.out.println("You have Exited");
                    System.out.println("Please try Again!!!");
                }  
    }
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public String getFname() {
        return Fname;
    }
    @Override
    public String getPassword() {
        return password;
    }
    public String getMname() {
        return Mname;
    }
    public String getLname() {
        return Lname;
    }
    public String getNationality() {
        return nationality;
    }
    public String getJob() {
        return job;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public long getNationalID() {
        return nationalID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getUsername() {
        return username;
    }
        public long generateNUM() 
        {
            Random random = new Random();
            return 1_000 + random.nextInt(9_000);
        }
    public String generate() {
        final String ALPHANUMERIC = "A?><:{KLB#$%^&CIJ!@*()_+PQRS3)_352wxFGHbcdefghijklmnyz";
            Random random = new Random();
            StringBuilder sb = new StringBuilder(15);
            
            for (int i = 0; i < 15; i++) {
                int index = random.nextInt(ALPHANUMERIC.length());
                sb.append(ALPHANUMERIC.charAt(index));
            }
            
            return sb.toString();
        }
    public String generateID() 
        {
    return generateNUM()+generate();
        }

    @Override
    public void login()throws Exception{
        try {
            DAO dao = new DAO();
            boolean valid = dao.validateUser(this.username, this.password);
            if (valid) {
                System.out.println("User '" + username + "' successfully logged in.");
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }

        public void userMenu(UserInfo user) throws Exception {
            Scanner in = new Scanner(System.in);
            boolean session = true;
            int choice = 0;
                DAO2 dao = new DAO2();
            PaymentProcessor paymentProcessor = new PaymentProcessor(dao);
            while (session) {
                try{
                System.out.println("\n USER DASHBOARD ");
                System.out.println("1. Update Profile");
                System.out.println("2. Search Flights ");
                System.out.println("3. View Booking"); 
                System.out.println("4. Create Booking");  
                System.out.println("5. Cancel Booking");   
                System.out.println("6. View profile Details");  
                System.out.println("7. Create a File about user");
                System.out.println("8. Complete Payment");
                System.out.println("9. Money Refund");
                System.out.println("10. Log Out");
                System.out.print("Choice: ");
                choice = in.nextInt();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

                switch (choice) {


                    case 1 ->     updateProfile();

                    case 2 -> dao.displayAvailableFlights();  
                    
                    case 3 ->viewBooking();

                    case 4 -> createBookingFromInput();

                    case 5 ->{
                        Scanner x =new Scanner(System.in);
                        System.out.print("Insert booking ID : ");
                        int c=x.nextInt();
                        System.out.print("Insert your ID : ");
                        int i=x.nextInt();
                        dao.cancelBooking(c, i);
                    }
                    case 6 ->
                    {
                        viewProfileDetails(1);
                    }
                                case 7 -> 
                    {
                        Scanner scan=new Scanner(System.in);
                                DAO da = new DAO();
                                System.out.print("Insert ID : ");
                                int identification =scan.nextInt(); 
                                UserInfo use = da.getUserById(identification);

                                UserInfo userObj = new UserInfo();
                                userObj.createATextFile(use);
                    }
                            case 8 -> 
                            {
                    System.out.print("Enter Booking Reference: ");
                    String ref = in.next();
                    paymentProcessor.processPayment(ref);
                                }
                        case 9->{
                    System.out.print("Enter Booking Reference: ");
                    String ref = in.next();
                    paymentProcessor.processRefund(ref);
                        }
                    case 10 -> {
                        session = false;
                    }
                    default -> System.out.println("Invalid choice.");

                }
            }
        }
    @Override
    public void logOut() throws Exception {
        System.out.println("User '" + username + "' logged out and activity logged.");
    }
    public void setFname(String fname) {
        Fname = fname;
    }
    public void setMname(String mname) {
        Mname = mname;
    }
    public void setLname(String lname) {
        Lname = lname;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setNationalID(long nationalID) {
        this.nationalID = nationalID;
    }
    public void setUsername(String username) {
        this.username = username;
    }

        public int getUserId() throws Exception {
            DAO dao = new DAO();
            return dao.getID(this.username, this.password);
        }

    public void createBookingFromInput()throws Exception {
        Scanner scanner = new Scanner(System.in);
        DAO2 dao=new DAO2();
        try {
            // Get current user ID
            int userId = this.getUserId();
            if (userId == -1) {
                System.out.println("User not found. Please log in again.");
                return;
            }

            // Create a new booking
            Booking booking = new Booking();
            booking.setCustomerId(userId);
            
            // Generate a unique booking reference
            booking.setBookingReference(generateBookingReference());
            dao.displayAvailableFlights(); // Call a method that lists flights
                System.out.print("Enter Flight ID: ");
                int flightId = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Check if flight exists (you need a method like this in your database handler)
                if (!dao.flightExists(flightId)) {
                    System.out.println("Error: Flight ID " + flightId + " does not exist.");
                    return;
                }
                booking.setFlightId(flightId);
            
            // Get passenger information
            List<Passenger> passengers = new ArrayList<>();
            System.out.print("How many passengers? ");
            int passengerCount = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            for (int i = 0; i < passengerCount; i++) {
                System.out.println("\nEnter details for passenger " + (i + 1));
                Passenger passenger = new Passenger();
                
                System.out.print("Name: ");
                passenger.setName(scanner.nextLine());
                
                System.out.print("Passport Number: ");
                passenger.setPassportNumber(scanner.nextLong());
                scanner.nextLine(); // consume newline
                
                System.out.print("Date of Birth (YYYY-MM-DD): ");
                passenger.setDateOfBirth(scanner.nextLine());
                
                System.out.print("Special Requests (if any): ");
                passenger.setSpecialRequests(scanner.nextLong());
                
                passengers.add(passenger);
            }
            booking.setPassengers(passengers);
            
            List<String> seatSelections = new ArrayList<>();
            scanner.nextLine(); 
            System.out.println("\nEnter seat selections:");
            for (int i = 0; i < passengerCount; i++) {
                System.out.print("Seat for passenger " + (i + 1) + ": ");
                seatSelections.add(scanner.nextLine());
            }
            booking.setSeatSelections(seatSelections);
            
            // Set initial statuses
booking.setStatus(Booking.BookingStatus.PENDING);
booking.setPaymentStatus(Booking.PaymentStatus.PENDING);
            
            // Calculate total price
            System.out.print("Enter total price: ");
            booking.setTotalPrice(scanner.nextDouble());
            scanner.nextLine(); // consume newline
            
            // Create the booking
            if (createBooking(booking)) {
                System.out.println("Booking created successfully!");
                System.out.println("Your booking reference: " + booking.getBookingReference());
            } else {
                System.out.println("Failed to create booking.");
            }
            
        } catch (Exception e) {
            System.err.println("Error during booking creation: " + e.getMessage());
        }
    }
        public boolean createBooking(Booking booking) {
        try {
            DAO2 dao = new DAO2();
            boolean success = dao.createBooking(booking);
            return success;
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return false;
        }
    }


    // Helper method to generate a unique booking reference
    private String generateBookingReference() {
        return "BK" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
private void viewBooking() throws Exception {
    Scanner in = new Scanner(System.in);
    DAO2 dao = new DAO2();
    
    System.out.print("Enter Booking Reference: ");
    String ref = in.next();
    
    Booking booking = dao.getBookingByReference(ref);
    if (booking != null) {
        System.out.println("\n=== BOOKING DETAILS ===");
        System.out.println("Reference: " + booking.getBookingReference());
        System.out.println("Status: " + booking.getStatus());
        System.out.println("Payment Status: " + booking.getPaymentStatus());
        System.out.println("Total Price: $" + booking.getTotalPrice());
        
        Flight flight = dao.getFlightById(booking.getFlightId());
        if (flight != null) {
            System.out.println("\nFlight: " + flight.getFlightNumber());
            System.out.println("Route: " + flight.getOrigin() + " to " + flight.getDestination());
        }
    } else {
        System.out.println("Booking not found!");
    }
}
    }