package controllers.price;

import controllers.validation.InputValidationController;
import database.PriceDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.price.Price;

public class ChangePriceController implements ChangePrice {

    private InputValidationController inputValidation = new InputValidationController();

    @FXML
    private Label cabin;
    @FXML
    private TextField fare;

    private Price oldPrice;

    public void initialize() {
        oldPrice = PriceListViewController.getSelectedPrice();
        cabin.setText(oldPrice.getCabin().toString());
        fare.setText(oldPrice.getFare().toString());
    }

    @Override
    public void changePrice() {
        Price changedPrice = getChangedPriceFromDialog();

        if (!changedPrice.equals(oldPrice)) {
            PriceDAO.changePriceInDB(changedPrice);
        }
    }

    private Price getChangedPriceFromDialog() {
        Price changedPrice = new Price();
        changedPrice.setFlightNumber(oldPrice.getFlightNumber());
        changedPrice.setCabin(oldPrice.getCabin());

        String inputFare = fare.getText();

        if (inputValidation.priceIsCorrect(inputFare)) {
            changedPrice.setFare(Integer.valueOf(inputFare));
        } else {
            inputValidation.showWarningAlert("Incorrect price format");
        }
        return changedPrice;
    }

}

