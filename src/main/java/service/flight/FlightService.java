package service.flight;

import model.flight.Flight;
import java.util.List;

public interface FlightService {

    /**
     * Appeals to database layer to get view of departures in chronological order
     * @return list of departure flight from database
     */
    List<Flight> getDepartures();

    /**
     * Appeals to database layer to get view of arrivals in chronological order
     * @return list of arrivals flight from database
     */
    List<Flight> getArrivals();

    Flight getFlight(String flightNumber);

    List<Flight> searchFlightBy(String flightNumber, String departure, String arrival);

    void addFlight(Flight newFlight);

    void changeFlight(String flightNumber, Flight changedFlight);

    void deleteFlight(String flightNumber);

    boolean flightIsExist(String flightNumber);

    List<String> getFlightNumbers();
}
