# Task 04 – Flight Booking OOP Console Application

**Name:** Prajakta Anil Gangane  
**Language:** Java  
**Task date:** 23 October 2026

## Description
A console-based flight booking application demonstrating Object-Oriented Programming (OOP). It lists flights, validates booking inputs, applies passenger-type discounts, confirms bookings, updates available seats, and displays the latest booking summary.

## Features
- View three predefined flights.
- Book a flight using its code.
- Validate passenger name, age, passenger type, ticket count, and seat availability.
- Regular passenger discount: 0%.
- Student passenger discount: 10%.
- Generate booking IDs such as `BK1001`.
- View the latest confirmed booking.
- Invalid bookings do not change seat counts or create bookings.
- Stores flights and bookings in memory; no database or external libraries are required.

## OOP concepts
- **Abstraction:** `Passenger` is an abstract class with an abstract discount method.
- **Inheritance:** `RegularPassenger` and `StudentPassenger` extend `Passenger`.
- **Polymorphism:** Each passenger type provides its own `getDiscountRate()` implementation.
- **Encapsulation:** Class fields are private and accessed through methods.
- **Classes and objects:** `Flight`, `Passenger`, `Booking`, and `FlightBookingSystem`.

## Flight data
| Flight code | Airline | Route | Seats | Base fare |
|---|---|---|---:|---:|
| PN101 | Kiran Air | Pune → Delhi | 20 | Rs. 4,000 |
| MB202 | SkyConnect | Mumbai → Bengaluru | 15 | Rs. 3,500 |
| PN303 | Kiran Air | Pune → Hyderabad | 18 | Rs. 2,800 |

## Fare formula
- Subtotal = base fare × number of tickets
- Discount = subtotal × passenger discount rate
- Final amount = subtotal − discount

## Run the program
Make sure Java JDK is installed. Open a terminal in this folder and run:

```bash
javac FlightBookingSystem.java
java FlightBookingSystem
```

## Sample test
Book flight `PN101` for passenger `Asha Patil`, age `21`, type `Student`, with `2` tickets:
- Subtotal: Rs. 8,000
- Student discount (10%): Rs. 800
- Final amount: Rs. 7,200

## Files
- `FlightBookingSystem.java` – complete source code
- `README.md` – project description and run instructions
