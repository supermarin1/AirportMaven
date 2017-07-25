# AirportMaven
Main Academy graduation project

Task:  
Develop componential layered application for airport management.
• view of the airline flight information about arrivals and departures (separately).
It should reflect the information about the arrival (departure) date and time,
flight number, city/port of arrival (departure), terminal, flight status (check-in,
gate closed, arrived, departed at, unknown, canceled, expected at, delayed, in
flight), gate.   
• view of the flights pricelist with the class prices.  
• view of the passengers list. It should reflect the information about the flight
number, passenger first name, second name, nationality, passport, date of
birthday, sex, class (business, economy). Only for company staff.   
• insert, delete and update of this information. Only for company staff
• search by the flight number, price, first and second name, passport, arrival
(departure) port of and information output in the specified format. Clients
information is only for company staff.  

Source file: AirportMaven/src/main/java/controllers/AirportMain.java

Requirements:
 - JVM 1.8;
 - Internet connection (to get acess to database);
 - login and password to see all features;

User permissions:
 - not-logged users - see flight arrivals/departures info only;
 - low-level users - see all detailed flights information (passengers and prices lists)
   (login: low_level_staff, password: qwerty);
 - top-level user - see/change/delete for all information about flight/passenger/price
   (login/password under request).
