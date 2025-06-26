public class DomesticFlight extends Flight {

    private static final double BASE_PRICE = 500;
    private double domesticTax;
    public DomesticFlight() {
    }
    
@Override
public void setPrice(double Price){}
    
@Override
public double calculatePrice() {
    return getDomesticTax() + BASE_PRICE;
}

@Override
public  void generateTicket(){}

@Override
public  void updateScheduele(){}

@Override
public  void printFlightSpecificDetails(){}


public double getDomesticTax() {
    return domesticTax;
}


public void setDomesticTax(double domesticTax) {
    this.domesticTax = domesticTax;
}

}