package controllers.passenger;

import controllers.validation.InputValidation;
import controllers.validation.InputValidationController;
import database.FlightDAO;
import database.PassengerDAO;
import database.PriceDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.passenger.Passenger;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


public class AddNewPassengerController implements AddNewPassenger {

    private InputValidation inputValidation = new InputValidationController();

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
        List<String> flights = FlightDAO.getFlightNumbersOnly();
        if (flights != null) {
            cbxFlightNumber.setItems(FXCollections.observableList(flights));
            cbxFlightNumber.setValue(flights.get(1));
        }
        genderGroup.selectToggle(male);
        birthday.setValue(LocalDate.of(1900, Month.JANUARY, 1));
        cabinGroup.selectToggle(economy);

        inputFormCheckListener();
    }

    @Override
    public void addNewPassenger() {
        Passenger newPassenger = getPassengerFromDialog();
        if (newPassenger != null) {
            PassengerDAO.addNewPassengerToDB(newPassenger);
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
            inputPassenger.setFare(PriceDAO.getPrice(
                    inputPassenger.getFlightNumber(), inputPassenger.getCabin()));
            return inputPassenger;
        } else {
            return null;
        }
    }

    private void inputFormCheckListener() {

        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.passengerNameInputCheck(newValue)) {
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
                if (!inputValidation.passengerNameInputCheck(newValue)) {
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
                if (!inputValidation.passengerNationalityInputCheck(newValue)) {
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
                if (!inputValidation.passportNumberInputCheck(newValue)) {
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
