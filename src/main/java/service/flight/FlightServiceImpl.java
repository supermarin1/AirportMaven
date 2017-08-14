package service.flight;

import database.FlightDAO;
import model.flight.Flight;
import service.validation.InputValidationImpl;

import java.util.Comparator;
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private InputValidationImpl inputValidation = new InputValidationImpl();

    @Override
    public List<Flight> getDepartures() {
        List<Flight> departures = FlightDAO.queryDepartureFlights();
        if (departures != null) {
            departures.sort(Comparator.comparing(Flight::getTimeOfDeparture));
        }
        return departures;
    }

    @Override
    public List<Flight> getArrivals() {
        List<Flight> arrivals = FlightDAO.queryArrivalFlights();
        if (arrivals != null) {
            arrivals.sort(Comparator.comparing(Flight::getTimeOfDeparture));
        }
        return arrivals;
    }

    @Override
    public Flight getFlight(String flightNumber) {
        Flight flight = null;
        if(inputValidation.flightNumberIsCorrect(flightNumber)){
            flight = FlightDAO.queryFlightByFlightNumber(flightNumber);
        }
        return flight;
    }

    @Override
    public List<Flight> searchFlightBy(String flightNumber, String departure, String arrival) {
        List<Flight> flights = null;

        boolean flightIsCorrect = inputValidation.flightNumberIsCorrect(flightNumber);
        boolean departureIsCorrect = inputValidation.cityNameIsCorrect(departure);
        boolean arrivalIsCorrect = inputValidation.cityNameIsCorrect(arrival);

        boolean searchIsAllowed = (flightNumber.isEmpty() || flightIsCorrect) &&
                (departure.isEmpty() || departureIsCorrect) && (arrival.isEmpty() || arrivalIsCorrect);

        if (searchIsAllowed) {
            flights = FlightDAO.queryFlightsByCities(flightNumber, departure, arrival);
        }

        if (flights != null) {
            flights.sort(Comparator.comparing(Flight::getTimeOfDeparture));
        }
        return flights;
    }

    @Override
    public void addFlight(Flight newFlight) {
        if (inputValidation.flightIsCorrect(newFlight)) {
            FlightDAO.addNewFlightToDB(newFlight);
        }

    }

    @Override
    public void changeFlight(String flightNumber, Flight changedFlight) {
        if (inputValidation.flightIsCorrect(changedFlight)) {
            Flight oldFlight = FlightDAO.queryFlightByFlightNumber(flightNumber);

            if (flightNumber.equals(changedFlight.getNumber()) && !changedFlight.equals(oldFlight)) {
                FlightDAO.changeFlightDataInDB(changedFlight.getNumber(), changedFlight);
            }
        }
    }

    @Override
    public void deleteFlight(String flightNumber) {
        if (inputValidation.flightNumberIsCorrect(flightNumber)) {
            FlightDAO.deleteFlightFromDB(flightNumber);
        }
    }

    @Override
    public boolean flightIsExist(String flightNumber) {
        boolean isExist = false;
        if (inputValidation.flightNumberIsCorrect(flightNumber)) {
            isExist = FlightDAO.flightIsExist(flightNumber);
        }
        return isExist;
    }

    @Override
    public List<String> getFlightNumbers() {
        return FlightDAO.getFlightNumbersOnly();
    }

}

