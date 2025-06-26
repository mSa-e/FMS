    import java.time.ZonedDateTime;
    import java.util.*;

    public class Booking {
        private String bookingReference;
        private int customerId;
        private int flightId;
        private List<Passenger> passengers;
        private List<String> seatSelections;
        private BookingStatus status;
        private PaymentStatus paymentStatus;
        private double totalPrice;
        private ZonedDateTime bookingTime;

        public enum BookingStatus {
            PENDING, CONFIRMED, CANCELLED, COMPLETED
        }

        public enum PaymentStatus {
            PENDING, PAID, FAILED, REFUNDED, PARTIALLY_REFUNDED
        }

        // Getters and Setters
        public String getBookingReference() { return bookingReference; }
        public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
        public int getCustomerId() { return customerId; }
        public void setCustomerId(int customerId) { this.customerId = customerId; }
        public int getFlightId() { return flightId; }
        public void setFlightId(int flightId) { this.flightId = flightId; }
        public List<Passenger> getPassengers() { return passengers; }
        public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
        public List<String> getSeatSelections() { return seatSelections; }
        public void setSeatSelections(List<String> seatSelections) { this.seatSelections = seatSelections; }
        public BookingStatus getStatus() { return status; }
public void setStatus(BookingStatus status) { this.status = status; }
public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
        public PaymentStatus getPaymentStatus() { return paymentStatus; }
        public double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
        public ZonedDateTime getBookingTime() { return bookingTime; }
        public void setBookingTime(ZonedDateTime bookingTime) { this.bookingTime = bookingTime; }


        // Helper methods for string representation
    public String getPassengersAsString() {
        if (passengers == null || passengers.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (Passenger passenger : passengers) {
            if (result.length() > 0) {
                result.append(",");
            }
            result.append(passenger.getName());
        }
        return result.toString();
    }

        public String getSeatSelectionsAsString() {
            if (seatSelections == null) return "";
            return String.join(",", seatSelections);
        }

    }

