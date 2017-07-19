package controllers.validation;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationController implements InputValidation {

    @Override
    public boolean passengerNameInputCheck(String name) {
        String test = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean passengerNationalityInputCheck(String nationality) {
        String test = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(nationality);
        return matcher.matches();
    }

    @Override
    public boolean passportNumberInputCheck(String passport) {
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
    public void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning input error");
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public boolean inputPassengerForm(String inputFirstName, String inputSecondName, String inputNationality, String inputPassport) {
        return passengerNameInputCheck(inputFirstName) &&
                passengerNameInputCheck(inputSecondName) &&
                passengerNationalityInputCheck(inputNationality) && passportNumberInputCheck(inputPassport);
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
