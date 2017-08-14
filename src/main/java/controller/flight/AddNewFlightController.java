package controller.flight;

import service.airline.AirlineService;
import service.airline.AirlineServiceImpl;
import service.flight.FlightService;
import service.flight.FlightServiceImpl;
import service.price.PriceService;
import service.price.PriceServiceImpl;
import service.validation.InputValidationImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.flight.Cabin;
import model.flight.Flight;
import model.price.Price;

import java.util.ArrayList;
import java.util.List;

public class AddNewFlightController{
    private AirlineService airlineService = new AirlineServiceImpl();
    private FlightService flightService = new FlightServiceImpl();
    private PriceService priceService = new PriceServiceImpl();

    private InputValidationImpl inputValidation = new InputValidationImpl();

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField flightNumber;
    @FXML
    private ComboBox<String> cbxAirlines;
    @FXML
    private TextField airline;
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
    private TextField economyFare;
    @FXML
    private TextField businessFare;
    @FXML
    private Label errorLabel;

    public void initialize() {
        List<String> airlines = airlineService.getAirlines();
        if (airlines != null) {
            cbxAirlines.setItems(FXCollections.observableList(airlines));
            cbxAirlines.setValue(airlines.get(1));
        }
        inputFormatCheckListener();
    }

    public void addNewFlight() {
        Flight newFlight = getFlightFromDialog();
        List<Price> newPriceList = getPriceListFromDialog();

        if ((newPriceList != null) && (newFlight != null)) {
            flightService.addFlight(newFlight);
            priceService.addPriceList(newFlight.getNumber(), newPriceList);
        }
    }

    private Flight getFlightFromDialog() {
        String inputFlightNumber = flightNumber.getText().toUpperCase();
        String inputAirline;
        if (airline.getText().isEmpty()) {
            inputAirline = cbxAirlines.getValue();
        } else {
            airlineService.addNewAirline(airline.getText().toUpperCase());
            inputAirline = airline.getText();
        }
        String inputDeparture = departureFrom.getText();
        String inputDepartureTime = timeOfDeparture.getText();
        String inputArrival = arrivalTo.getText();
        String inputArrivalTime = timeOfArrival.getText();

        if (inputValidation.inputFlightAddForm(inputFlightNumber, inputAirline, inputDeparture,
                inputDepartureTime, inputArrival, inputArrivalTime)) {
            Flight inputFlight = new Flight();

            inputFlight.setNumber(inputFlightNumber);
            inputFlight.setAirline(inputAirline);
            inputFlight.setDepartureCity(inputDeparture);
            inputFlight.setTimeOfDeparture(inputDepartureTime);
            inputFlight.setDepartureTerminal(departureTerminal.getText());
            inputFlight.setArrivalCity(inputArrival);
            inputFlight.setTimeOfArrival(inputArrivalTime);
            inputFlight.setArrivalTerminal(arrivalTerminal.getText());
            return inputFlight;
        } else {
            return null;
        }
    }

    private List<Price> getPriceListFromDialog() {
        String inputEconomyFare = economyFare.getText();
        String inputBusinessFare = businessFare.getText();

        if (inputValidation.inputPriceForm(inputEconomyFare, inputBusinessFare)) {
            List<Price> inputPrices = new ArrayList<>();

            Price economyPrice = new Price();
            economyPrice.setFlightNumber(flightNumber.getText().toUpperCase());
            economyPrice.setCabin(Cabin.ECONOMY);
            economyPrice.setFare(Integer.valueOf(inputEconomyFare));

            Price businessPrice = new Price();
            businessPrice.setFlightNumber(flightNumber.getText().toUpperCase());
            businessPrice.setCabin(Cabin.BUSINESS);
            businessPrice.setFare(Integer.valueOf(inputBusinessFare));

            inputPrices.add(economyPrice);
            inputPrices.add(businessPrice);

            return inputPrices;
        } else {
            return null;
        }
    }

    private void inputFormatCheckListener() {
        flightNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.flightNumberIsCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid flight number");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        airline.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbxAirlines.setDisable(true);
                if (!inputValidation.airlineNameIsCorrect(newValue)) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid airline name");
                    errorLabel.setTextFill(Paint.valueOf(String.valueOf(Color.RED)));
                    if (newValue.isEmpty()) {
                        cbxAirlines.setDisable(false);
                        errorLabel.setVisible(false);
                    }
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

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

        economyFare.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.priceIsCorrect(newValue)) {
                    showErrorLabel("Invalid economy fare input");
                } else {
                    hideErrorLabel();
                }
            }
        });

        businessFare.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!inputValidation.priceIsCorrect(newValue)) {
                    showErrorLabel("Invalid business fare input");
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
