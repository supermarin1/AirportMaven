package service.price;

import model.flight.Cabin;
import model.price.Price;

import java.util.List;

public interface PriceService {

    /**
     * Gets price list of particular flight
     * @param flightNumber - number of flight price list we are interested in
     * @return - price list
     */
    List<Price> getPriceList (String flightNumber);

    Integer getFare(String flightNumber, Cabin cabin);

    /**
     * Adds price list of particular flight
     * @param flightNumber - number of flight price list we are interested in
     * @param newPriceList - new price list
     */
    void addPriceList(String flightNumber, List<Price> newPriceList);

    /**
     * Changes price of particular flight
     * @param flightNumber - number of flight price we are interested in
     * @param changedPriceList - new price
     */
    void changePriceList(String flightNumber, Price changedPriceList);
}
