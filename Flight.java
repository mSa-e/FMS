public abstract  class Flight {
    private int id;
    private int flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private String FlightStatus;
    private double economyPrice;
    private double businessPrice;
    private double firstClassPrice;
    private String flightType; // "DOMESTIC" or "INTERNATIONAL"
    
    public Flight() 
    {
    }

    // Getters and Setters
    public double getEconomyPrice() { return economyPrice; }
    public void setEconomyPrice(double economyPrice) { this.economyPrice = economyPrice; }
    
    public double getBusinessPrice() { return businessPrice; }
    public void setBusinessPrice(double businessPrice) { this.businessPrice = businessPrice; }
    
    public double getFirstClassPrice() { return firstClassPrice; }
    public void setFirstClassPrice(double firstClassPrice) { this.firstClassPrice = firstClassPrice; }
    
    public String getFlightType() { return flightType; }
    public void setFlightType(String flightType) { this.flightType = flightType; }

    public int getId() { return id; }

    
     public void setId(int id) {
        this.id = id;
    }

    public int getFlightNumber() { return flightNumber; }


    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getAirline() { return airline; }

    public String getOrigin() { return origin; }

    public String getDestination() { return destination; }


    public void setDestination(String destination) {
        this.destination = destination;
    }

        public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public String getDepartureTime() { return departureTime; }


    public String getArrivalTime() { return arrivalTime; }

    public int getTotalSeats() { return totalSeats; }

        public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() { return availableSeats; }

public void setAvailableSeats(int availableSeats) {
    if (availableSeats <= this.totalSeats && availableSeats >= 0) {
        this.availableSeats = availableSeats;
    } else {
        System.out.println("Invalid number of available seats");
    }
}
  
  ////////////////
public abstract  void setPrice(double Price);


public abstract  double calculatePrice();


public boolean setFlightStatus(String status) {
    if (status == null) {
        this.FlightStatus = "On Time"; // default value
        return true;
    } else if (status.equalsIgnoreCase("On Time") || status.equalsIgnoreCase("OnTime")) {
        this.FlightStatus = "On Time";
        return true;
    } else if (status.equalsIgnoreCase("Delayed")) {
        this.FlightStatus = "Delayed";
    return true;
    } else if (status.equalsIgnoreCase("Cancelled")) {
        this.FlightStatus = "Cancelled";
        return true;
    } else {
        throw new IllegalArgumentException("Status must be one of: On Time, Delayed, Cancelled");
    }
}

        public String getFlightStatus() {
        return this.FlightStatus;
    }

    public abstract void generateTicket(); 

    public abstract void updateScheduele();

    public abstract void printFlightSpecificDetails();

    public boolean checkAvailability(int requiredSeats) {
        return availableSeats >= requiredSeats;
    }

    public boolean reserveSeats(int numberOfSeats) {
        if (availableSeats >= numberOfSeats) {
            availableSeats -= numberOfSeats;
            return true;
        }
        return false;
    }

        public void updateStatus(String newStatus) {
        this.FlightStatus = newStatus;
    }
}        