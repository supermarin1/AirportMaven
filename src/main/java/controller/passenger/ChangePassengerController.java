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
import model.flight.Cabin;
import model.passenger.Gender;
import model.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

public class ChangePassengerController {
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
    private RadioButton female;
    @FXML
    private TextField nationality;
    @FXML
    private TextField passport;
    @FXML
    private DatePicker birthday;
    @FXML
    private ToggleGroup cabinGroup;
    @FXML
    private RadioButton business;
    @FXML
    private RadioButton economy;
    @FXML
    private Label errorLabel;

    private Passenger oldPassenger;

    public void initialize() {
        List<String> flights = flightService.getFlightNumbers();

        if (flights != null) {
            cbxFlightNumber.setItems(FXCollections.observableList(flights));
        }

        oldPassenger = PassengerListViewController.getSelectedPassenger();
        if (oldPassenger == null) {
            oldPassenger = PassengerSearchController.getSelectedPassenger();
        }

        setOldPassengerInfo(oldPassenger);

        inputFormCheckListener();
    }

    private void setOldPassengerInfo(Passenger oldPassenger) {
        cbxFlightNumber.setValue(oldPassenger.getFlightNumber());

        firstName.setText(oldPassenger.getFirstName());
        secondName.setText(oldPassenger.getSecondName());

        if (oldPassenger.getGender().equals(Gender.valueOf(male.getText()))) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        nationality.setText(oldPassenger.getNationality());
        passport.setText(oldPassenger.getPassport());
        birthday.setValue(oldPassenger.getDateOfBirth());

        if (oldPassenger.getCabin().equals(Cabin.valueOf(economy.getText()))) {
            economy.setSelected(true);
        } else {
            business.setSelected(true);
        }
    }

    void showChangePassengerInfoView() {
        Passenger changedPassenger;
        changedPassenger = readPassengerFromDialog();

        if (changedPassenger != null && !changedPassenger.equals(oldPassenger)) {
            passengerService.changePassenger(oldPassenger.getPassport(), changedPassenger);
        }
    }

    private Passenger readPassengerFromDialog() {

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
