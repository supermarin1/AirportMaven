package controllers.flight;

import database.FlightDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.flight.Flight;
import model.flight.FlightStatus;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

/**
 * Flights arrival and departure view controller
 */
public class FlightsViewController implements FlightsView {

    private ObservableList<Flight> flightsToShow = FXCollections.observableArrayList();
    private ObservableList<Flight> showedFlights = FXCollections.observableArrayList();

    @FXML
    private Button departureButton;
    @FXML
    private Button arrivalButton;
    @FXML
    private TableView<Flight> departureFlightsTable;
    @FXML
    private TableColumn<Flight, LocalTime> timeOfDeparture;
    @FXML
    private TableColumn<Flight, String> airlineD;
    @FXML
    private TableColumn<Flight, String> numberD;
    @FXML
    private TableColumn<Flight, String> arrivalCity;
    @FXML
    private TableColumn<Flight, String> departureTerminal;
    @FXML
    private TableColumn<Flight, FlightStatus> statusD;
    @FXML
    private TableView<Flight> arrivalFlightsTable;
    @FXML
    private TableColumn<Flight, LocalTime> timeOfArrival;
    @FXML
    private TableColumn<Flight, String> airlineA;
    @FXML
    private TableColumn<Flight, String> numberA;
    @FXML
    private TableColumn<Flight, String> departureCity;
    @FXML
    private TableColumn<Flight, String> arrivalTerminal;
    @FXML
    private TableColumn<Flight, FlightStatus> statusA;

    /**
     * Shows departure flights by default
     */
    @FXML
    public void initialize() {
        showDepartureFlights();
        departureButton.setDisable(true);
    }

    /**
     * Shows departures or arrivals depends on button was clicked
     *
     * @param e - mouse click on
     */
    @FXML
    private void showFlights(ActionEvent e) {
        flightsToShow.removeAll(showedFlights);
        if (e.getSource().equals(departureButton)) {
            showDepartureFlights();
        } else if (e.getSource().equals(arrivalButton)) {
            showArrivalFlights();
        }
    }

    /**
     * Shows departure flights on the screen
     */
    private void showDepartureFlights() {
        List<Flight> flights = viewDepartures();
        flightsToShow.addAll(flights);

        timeOfDeparture.setCellValueFactory(new PropertyValueFactory<>("timeOfDeparture"));
        airlineD.setCellValueFactory(new PropertyValueFactory<>("airline"));
        numberD.setCellValueFactory(new PropertyValueFactory<>("number"));
        arrivalCity.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        departureTerminal.setCellValueFactory(new PropertyValueFactory<>("departureTerminal"));
        statusD.setCellValueFactory(new PropertyValueFactory<>("status"));

        departureFlightsTable.setItems(flightsToShow);
        showedFlights = flightsToShow;

        reverseButtons();

        departureFlightsTable.setVisible(true);
        arrivalFlightsTable.setVisible(false);
    }

    /**
     * Shows arrival flights on the screen
     */
    private void showArrivalFlights() {
        List<Flight> flights = viewArrivals();
        flightsToShow.addAll(flights);

        timeOfArrival.setCellValueFactory(new PropertyValueFactory<>("timeOfArrival"));
        airlineA.setCellValueFactory(new PropertyValueFactory<>("airline"));
        numberA.setCellValueFactory(new PropertyValueFactory<>("number"));
        departureCity.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        arrivalTerminal.setCellValueFactory(new PropertyValueFactory<>("arrivalTerminal"));
        statusA.setCellValueFactory(new PropertyValueFactory<>("status"));

        arrivalFlightsTable.setItems(flightsToShow);
        showedFlights = flightsToShow;

        reverseButtons();

        arrivalFlightsTable.setVisible(true);
        departureFlightsTable.setVisible(false);
    }

    @Override
    public List<Flight> viewDepartures() {
        List<Flight> departures = FlightDAO.queryDepartureFlights();
        if (departures != null) {
            departures.sort(Comparator.comparing(Flight::getTimeOfDeparture));
        }
        return departures;
    }

    @Override
    public List<Flight> viewArrivals() {
        List<Flight> arrivals = FlightDAO.queryArrivalFlights();
        if (arrivals != null) {
            arrivals.sort(Comparator.comparing(Flight::getTimeOfDeparture));
        }
        return arrivals;
    }

    private void reverseButtons() {
        if (departureButton.isDisable()) {
            departureButton.setDisable(false);
            arrivalButton.setDisable(true);
        } else {
            departureButton.setDisable(true);
            arrivalButton.setDisable(false);
        }
    }
}
