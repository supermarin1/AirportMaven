package model.passenger;

import model.flight.Cabin;

import java.time.LocalDate;

/**
 *
 */
public class Passenger {

    private String flightNumber;
    private String route;
    private String firstName;
    private String secondName;
    private Gender gender;
    private String nationality;
    private String passport;

    private LocalDate dateOfBirth;

    private Cabin cabin;
    private Integer fare;
    @Override
    public String toString() {
        return "passenger{" +
                "flightNumber='" + flightNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", gender=" + gender +
                ", nationality='" + nationality + '\'' +
                ", passport='" + passport + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", cabin=" + cabin +
                ", fare=" + fare +
                '}';
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String origin, String destination) {
        this.route = origin + " - " + destination;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        String[] date = dateOfBirth.split("/");
        this.dateOfBirth = LocalDate.of(Integer.valueOf(date[2]),Integer.valueOf(date[1]), Integer.valueOf(date[0]));
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = Cabin.valueOf(cabin);
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (flightNumber != null ? !flightNumber.equals(passenger.flightNumber) : passenger.flightNumber != null)
            return false;
        if (route != null ? !route.equals(passenger.route) : passenger.route != null) return false;
        if (firstName != null ? !firstName.equals(passenger.firstName) : passenger.firstName != null) return false;
        if (secondName != null ? !secondName.equals(passenger.secondName) : passenger.secondName != null) return false;
        if (gender != passenger.gender) return false;
        if (nationality != null ? !nationality.equals(passenger.nationality) : passenger.nationality != null)
            return false;
        if (passport != null ? !passport.equals(passenger.passport) : passenger.passport != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(passenger.dateOfBirth) : passenger.dateOfBirth != null)
            return false;
        if (cabin != passenger.cabin) return false;
        return fare != null ? fare.equals(passenger.fare) : passenger.fare == null;
    }

    @Override
    public int hashCode() {
        int result = flightNumber != null ? flightNumber.hashCode() : 0;
        result = 31 * result + (route != null ? route.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (cabin != null ? cabin.hashCode() : 0);
        result = 31 * result + (fare != null ? fare.hashCode() : 0);
        return result;
    }
}
