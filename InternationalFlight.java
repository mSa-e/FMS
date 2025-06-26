
public class InternationalFlight extends Flight {

    private boolean passportRequired;
    private boolean visaRequired;
    private double internationalTaxes;
    private static final double BASE_PRICE = 500;



    public boolean isPassportRequired() {
        return passportRequired;
    }

    public void setPassportRequired(boolean passportRequired) {
        this.passportRequired = passportRequired;
    }

    public boolean isVisaRequired() {
        return visaRequired;
    }

    public void setVisaRequired(boolean visaRequired) {
        this.visaRequired = visaRequired;
    }

    public double getInternationalTaxes() {
        return internationalTaxes;
    }

    public void setInternationalTaxes(double internationalTaxes) {
        this.internationalTaxes = internationalTaxes;
    }


    public InternationalFlight() {
    }


@Override
public void setPrice(double Price){}

@Override
public double calculatePrice() {
    return getInternationalTaxes() + BASE_PRICE;
}

@Override
public  void generateTicket(){}

@Override
public  void updateScheduele(){}

@Override
public  void printFlightSpecificDetails(){}







}
    // private boolean visaRequired;
    // private List<String> requiredDocuments;
    // private static final double INTERNATIONAL_TAX_RATE = 0.10;
    
    // @Override
    // public double calculatePrice(SeatClass seatClass, int passengers) {
    //     double basePrice = getBasePriceForClass(seatClass);
    //     return basePrice * passengers * (1 + INTERNATIONAL_TAX_RATE);
    // }
    
    // public boolean checkVisaRequirements(Passenger passenger) {
    //     return !visaRequired || passenger.hasValidVisa();
    // }
    
    // @Override
    // public void generateTicket() {
    //     // International flight specific ticket generation
// public class InternationalFlight extends Flight {
//     private boolean visaRequired;
//     private List<String> requiredDocuments;
    
//     @Override
//     public double calculatePrice(SeatClass seatClass, int passengers) {
//         // International pricing with taxes/fees
//     }
    
//     public boolean checkVisaRequirements(Passenger passenger) {
//         // Visa validation logic
//     }