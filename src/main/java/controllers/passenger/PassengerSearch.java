package controllers.passenger;

import javafx.collections.ObservableList;
import model.passenger.Passenger;

/**
 * An interface to get all passenger info according to user input requests.
 * Responsible for connecting UI requests and DB.
 */
interface PassengerSearch {
    /**
     * Appeals to database layer to get passengers searching by name
     *
     * @param firstName - first passenger name
     * @param secondName - second passenger name
     * @return a list of passengers, ready to be added to table on the view
     */
    ObservableList<Passenger> searchPassengerByName(String firstName, String secondName);

    /**
     * Appeals to database layer to get passengers searching by passport number
     *
     * @param passport - passenger passport number
     * @return a list of passengers, ready to be added to table on the view
     */
    ObservableList<Passenger> searchPassengerByPassport(String passport);
}
