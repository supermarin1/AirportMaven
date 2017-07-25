package model.price;

import model.flight.Cabin;

public class Price {

    private String flightNumber;
    private Cabin cabin;
    private Integer fare;

    @Override
    public String toString() {
        return "Price{" +
                "flightNumber='" + flightNumber + '\'' +
                ", cabin=" + cabin +
                ", fare=" + fare +
                '}';
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = Cabin.valueOf(cabin);
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }
}
