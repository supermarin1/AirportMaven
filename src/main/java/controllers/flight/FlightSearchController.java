package controllers.flight;

import controllers.AirportViewController;
import controllers.useraccess.permission.SearchController;
import controllers.useraccess.permission.*;
import controllers.validation.InputValidationController;
import database.FlightDAO;
import database.PriceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.flight.Flight;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FlightSearchController
        extends SearchController
        implements ReadWritePermissionInterface, FlightSearch {

    private InputValidationController inputValidation = new InputValidationController();
    private ObservableList<Flight> flightsToShow = FXCollections.observableArrayList();
    private ObservableList<Flight> showedFlights = FXCollections.observableArrayList();

    @FXML
    private AnchorPane mainViewPane;
    @FXML
    private TextField flightNumber;
    @FXML
    private TextField cityFrom;
    @FXML
    private TextField cityTo;
    @FXML
    private TableView<Flight> flightsTable;
    @FXML
    private TableColumn<Flight, String> airline;
    @FXML
    private TableColumn<Flight, String> number;
    @FXML
    private TableColumn<Flight, String> departure;
    @FXML
    private TableColumn<Flight, String> arrival;
    @FXML
    private TableColumn<Flight, String> departureTime;
    @FXML
    private TableColumn<Flight, String> departureTerminal;
    @FXML
    private TableColumn<Flight, String> arrivalTime;
    @FXML
    private TableColumn<Flight, String> arrivalTerminal;
    @FXML
    private TableColumn<Flight, String> status;
    @FXML
    private HBox changeDeleteButtons;
    @FXML
    private Button changeButton;
    @FXML
    private Button deleteButton;

    private static Flight selectedFlight;

    static Flight getSelectedFlight() {
        return selectedFlight;
    }

    @Override
    public void searchFlight() {
        mainViewPane.setVisible(true);
        flightsToShow.removeAll(showedFlights);

        String inputFlightNumber = flightNumber.getText();
        String inputDeparture = cityFrom.getText();
        String inputArrival = cityTo.getText();

        boolean flightNumIsEmpty = inputFlightNumber.isEmpty();
        boolean departureIsEmpty = inputDeparture.isEmpty();
        boolean arrivalIsEmpty = inputArrival.isEmpty();

        boolean flightIsCorrect = inputValidation.flightNumberIsCorrect(inputFlightNumber);
        boolean departureIsCorrect = inputValidation.cityNameIsCorrect(inputDeparture);
        boolean arrivalIsCorrect = inputValidation.cityNameIsCorrect(inputArrival);

        boolean searchIsAllowed = (flightNumIsEmpty || flightIsCorrect) &&
                (departureIsEmpty || departureIsCorrect) && (arrivalIsEmpty || arrivalIsCorrect);

        if (searchIsAllowed) {
            flightsToShow = getFlightsFromDB(inputFlightNumber, inputDeparture, inputArrival);
        } else {
            inputValidation.showWarningAlert("Invalid input search parameters");
            mainViewPane.setVisible(false);
        }

        addFlightsToTableOnView();

        flightsTable.setVisible(true);

        ReadWritePermissionInterface action = this;
        ReadWritePermissionInterface proxyAction = ActionProxy.newInstance(action);
        proxyAction.showButtons(AirportViewController.loggedUser);
    }

    @Override
    public ObservableList<Flight> getFlightsFromDB(String flightNum, String departure, String arrival) {
        System.out.println("we're in method");

        List<Flight> flights = FlightDAO.queryFlightsByCities(flightNum, departure, arrival);
        if (flights != null) {
            flightsToShow.addAll(flights);
        }
        return flightsToShow;
    }

    private void addFlightsToTableOnView() {

        airline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        departure.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        arrival.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        departureTime.setCellValueFactory(new PropertyValueFactory<>("timeOfDeparture"));
        departureTerminal.setCellValueFactory(new PropertyValueFactory<>("departureTerminal"));
        arrivalTime.setCellValueFactory(new PropertyValueFactory<>("timeOfArrival"));
        arrivalTerminal.setCellValueFactory(new PropertyValueFactory<>("arrivalTerminal"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        flightsTable.setItems(flightsToShow);

        showedFlights = flightsToShow;
    }

    public void addNewFlight() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainViewPane.getScene().getWindow());
        dialog.setTitle("Add new flight");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/flight/AddNewFlight.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        AddNewFlightController addFlightController;
        addFlightController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addFlightController.addNewFlight();
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void changeFlightInfo() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainViewPane.getScene().getWindow());
        dialog.setTitle("Change flight");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/flight/ChangeFlightInfo.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        ChangeFlightController changeController;
        changeController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeController.changeFlightInfo();
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void deleteFlight() {
        selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
        FlightDAO.deleteFlightFromDB(selectedFlight.getNumber());
    }

    @FXML
    private void handleMouseClick() {
        changeButton.setDisable(false);
        deleteButton.setDisable(false);
        selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void showButtons(User user) {
        changeDeleteButtons.setVisible(true);
    }
}
