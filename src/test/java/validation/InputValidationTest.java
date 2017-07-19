package validation;


import controllers.validation.InputValidation;
import controllers.validation.InputValidationController;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputValidationTest {
    @Test
    public void passengerNameInputCheckTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.passengerNameInputCheck("Maryna"));
        assertEquals(true, testObj.passengerNameInputCheck("admin"));
        assertEquals(false, testObj.passengerNameInputCheck("7sdfa"));
    }

    @Test
    public void passportNumberInputCheckTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.passportNumberInputCheck("PS125325"));
        assertEquals(true, testObj.passportNumberInputCheck("aa000520"));
        assertEquals(false, testObj.passportNumberInputCheck("111586"));
        assertEquals(false, testObj.passportNumberInputCheck("AA15233"));
    }

    @Test
    public void cityNameIsCorrectTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.cityNameIsCorrect("KYIV"));
        assertEquals(true, testObj.cityNameIsCorrect("kyiv"));
        assertEquals(false, testObj.cityNameIsCorrect("sdf2s"));
    }

    @Test
    public void timeIsCorrectTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.timeIsCorrect("21:55:03"));
        assertEquals(true, testObj.timeIsCorrect("12:00"));
        assertEquals(false, testObj.timeIsCorrect("1:2:0"));
    }

    @Test
    public void priceIsCorrectTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.priceIsCorrect("450"));
        assertEquals(true, testObj.priceIsCorrect("145.5"));
        assertEquals(true, testObj.priceIsCorrect("45,5"));
        assertEquals(false, testObj.priceIsCorrect("45,5a"));
    }
    @Test
    public void flightNumberIsCorrectTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.flightNumberIsCorrect("PS111"));
        assertEquals(true, testObj.flightNumberIsCorrect("PS12"));
        assertEquals(true, testObj.flightNumberIsCorrect("VY1458"));
        assertEquals(false, testObj.flightNumberIsCorrect("A11"));
        assertEquals(false, testObj.flightNumberIsCorrect("AAA11"));
    }

    @Test
    public void airlineNameIsCorrectTest() {
        InputValidation testObj = new InputValidationController();
        assertEquals(true, testObj.airlineNameIsCorrect("UKRAINE INTERNATIONAL AIRLINES"));
        assertEquals(true, testObj.airlineNameIsCorrect("KLM"));
        assertEquals(false, testObj.airlineNameIsCorrect("A3"));
    }
}
