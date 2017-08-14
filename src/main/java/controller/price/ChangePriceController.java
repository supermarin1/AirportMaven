package controller.price;

import service.price.PriceService;
import service.price.PriceServiceImpl;
import service.validation.InputValidationImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.price.Price;

public class ChangePriceController {

    private PriceService priceService = new PriceServiceImpl();
    private InputValidationImpl inputValidation = new InputValidationImpl();

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


    public void changePrice() {
        Price changedPrice = getChangedPriceFromDialog();
        if (!changedPrice.equals(oldPrice)) {
            priceService.changePriceList(changedPrice.getFlightNumber(), changedPrice);
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

