package service.airline;

import database.AirlineDAO;
import model.airline.Airline;
import service.validation.InputValidation;
import service.validation.InputValidationImpl;

import java.util.List;

public class AirlineServiceImpl implements AirlineService {
    private InputValidation inputValidation = new InputValidationImpl();

    @Override
    public List<String>  getAirlines() {
        return AirlineDAO.getAirlines();
    }

    @Override
    public void addNewAirline(String airlineName) {
        if(inputValidation.airlineNameIsCorrect(airlineName)){
            AirlineDAO.addNewAirline(airlineName.toUpperCase());
        }
    }
}
