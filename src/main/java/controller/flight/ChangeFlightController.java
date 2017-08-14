package controller.flight;

import service.flight.FlightService;
import service.flight.FlightServiceImpl;
import service.validation.InputValidationImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.flight.Flight;
import model.flight.FlightStatus;

import java.util.ArrayList;
import java.util.List;

public class ChangeFlightController {

    private FlightService flightService = new FlightServiceImpl();

    private InputValidationImpl inputValidation = new InputValidationImpl();

    @FXML
    private Label flightNumber;
    @FXML
    private Label airline;
    @FXML
    private TextField departureFrom;
    @FXML
    private TextField timeOfDeparture;
    @FXML
    private TextField departureTerminal;
    @FXML
    private TextField arrivalTo;
    @FXML
    private TextField timeOfArrival;
    @FXML
    private TextField arrivalTerminal;
    @FXML
    private ComboBox<FlightStatus> cbxStatus;
    @FXML
    private Label errorLabel;

    private Flight oldFlight;

    public void initialize() {
        List<FlightStatus> statusCanBeChanged = new ArrayList<>();

        statusCanBeChanged.add(FlightStatus.CANCELED);
        statusCanBeChanged.add(FlightStatus.DELAYED);
        statusCanBeChanged.add(FlightStatus.SCHEDULED);

        cbxStatus.setItems(FXCollections.observableList(statusCanBeChanged));

        oldFlight = FlightSearchController.getSelectedFlight();
        setOldFlightInfo(oldFlight);

        inputFormatCheckListener();
    }

    private void setOldFlightInfo(Flight flight) {
        flightNumber.setText(flight.getNumber());
        airline.setText(flight.getAirline());
        departureFrom.setText(flight.getDepartureCity());
        timeOfDeparture.setText(String.valueOf(flight.getTimeOfDeparture()));
        departureTerminal.setText(flight.getDepartureTerminal());
        arrivalTo.setText(flight.getArrivalCity());
        timeOfArrival.setText(String.valueOf(flight.getTimeOfArrival()));
        arrivalTerminal.setText(flight.getArrivalTerminal());

        cbxStatus.setValue(FlightStatus.SCHEDULED);
    }

    void changeFlightInfo() {
        Flight changedFlight = getFlightFromDialog();
        if (changedFlight != null && !changedFlight.equals(oldFlight)) {
            flightService.changeFlight(changedFlight.getNumber(), changedFlight);
        }
    }

    private Flight getFlightFromDialog() {
        String inputDeparture = departureFrom.getText();
        String inputDepartureTime = timeOfDeparture.getText();
        String inputArrival = arrivalTo.getText();
        String inputArrivalTime = timeOfArrival.getText();

        if (inputValidation.cityNameIsCorrect(inputDeparture) &&
                inputValidation.timeIsCorrect(inputDepartureTime) &&
                inputValidation.cityNameIsCorrect(inputArrival) &&
                inputValidation.timeIsCorrect(inputArrivalTime)) {
            Flight inputFlight = new Flight();

            inputFlight.setNumber(oldFlight.getNumber());
            inputFlight.setAirline(oldFlight.getAirline());
            inputFlight.setDepartureCity(departureFrom.getText().trim());
            inputFlight.setTimeOfDeparture(timeOfDeparture.getText().trim());
            inputFlight.setDepartureTerminal(departureTerminal.getText().trim());
            inputFlight.setArrivalCity(arrivalTo.getText().trim());
            inputFlight.setTimeOfArrival(timeOfArrival.getText().trim());
            inputFlight.setArrivalTerminal(arrivalTerminal.getText().trim());
            inputFlight.setStatus(cbxStatus.getValue());

            return inputFlight;
        } else {
            if (errorLabel.isVisible()) {
                inputValidation.showWarningAlert("Failed." + errorLabel.getText());
            }
            return null;
        }
    }


    private void inputFormatCheckListener() {
        departureFrom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.cityNameIsCorrect(newValue)) {
                    showErrorLabel("Invalid city name");
                } else {
                    hideErrorLabel();
                }
            }
        });

        timeOfDeparture.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.timeIsCorrect(newValue)) {
                    showErrorLabel("Invalid departure time input");
                } else {
                    hideErrorLabel();
                }
            }
        });

        arrivalTo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.cityNameIsCorrect(newValue)) {
                    showErrorLabel("Invalid city name");
                } else {
                    hideErrorLabel();
                }
            }
        });

        timeOfArrival.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.timeIsCorrect(newValue)) {
                    showErrorLabel("Invalid arrival time input");
                } else {
                    hideErrorLabel();
                }
            }
        });
    }

    private void showErrorLabel(String message) {
        errorLabel.setVisible(true);
        errorLabel.setText(message);
        errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
    }

    private void hideErrorLabel() {
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }
}
