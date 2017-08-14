package service.validation;

import javafx.scene.control.Alert;
import model.flight.Flight;
import model.passenger.Passenger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationImpl implements InputValidation {

    @Override
    public boolean nameIsCorrect(String name) {
        String test = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean nationalityIsCorrect(String nationality) {
        String test = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(nationality);
        return matcher.matches();
    }

    @Override
    public boolean passportNumberIaCorrect(String passport) {
        String test = "^[A-Za-z]{2}\\d{6}$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(passport);
        return matcher.matches();
    }

    @Override
    public boolean cityNameIsCorrect(String name) {
        String test = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean timeIsCorrect(String time) {
        String test = "^(\\d{2})([:]\\d{2}){1,2}$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    @Override
    public boolean priceIsCorrect(String price) {
        String test = "^[0-9.,]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    @Override
    public boolean flightNumberIsCorrect(String flightNumber) {
        String test = "^([A-Za-z]{2}\\d{2,4})$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(flightNumber);
        return matcher.matches();
    }

    @Override
    public boolean airlineNameIsCorrect(String name) {
        String test = "^[A-Za-z ]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean flightIsCorrect(Flight flight) {
        boolean numberIsCorrect = flightNumberIsCorrect(flight.getNumber());
        boolean airlineIsCorrect = airlineNameIsCorrect(flight.getAirline());
        boolean departureIsCorrect = cityNameIsCorrect(flight.getDepartureCity());
        boolean departureTimeIsCorrect = timeIsCorrect(flight.getTimeOfDeparture().toString());
        boolean departureTerminalIsCorrect = true;
        boolean arrivalIsCorrect = cityNameIsCorrect(flight.getArrivalCity());
        ;
        boolean arrivalTimeIsCorrect = timeIsCorrect(flight.getTimeOfArrival().toString());
        ;
        boolean arrivalTerminalIsCorrect = true;

        return numberIsCorrect && airlineIsCorrect &&
                departureIsCorrect && departureTimeIsCorrect && departureTerminalIsCorrect &&
                arrivalIsCorrect && arrivalTimeIsCorrect && arrivalTerminalIsCorrect;
    }

    @Override
    public boolean passengerIsCorrect(Passenger passenger){
        boolean flightNumber = flightNumberIsCorrect(passenger.getFlightNumber());
        boolean firstName = nameIsCorrect(passenger.getFirstName());
        boolean secondName = nameIsCorrect(passenger.getSecondName());
        boolean nationality = nationalityIsCorrect(passenger.getNationality());
        boolean passport = passportNumberIaCorrect(passenger.getPassport());

        return flightNumber && firstName && secondName && nationality && passport;
    }

    // the next must be deleted

    @Override
    public void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning input error");
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public boolean inputPassengerForm(String inputFirstName, String inputSecondName, String inputNationality, String inputPassport) {
        return nameIsCorrect(inputFirstName) &&
                nameIsCorrect(inputSecondName) &&
                nationalityIsCorrect(inputNationality) && passportNumberIaCorrect(inputPassport);
    }

    @Override
    public boolean inputFlightAddForm(String flightNumber, String airline,
                                      String departure, String departureTime, String arrival, String arrivalTime) {
        return flightNumberIsCorrect(flightNumber) && airlineNameIsCorrect(airline) &&
                cityNameIsCorrect(departure) && timeIsCorrect(departureTime) &&
                cityNameIsCorrect(arrival) && timeIsCorrect(arrivalTime);
    }

    @Override
    public boolean inputPriceForm(String economyFare, String businessFare) {
        return priceIsCorrect(economyFare) && priceIsCorrect(businessFare);
    }
}
