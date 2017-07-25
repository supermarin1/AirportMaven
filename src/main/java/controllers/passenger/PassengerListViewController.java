package controllers.passenger;

import controllers.AirportViewController;
import controllers.useraccess.permission.SearchController;
import controllers.useraccess.permission.ActionProxy;
import controllers.useraccess.permission.ReadWritePermissionInterface;
import controllers.validation.InputValidationController;
import database.FlightDAO;
import database.PassengerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.flight.Cabin;
import model.passenger.Gender;
import model.passenger.Passenger;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PassengerListViewController
        extends SearchController
        implements ReadWritePermissionInterface, PassengerListView, AddNewPassenger, ChangePassenger, DeletePassenger {
    private InputValidationController inputValidation = new InputValidationController();

    private ObservableList<Passenger> passengersToShow = FXCollections.observableArrayList();
    private ObservableList<Passenger> showedPassengers = FXCollections.observableArrayList();

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TextField flightNumberPax;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableView<Passenger> passengerList;
    @FXML
    private TableColumn<Passenger, String> firstName;
    @FXML
    private TableColumn<Passenger, String> secondName;
    @FXML
    private TableColumn<Passenger, Gender> gender;
    @FXML
    private TableColumn<Passenger, String> nationality;
    @FXML
    private TableColumn<Passenger, String> passport;
    @FXML
    private TableColumn<Passenger, String> dateOfBirth;
    @FXML
    private TableColumn<Passenger, Cabin> cabin;
    @FXML
    private HBox changeDeleteButtons;
    @FXML
    private Button changeButton;
    @FXML
    private Button deleteButton;

    private static Passenger selectedPassenger;

    static Passenger getSelectedPassenger() {
        return selectedPassenger;
    }

    public void initialize(){
        flightNumberPax.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                showPassengerListOfFlight();
            }
        });
    }

    @FXML
    public void showPassengerListOfFlight() {
        changeButton.setDisable(true);
        deleteButton.setDisable(true);
        mainPane.setVisible(true);
        passengerList.setVisible(true);
        passengersToShow.removeAll(showedPassengers);

        String inputFlightNumber = flightNumberPax.getText();

        if (inputValidation.flightNumberIsCorrect(inputFlightNumber) &&
                flightIsExist(inputFlightNumber)) {

            List<Passenger> passengers = viewPassengerList(inputFlightNumber);

            passengersToShow.addAll(passengers);

            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
            gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
            passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
            dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            cabin.setCellValueFactory(new PropertyValueFactory<>("cabin"));

            passengerList.setItems(passengersToShow);
            showedPassengers = passengersToShow;

            passengerList.setVisible(true);

            ReadWritePermissionInterface action = this;
            ReadWritePermissionInterface proxyAction = ActionProxy.newInstance(action);
            proxyAction.showButtons(AirportViewController.loggedUser);

        } else if (!inputValidation.flightNumberIsCorrect(inputFlightNumber)) {
            inputValidation.showWarningAlert("Incorrect flight number.");
            passengerList.setVisible(false);

        } else if (!flightIsExist(inputFlightNumber)) {
            inputValidation.showWarningAlert("There is no such flight.");
            passengerList.setVisible(false);
        }
    }

    private List<Passenger> viewPassengerList(String flightNumber) {
        return PassengerDAO.queryPassengersByFlightNumber(flightNumber);
    }

    private boolean flightIsExist(String flightNumber) {
        return FlightDAO.flightIsExist(flightNumber);
    }

    @Override
    public void addNewPassenger() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAnchorPane.getScene().getWindow());
        dialog.setTitle("Add new passenger");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/passenger/AddNewPassenger.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        AddNewPassengerController addController;
        addController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addController.addNewPassenger();
        }
    }

    public void changePassengerInfo() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAnchorPane.getScene().getWindow());
        dialog.setTitle("Change passenger");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/passenger/ChangePassengerInfo.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        ChangePassengerController changeController;
        changeController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeController.changePassengerInfo();
        }
    }

    public void deletePassenger() {
        selectedPassenger = passengerList.getSelectionModel().getSelectedItem();
        PassengerDAO.deletePassengerFromDB(selectedPassenger.getPassport());
    }

    public void handleMouseClick() {
        changeButton.setDisable(false);
        deleteButton.setDisable(false);
        selectedPassenger = passengerList.getSelectionModel().getSelectedItem();
        selectedPassenger.setFlightNumber(flightNumberPax.getText());
    }

    @Override
    public void showButtons(User user) {
        changeDeleteButtons.setVisible(true);
    }
}
