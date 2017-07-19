package controllers.price;

import controllers.AirportViewController;
import controllers.useraccess.permission.SearchController;
import controllers.useraccess.permission.ActionProxy;
import controllers.useraccess.permission.ReadWritePermissionInterface;
import controllers.validation.InputValidationController;
import database.FlightDAO;
import database.PriceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.flight.Flight;
import model.price.Price;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class PriceListViewController extends SearchController implements ReadWritePermissionInterface, PriceListView {

    private InputValidationController inputValidation = new InputValidationController();

    private ObservableList<Price> priceToShow = FXCollections.observableArrayList();
    private ObservableList<Price> showedList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TextField flightNumberPrice;
    @FXML
    private AnchorPane mainViewPane;
    @FXML
    private Label airlineName;
    @FXML
    private Label departureCity;
    @FXML
    private Label arrivalCity;
    @FXML
    private TableView<Price> priceList;
    @FXML
    private TableColumn<Object, Object> priceCabin;
    @FXML
    private TableColumn<Object, Object> fare;
    @FXML
    private HBox changeDeleteButtons;
    @FXML
    private Button changeButton;

    private static Price selectedPrice;

    static Price getSelectedPrice() {
        return selectedPrice;
    }

    public void initialize(){
        flightNumberPrice.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                showPriceList();
            }
        });
    }

    @FXML
    @Override
    public void showPriceList() {
        priceToShow.removeAll(showedList);

        String inputFlightNumber = flightNumberPrice.getText();

        if (inputValidation.flightNumberIsCorrect(inputFlightNumber) &&
                flightIsExist(inputFlightNumber)) {

            setFlightInfo(inputFlightNumber);

            List<Price> prices = getPriceListFromDB(inputFlightNumber);

            priceToShow.addAll(prices);

            priceCabin.setCellValueFactory(new PropertyValueFactory<>("cabin"));
            fare.setCellValueFactory(new PropertyValueFactory<>("fare"));

            priceList.setItems(priceToShow);
            showedList = priceToShow;

            mainViewPane.setVisible(true);
            priceList.setVisible(true);

            ReadWritePermissionInterface action = this;
            ReadWritePermissionInterface proxyAction = ActionProxy.newInstance(action);
            proxyAction.showButtons(AirportViewController.loggedUser);

        } else if (!inputValidation.flightNumberIsCorrect(inputFlightNumber)) {
            inputValidation.showWarningAlert("Flight Number is incorrect");
            mainViewPane.setVisible(false);
        } else if (!flightIsExist(inputFlightNumber)) {
            inputValidation.showWarningAlert("Flight Number not found");
            mainViewPane.setVisible(false);
        }
    }

    private List<Price> getPriceListFromDB(String flightNumber) {
        return PriceDAO.queryPriceListByFlightNumber(flightNumber);
    }

    private void setFlightInfo(String flightNumber) {
//        flightInfo.setVisible(true);
        Flight flight = FlightDAO.queryFlightByFlightNumber(flightNumber);
        airlineName.setText(flight.getAirline());
        departureCity.setText(flight.getDepartureCity());
        arrivalCity.setText(flight.getArrivalCity());
    }

    private boolean flightIsExist(String flightNumber) {
        return FlightDAO.flightIsExist(flightNumber);
    }

    @FXML
    private void changePrice() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAnchorPane.getScene().getWindow());
        dialog.setTitle("Change price");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/view/price/ChangePrice.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        ChangePriceController changeController;
        changeController = fxmlLoader.getController();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeController.changePrice();
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    @FXML
    private void handleMouseClick() {
        changeButton.setDisable(false);
        selectedPrice = priceList.getSelectionModel().getSelectedItem();
        selectedPrice.setFlightNumber(flightNumberPrice.getText());
    }

    @Override
    public void showButtons(User user) {
        changeDeleteButtons.setVisible(true);
    }

}
