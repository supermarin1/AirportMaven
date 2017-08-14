package service.price;

import database.PriceDAO;
import model.flight.Cabin;
import model.price.Price;
import service.validation.InputValidationImpl;

import java.util.List;

public class PriceServiceImpl implements PriceService {

    private InputValidationImpl inputValidation = new InputValidationImpl();

    @Override
    public List<Price> getPriceList(String flightNumber) {
        List<Price> prices = null;
        if(inputValidation.flightNumberIsCorrect(flightNumber)){
            prices = PriceDAO.queryPriceListByFlightNumber(flightNumber);
        }
        return prices;
    }

    @Override
    public Integer getFare(String flightNumber, Cabin cabin) {
        return PriceDAO.getPrice(flightNumber, cabin);
    }

    @Override
    public void addPriceList(String flightNumber, List<Price> newPriceList) {
        if (inputValidation.flightNumberIsCorrect(flightNumber)) {
            for (Price price : newPriceList) {
                if (flightNumber.equals(price.getFlightNumber()) &&
                        inputValidation.priceIsCorrect(price.getFare().toString())) {
                    PriceDAO.addNewPriceListToDB(price);
                }
            }
        }
    }

    @Override
    public void changePriceList(String flightNumber, Price changedPriceList) {
        if (inputValidation.flightNumberIsCorrect(flightNumber) &&
                flightNumber.equals(changedPriceList.getFlightNumber()) &&
                inputValidation.priceIsCorrect(changedPriceList.getFare().toString())) {
            PriceDAO.changePriceInDB(changedPriceList);
        }
    }
}
