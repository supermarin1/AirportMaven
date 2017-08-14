package service.passenger;

import database.PassengerDAO;
import model.passenger.Passenger;
import service.validation.InputValidation;
import service.validation.InputValidationImpl;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {
    private InputValidation inputValidation = new InputValidationImpl();

    @Override
    public List<Passenger> getPassengerList(String flightNumber) {
        List<Passenger> passengerList = null;
        if (inputValidation.flightNumberIsCorrect(flightNumber)) {
            passengerList = PassengerDAO.queryPassengersByFlightNumber(flightNumber);
        }
        return passengerList;
    }

    @Override
    public List<Passenger> searchPassengerByName(String firstName, String secondName) {
        List<Passenger> passengers = null;

        boolean firstNameIsCorrect = inputValidation.nameIsCorrect(firstName);
        boolean secondNameIsCorrect = inputValidation.nameIsCorrect(secondName);
        boolean searchIsAllowed = (firstName.isEmpty() || firstNameIsCorrect) &&
                (secondName.isEmpty() || secondNameIsCorrect) &&
                !(firstName.isEmpty() && secondName.isEmpty());

        if (searchIsAllowed) {
            passengers = PassengerDAO.querySearchPassengerByName(firstName, secondName);
        }
        return passengers;
    }

    @Override
    public List<Passenger> searchPassengerByPassport(String passport) {
        List<Passenger> passengers = null;
        if (inputValidation.passportNumberIaCorrect(passport)) {
            passengers = PassengerDAO.querySearchPassengerByPassport(passport);
        }
        return passengers;
    }

    @Override
    public void addPassenger(Passenger newPassenger) {
        if (inputValidation.passengerIsCorrect(newPassenger)) {
            PassengerDAO.addNewPassengerToDB(newPassenger);
        }
    }

    @Override
    public void changePassenger(String passport, Passenger changedPassenger) {
        if (inputValidation.passportNumberIaCorrect(passport) && inputValidation.passengerIsCorrect(changedPassenger)) {
            PassengerDAO.changePassengerDataInDB(passport, changedPassenger);
        }
    }

    @Override
    public void deletePassenger(String passport) {
        if (inputValidation.passportNumberIaCorrect(passport)) {
            PassengerDAO.deletePassengerFromDB(passport);
        }
    }
}
