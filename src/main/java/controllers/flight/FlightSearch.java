package controllers.flight;

import javafx.collections.ObservableList;
import model.flight.Flight;

public interface FlightSearch {

    void searchFlight();
    ObservableList<Flight> getFlightsFromDB(String flightNum, String departure, String arrival);

}
