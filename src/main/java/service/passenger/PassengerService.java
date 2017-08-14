package service.passenger;

import model.passenger.Passenger;

import java.util.List;

public interface PassengerService {
    /**
     * Appeals to database layer to get passengers list of particular flight
     *
     * @param flightNumber - number of flight passengers list
     * @return a list of passengers
     */
    List<Passenger> getPassengerList(String flightNumber);

    /**
     * Appeals to database layer to get passengers searching by name
     *
     * @param firstName  - first passenger name
     * @param secondName - second passenger name
     * @return a list of passengers
     */
    List<Passenger> searchPassengerByName(String firstName, String secondName);

    /**
     * Appeals to database layer to get passengers searching by passport number
     *
     * @param passport - passenger passport number
     * @return a list of passengers
     */
    List<Passenger> searchPassengerByPassport(String passport);

    /**
     * Adds new passenger
     *
     * @param newPassenger - passenger to be added
     */
    void addPassenger(Passenger newPassenger);

    /**
     * Changes passengers info
     *
     * @param passport - passport number to identify passenger to be changed
     * @param changedPassenger - passenger with new data
     */
    void changePassenger(String passport, Passenger changedPassenger);

    /**
     * Deletes passenger
     *
     * @param passport - passport number to identify passenger to be deleted
     */
    void deletePassenger(String passport);
}
