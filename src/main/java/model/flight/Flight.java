package model.flight;

import java.time.LocalTime;

public class Flight {
    private Enum<FlightType> type;
    private String number;
    private String airline;
    private String departureCity;
    private String arrivalCity;
    private LocalTime timeOfDeparture;
    private LocalTime timeOfArrival;
    private String departureTerminal;
    private String arrivalTerminal;
    private Enum<FlightStatus> status;

    public static Enum<FlightStatus> getFlightStatus(String status, Enum<FlightType> type, Flight flight) {
        if (status != null) {
            return FlightStatus.valueOf(status);
        } else if (type == FlightType.DEPARTURE) {
            return FlightStatus.getDepartureFlightStatus(flight.timeOfDeparture);
        } else {
            return FlightStatus.getArrivalFlightStatus(flight.timeOfArrival);
        }
    }

    @Override
    public String toString() {
        return "flight: " +
                "type=" + type +
                ", number='" + number + '\'' +
                ", airline='" + airline + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", timeOfDeparture=" + timeOfDeparture +
                ", timeOfArrival=" + timeOfArrival +
                ", departureTerminal='" + departureTerminal + '\'' +
                ", arrivalTerminal='" + arrivalTerminal + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Enum<FlightType> getType() {
        return type;
    }

    public void setType(Enum<FlightType> type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(String time) {
        String[] times = time.split(":");

            this.timeOfDeparture =
                    LocalTime.of(Integer.valueOf(times[0]), Integer.valueOf(times[1]));

    }

    public LocalTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(String time) {
        String[] times = time.split(":");

            this.timeOfArrival =
                    LocalTime.of(Integer.valueOf(times[0]), Integer.valueOf(times[1]));

    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public Enum<FlightStatus> getStatus() {
        return status;
    }

    public void setStatus(Enum<FlightStatus> status) {
        this.status = status;
    }

}
