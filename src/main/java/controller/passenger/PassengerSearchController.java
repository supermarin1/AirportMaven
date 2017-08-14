package controller.passenger;

import controller.AirportViewController;
import service.passenger.PassengerService;
import service.passenger.PassengerServiceImpl;
import service.useraccess.permission.SearchController;
import service.useraccess.permission.ActionProxy;
import service.useraccess.permission.ReadWritePermissionInterface;
import service.validation.InputValidationImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.flight.Cabin;
import model.passenger.Gender;
import model.passenger.Passenger;
import model.user.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PassengerSearchController extends SearchController implements
        ReadWritePermissionInterface{

    private PassengerService passengerService = new PassengerServiceImpl();

    private InputValidationImpl inputValidation = new InputValidationImpl();

    private ObservableList<Passenger> passengersToShow = FXCollections.observableArrayList();
    private ObservableList<Passenger> showedPassengers = FXCollections.observableArrayList();

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane mainViewPane;
    @FXML
    private HBox searchPassengerListByName;
    @FXML
    private TextField passengerFirstName;
    @FXML
    private TextField passengerSecondName;
    @FXML
    private HBox searchPassengerListByPassport;
    @FXML
    private TextField passengerPassport;
    @FXML
    private TableView<Passenger> passengersTable;
    @FXML
    private TableColumn<Passenger, String> flightNumber;
    @FXML
    private TableColumn<Passenger, String> route;
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
    private TableColumn<Passenger, LocalDate> dateOfBirth;
    @FXML
    private TableColumn<Passenger, Cabin> cabin;
    @FXML
    private TableColumn<Passenger, Integer> fare;
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

    @FXML
    public void initialize() {
        if (AirportViewController.searchPassengerBy.equals("name")) {
            searchPassengerListByName.setVisible(true);
        } else if (AirportViewController.searchPassengerBy.equals("passport")) {
            searchPassengerListByPassport.setVisible(true);
        }
    }

    @FXML
    private void showPassengerList() {
        mainViewPane.setVisible(true);
        passengersToShow.removeAll(showedPassengers);

        if (AirportViewController.searchPassengerBy.equals("name")) {
            String inputFirstName = passengerFirstName.getText();
            String inputSecondName = passengerSecondName.getText();

            searchPassengerListByName(inputFirstName, inputSecondName);
        }
        if (AirportViewController.searchPassengerBy.equals("passport")) {
            String inputPassport = passengerPassport.getText();

            searchPassengerListByPassport(inputPassport);
        }
    }

    private void searchPassengerListByName(String inputFirstName, String inputSecondName) {
        searchPassengerListByName.setVisible(true);
        searchPassengerListByPassport.setVisible(false);

        boolean firstNameIsEmpty = inputFirstName.isEmpty();
        boolean secondNameIsEmpty = inputSecondName.isEmpty();

        boolean firstNameIsCorrect = inputValidation.nameIsCorrect(inputFirstName);
        boolean secondNameIsCorrect = inputValidation.nameIsCorrect(inputSecondName);

        boolean searchIsAllowed = (firstNameIsEmpty || firstNameIsCorrect) &&
                (secondNameIsEmpty || secondNameIsCorrect) &&
                !(firstNameIsEmpty && secondNameIsEmpty );

        if (searchIsAllowed) {
            passengersToShow = searchPassengerByName(inputFirstName, inputSecondName);
            if (!passengersToShow.isEmpty()) {
                showPassengersData();
            } else {
                inputValidation.showWarningAlert("There is no such passenger.");
                passengersTable.setVisible(false);
            }
        } else {
            inputValidation.showWarningAlert("Invalid input name. Pls, enter valid data");
        }
    }

    private void searchPassengerListByPassport(String inputPassport) {
        searchPassengerListByName.setVisible(false);
        searchPassengerListByPassport.setVisible(true);

        passengersToShow = searchPassengerByPassport(inputPassport);

        if (!passengersToShow.isEmpty()) {
            showPassengersData();
        }
    }

    private void showPassengersData() {
        flightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        route.setCellValueFactory(new PropertyValueFactory<>("route"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        cabin.setCellValueFactory(new PropertyValueFactory<>("cabin"));
        fare.setCellValueFactory(new PropertyValueFactory<>("fare"));

        passengersTable.setItems(passengersToShow);
        showedPassengers = passengersToShow;

        passengersTable.setVisible(true);

        ReadWritePermissionInterface action = this;
        ReadWritePermissionInterface proxyAction = ActionProxy.newInstance(action);
        proxyAction.showButtons(AirportViewController.loggedUser);
    }

    private ObservableList<Passenger> searchPassengerByName(String firstName, String secondName) {
        List<Passenger> passengers = passengerService.searchPassengerByName(firstName,secondName);
        if (passengers != null) {
            passengersToShow.addAll(passengers);
        }
        return passengersToShow;
    }

    private ObservableList<Passenger> searchPassengerByPassport(String passport) {
        List<Passenger> passengers = passengerService.searchPassengerByPassport(passport);
        if (passengers != null) {
            passengersToShow.addAll(passengers);
        }
        return passengersToShow;
    }


    @FXML
    public void showAddNewPassengerView() {
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
            addController.showAddNewPassengerView();
        }
    }

    @FXML
    public void showChangePassengerInfoView() {
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
            changeController.showChangePassengerInfoView();
        }
    }

    @FXML
    public void deletePassenger() {
        selectedPassenger = passengersTable.getSelectionModel().getSelectedItem();
        passengerService.deletePassenger(selectedPassenger.getPassport());
    }

    @FXML
    private void handleMouseClick() {
        changeButton.setDisable(false);
        deleteButton.setDisable(false);
        selectedPassenger = passengersTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void showButtons(User user) {
        changeDeleteButtons.setVisible(true);
    }
}
