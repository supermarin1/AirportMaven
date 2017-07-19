package controllers.flight;

import model.flight.Flight;

import java.util.List;


/**
 * An interface to get views of arrivals and departure flights.
 * Responsible for connecting UI requests and DB.
 */
public interface FlightsView {
    /**
     * Appeals to database layer to get view of departures in chronological order
     * @return list of departure flight from database
     */
    List<Flight> viewDepartures();

    /**
     * Appeals to database layer to get view of arrivals in chronological order
     * @return list of arrivals flight from database
     */
    List<Flight> viewArrivals();
}
