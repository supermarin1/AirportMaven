package controllers;

import controllers.flight.AddNewFlightController;
import controllers.passenger.AddNewPassengerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import model.user.User;
import controllers.useraccess.permission.MainMenuLoginInterface;
import controllers.useraccess.permission.ActionProxy;
import controllers.useraccess.permission.MyPermission;
import controllers.useraccess.permission.PermissionAction;
import controllers.useraccess.LoginDialogController;

import java.io.IOException;
import java.util.Optional;

/**
 * MenuBar and main view controller.
 */

public class AirportViewController implements AirportView, MainMenuLoginInterface {
    public static User loggedUser;
    public static String searchPassengerBy = null;

    @FXML
    private Menu flightsMenu;
    @FXML
    private Menu passengersMenu;
    @FXML
    private Menu logoutMenu;
    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void initialize() throws IOException {
        showFlightMainMenu();
    }

    /**
     * Shows on the main scene flight menu (arrivals/departures)
     *
     */
    @FXML
    private void showFlightMainMenu() {
        Node flightNode = null;
        try {
            flightNode = FXMLLoader.load(getClass().getResource("/fxml/view/flight/FlightsView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(flightNode);
    }

    /**
     * Shows new dialog pane to log into the system for company staff
     */
    @FXML
    public void showLoginDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAnchorPane.getScene().getWindow());
        dialog.setTitle("Login into the system");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/login/LoginDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        LoginDialogController loginDialogController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            login(loginDialogController);
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    private void login(LoginDialogController loginDialogController) {
        loggedUser = loginDialogController.loginIntoTheSystem();

        MainMenuLoginInterface action = this;
        MainMenuLoginInterface proxyAction = ActionProxy.newInstance(action);
        proxyAction.showMenu(loggedUser);
    }

    @Override
    @MyPermission(value = {PermissionAction.ADMIN, PermissionAction.READ_WRITE, PermissionAction.READ_WRITE})
    public void showMenu(User user) {
        flightsMenu.setVisible(true);
        passengersMenu.setVisible(true);
        logoutMenu.setVisible(true);
    }

    /**
     * Shows on the main scene flight search window
     */
    public void searchFlight() {
        Node flightNode = null;
        try {
            flightNode = FXMLLoader.load(getClass().getResource("/fxml/view/flight/FlightSearch.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(flightNode);
    }

    @Override
    public void showPassengerListMenu() {
        Node flightNode = null;
        try {
            flightNode = FXMLLoader.load(getClass().getResource("/fxml/view/passenger/PassengerListView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(flightNode);
    }

    @Override
    public void showPriceListMenu() {
        Node priceNode = null;
        try {
            priceNode = FXMLLoader.load(getClass().getResource("/fxml/view/price/PriceListView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(priceNode);
    }

    public void searchPaxByName() {
        searchPassengerBy = "name";
        Node passengerNode = null;
        try {
            passengerNode = FXMLLoader.load(getClass().getResource("/fxml/view/passenger/PassengerSearch.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(passengerNode);
    }

    public void searchByPassport() {
        searchPassengerBy = "passport";
        Node passengerNode = null;
        try {
            passengerNode = FXMLLoader.load(getClass().getResource("/fxml/view/passenger/PassengerSearch.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(passengerNode);
    }

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

        AddNewPassengerController addController = fxmlLoader.getController();
        addController.initialize();

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            addController.addNewPassenger();
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void addNewFlight() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAnchorPane.getScene().getWindow());
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

        AddNewFlightController addFlightController;
        addFlightController = fxmlLoader.getController();

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            addFlightController.addNewFlight();
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void logout() throws IOException {
        loggedUser = null;
        showFlightMainMenu();
        flightsMenu.setVisible(false);
        passengersMenu.setVisible(false);
        logoutMenu.setVisible(false);
    }
}
