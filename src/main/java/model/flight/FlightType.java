package model.flight;

public enum FlightType {
    DEPARTURE, ARRIVAL;

    public static Enum<FlightType> getFlightType(String arrival) {
        return arrival.equals("KYIV") ? ARRIVAL : DEPARTURE;
    }
}
