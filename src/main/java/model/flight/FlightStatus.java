package model.flight;

import java.time.LocalTime;

public enum FlightStatus {
    CHECK_IN(LocalTime.of(2, 40)),
    BOARDING(LocalTime.of(0, 40)),
    GATE_CLOSED(LocalTime.of(0, 5)),
    DEPARTED(LocalTime.of(0, 10)),

    CANCELED(null),
    SCHEDULED(null),
    DELAYED(null),

    IN_FLIGHT(null),
    ARRIVED(LocalTime.of(0, 10));

    private final LocalTime timeValue;

    FlightStatus(LocalTime timeValue) {
        this.timeValue = timeValue;
    }

    public LocalTime getTimeValue() {
        return timeValue;
    }


    static FlightStatus getDepartureFlightStatus(LocalTime departure) {

        LocalTime timeBeforeDeparture = departure.
                minusHours(LocalTime.now().getHour()).
                minusMinutes(LocalTime.now().getMinute());

        if (timeBeforeDeparture.compareTo(CHECK_IN.getTimeValue()) < 0 &&
                timeBeforeDeparture.compareTo(BOARDING.getTimeValue()) > 0) {
            return CHECK_IN;
        }

        if (timeBeforeDeparture.compareTo(BOARDING.getTimeValue()) < 0 &&
                timeBeforeDeparture.compareTo(GATE_CLOSED.getTimeValue()) > 0) {
            return BOARDING;
        }

        if (timeBeforeDeparture.compareTo(GATE_CLOSED.getTimeValue()) < 0 &&
                timeBeforeDeparture.compareTo(DEPARTED.getTimeValue()) > 0) {
            return GATE_CLOSED;
        }

        if (LocalTime.now().isAfter(departure)) {
            return DEPARTED;
        }
        return SCHEDULED;

    }

    static FlightStatus getArrivalFlightStatus(LocalTime arrival) {
        LocalTime timeLeft = arrival.
                minusHours(LocalTime.now().getHour()).
                minusMinutes(LocalTime.now().getMinute());

        if (timeLeft.compareTo(ARRIVED.getTimeValue()) > 0) {
            return ARRIVED;
        } else {
            return IN_FLIGHT;
        }
    }
}
