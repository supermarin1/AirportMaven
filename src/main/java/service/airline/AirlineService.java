package service.airline;

import model.airline.Airline;

import java.util.List;

public interface AirlineService {
    List<String> getAirlines();
    void addNewAirline(String airlineName);
}
