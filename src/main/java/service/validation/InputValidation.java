package service.validation;

import model.flight.Flight;
import model.passenger.Passenger;

/**
 * Input regular expressions validation
 */
public interface InputValidation {
    /**
     * Checking input flight number.
     * Should be 2 letters followed by 2-4 numbers.
     *
     * @param flightNumber - input flight number to be checked
     * @return is correct - true, not correct - false
     */
    boolean flightNumberIsCorrect(String flightNumber);

    /**
     * Checking input airline name.
     * Should be letters and spaces.
     *
     * @param name - input airline name
     * @return is correct - true, not correct - false
     */
    boolean airlineNameIsCorrect(String name);


    boolean nationalityIsCorrect(String name);

    /**
     * Checking input first or second name (Valid for both).
     * Should be letters only.
     *
     * @param name - input first or second name
     * @return - is correct - true, not correct - false
     */
    boolean nameIsCorrect(String name);

    /**
     * Checking input passport number.
     * Should be 2 letters followed by 6 numbers.
     *
     * @param passport - input passport number
     * @return - is correct - true, not correct - false
     */
    boolean passportNumberIaCorrect(String passport);

    /**
     * Checking input city name.
     * Should be letters only.
     *
     * @param name - input city name
     * @return - is correct - true, not correct - false
     */
    boolean cityNameIsCorrect(String name);

    /**
     * Checking input time.
     * Should be 'HH:MM:SS' format
     *
     * @param time - input time
     * @return - is correct - true, not correct - false
     */
    boolean timeIsCorrect(String time);

    /**
     * Checking input price.
     * Should be only numbers (can be decimal).
     *
     * @param price - input price
     * @return - is correct - true, not correct - false
     */
    boolean priceIsCorrect(String price);

    /**
     * Validate all fields
     *
     * @param flight - flight to be checked
     * @return - is correct - true, not correct - false
     */
    boolean flightIsCorrect (Flight flight);

    /**
     * alidate all fields
     *
     * @param passenger - passenger to be checked
     * @return - is correct - true, not correct - false
     */
    boolean passengerIsCorrect (Passenger passenger);



    /**
     * Shows alert with invalid input info
     *
     * @param message - message will be shown on alert
     */
    void showWarningAlert(String message);

    /**
     * Validate input passenger data from UI using methods already exist
     *
     * @param inputFirstName   - passenger first name from dialog text field
     * @param inputSecondName  - passenger second name from dialog text field
     * @param inputNationality - passenger first name from dialog text field
     * @param inputPassport    - passenger passport number from dialog text field
     * @return - all inputs are correct - true, are not correct - false
     */
    boolean inputPassengerForm(String inputFirstName, String inputSecondName, String inputNationality, String inputPassport);

    /**
     * Validate input flight data from UI using methods already exist
     *
     * @param flightNumber  - flight number from dialog text field
     * @param airline       - airline name from dialog text field
     * @param departure     - city of departure from dialog text field
     * @param departureTime - time of departure from dialog text field
     * @param arrival       - city of arrival from dialog text field
     * @param arrivalTime   -time of arrival from dialog text field
     * @return - all inputs are correct - true, are not correct - false
     */
    boolean inputFlightAddForm(String flightNumber, String airline,
                               String departure, String departureTime, String arrival, String arrivalTime);

    /**
     * Validate input prices data from UI using methods already exist
     *
     * @param economyFare - economy fare from dialog text field
     * @param businessFare - business fare from dialog text field
     * @return- all inputs are correct - true, are not correct - false
     */
    boolean inputPriceForm(String economyFare, String businessFare);
}
