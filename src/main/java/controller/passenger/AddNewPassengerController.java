package controller.passenger;

import service.flight.FlightService;
import service.flight.FlightServiceImpl;
import service.passenger.PassengerService;
import service.passenger.PassengerServiceImpl;
import service.price.PriceService;
import service.price.PriceServiceImpl;
import service.validation.InputValidation;
import service.validation.InputValidationImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.passenger.Passenger;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


public class AddNewPassengerController {
    private FlightService flightService = new FlightServiceImpl();
    private PassengerService passengerService = new PassengerServiceImpl();
    private PriceService priceService = new PriceServiceImpl();

    private InputValidation inputValidation = new InputValidationImpl();

    @FXML
    private ComboBox<String> cbxFlightNumber;
    @FXML
    private TextField firstName;
    @FXML
    private TextField secondName;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton male;
    @FXML
    private TextField nationality;
    @FXML
    private TextField passport;
    @FXML
    public DatePicker birthday;
    @FXML
    private ToggleGroup cabinGroup;
    @FXML
    private RadioButton economy;
    @FXML
    private Label errorLabel;

    public void initialize() {
        List<String> flights = flightService.getFlightNumbers();
        if (flights != null) {
            cbxFlightNumber.setItems(FXCollections.observableList(flights));
            cbxFlightNumber.setValue(flights.get(1));
        }
        genderGroup.selectToggle(male);
        birthday.setValue(LocalDate.of(1900, Month.JANUARY, 1));
        cabinGroup.selectToggle(economy);

        inputFormCheckListener();
    }

    public void showAddNewPassengerView() {
        Passenger newPassenger = getPassengerFromDialog();
        if (newPassenger != null) {
            passengerService.addPassenger(newPassenger);
            inputValidation.showWarningAlert("Passenger added");
        } else {
            inputValidation.showWarningAlert("Passenger not added");
        }
    }

    private Passenger getPassengerFromDialog() {

        String inputFirstName = firstName.getText();
        String inputSecondName = secondName.getText();
        String inputNationality = nationality.getText();
        String inputPassport = passport.getText();

        if (inputValidation.inputPassengerForm(inputFirstName, inputSecondName, inputNationality, inputPassport)) {
            Passenger inputPassenger = new Passenger();

            inputPassenger.setFlightNumber(cbxFlightNumber.getValue());

            inputPassenger.setFirstName(firstName.getText());
            inputPassenger.setSecondName(secondName.getText());

            RadioButton tempGender = (RadioButton) genderGroup.getSelectedToggle();
            inputPassenger.setGender(tempGender.getText().trim());

            inputPassenger.setNationality(nationality.getText());
            inputPassenger.setPassport(passport.getText());

            LocalDate inputBDay = birthday.getValue();
            inputPassenger.setDateOfBirth(LocalDate.of(
                    inputBDay.getYear(), inputBDay.getMonth(), inputBDay.getDayOfMonth()));

            RadioButton tempCabin = (RadioButton) cabinGroup.getSelectedToggle();

            inputPassenger.setCabin(tempCabin.getText().trim());
            inputPassenger.setFare(priceService.getFare(
                    inputPassenger.getFlightNumber(), inputPassenger.getCabin()));

            return inputPassenger;
        } else {
            return null;
        }
    }

    private void inputFormCheckListener() {

        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.nameIsCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid first name");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        secondName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.nameIsCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid second name");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        nationality.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.nationalityIsCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid nationality");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        passport.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.passportNumberIaCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid passport number");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });
    }
}
