//Stores passenger info
//generate Text files
public class Passenger {
        private int passengerId;
        private String BookingReference;
        private String name;
            private Long passportNumber;
            private String dateOfBirth;
            private Long specialRequests;

        public void updateInfo(){}
        public void getPassengerDetails(){}
        public int getPassengerId() {
            return passengerId;
        }
        public void setPassengerId(int passengerId) {
            this.passengerId = passengerId;
        }
        public String getBookingReference() {
            return BookingReference;
        }
        public void setBookingReference(String bookingReference) {
            BookingReference = bookingReference;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Long getPassportNumber() {
            return passportNumber;
        }
        public void setPassportNumber(Long passportNumber) {
            this.passportNumber = passportNumber;
        }
        public String getDateOfBirth() {
            return dateOfBirth;
        }
        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }
        public Long getSpecialRequests() {
            return specialRequests;
        }
        public void setSpecialRequests(Long specialRequests) {
            this.specialRequests = specialRequests;
        }

        
}
//     private final String id;
//     private final String name;
//     private final Passport passport;
//     private final LocalDate dateOfBirth;
//     private final Set<SpecialRequest> specialRequests;
    
//     // Builder Pattern for complex construction
//     public static class Builder {
//         // Builder implementation
//     }
    
//     // Domain validation
//     public boolean isValidForFlight(Flight flight) {
//         if (flight instanceof InternationalFlight) {
//             return ((InternationalFlight)flight).checkVisaRequirements(this);
//         }
//         return true;
//     }
// }