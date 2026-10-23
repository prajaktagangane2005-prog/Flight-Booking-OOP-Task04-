import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Passenger {
    private final String name;
    private final int age;

    public Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract double getDiscountRate();

    public String getPassengerType() {
        return getClass().getSimpleName().replace("Passenger", "");
    }
}

class RegularPassenger extends Passenger {
    public RegularPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public double getDiscountRate() {
        return 0.0;
    }
}

class StudentPassenger extends Passenger {
    public StudentPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public double getDiscountRate() {
        return 0.10;
    }
}

class Flight {
    private final String code;
    private final String airline;
    private final String source;
    private final String destination;
    private int availableSeats;
    private final double baseFare;

    public Flight(String code, String airline, String source, String destination,
                  int availableSeats, double baseFare) {
        this.code = code;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.baseFare = baseFare;
    }

    public String getCode() { return code; }
    public String getAirline() { return airline; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public int getAvailableSeats() { return availableSeats; }
    public double getBaseFare() { return baseFare; }

    public boolean hasSeats(int requestedSeats) {
        return requestedSeats > 0 && requestedSeats <= availableSeats;
    }

    public void reserveSeats(int requestedSeats) {
        if (!hasSeats(requestedSeats)) {
            throw new IllegalArgumentException("Requested seats are not available.");
        }
        availableSeats -= requestedSeats;
    }

    public void display() {
        System.out.printf("%-8s %-16s %-12s %-14s %-8d Rs. %.2f%n",
                code, airline, source, destination, availableSeats, baseFare);
    }
}

class Booking {
    private final String bookingId;
    private final Flight flight;
    private final Passenger passenger;
    private final int ticketCount;
    private final double subtotal;
    private final double discount;
    private final double total;

    public Booking(String bookingId, Flight flight, Passenger passenger, int ticketCount) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.ticketCount = ticketCount;
        this.subtotal = flight.getBaseFare() * ticketCount;
        this.discount = subtotal * passenger.getDiscountRate();
        this.total = subtotal - discount;
    }

    public void displaySummary() {
        System.out.println("\n========== BOOKING SUMMARY ==========");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Flight: " + flight.getCode() + " | " + flight.getSource()
                + " to " + flight.getDestination());
        System.out.println("Airline: " + flight.getAirline());
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Age: " + passenger.getAge());
        System.out.println("Passenger type: " + passenger.getPassengerType());
        System.out.println("Tickets: " + ticketCount);
        System.out.printf("Base fare per ticket: Rs. %.2f%n", flight.getBaseFare());
        System.out.printf("Subtotal: Rs. %.2f%n", subtotal);
        System.out.printf("%s discount (%.0f%%): Rs. %.2f%n",
                passenger.getPassengerType(), passenger.getDiscountRate() * 100, discount);
        System.out.printf("Final amount: Rs. %.2f%n", total);
        System.out.println("=====================================");
    }
}

public class FlightBookingSystem {
    private final List<Flight> flights = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private int nextBookingNumber = 1001;

    public FlightBookingSystem() {
        flights.add(new Flight("PN101", "Kiran Air", "Pune", "Delhi", 20, 4000));
        flights.add(new Flight("MB202", "SkyConnect", "Mumbai", "Bengaluru", 15, 3500));
        flights.add(new Flight("PN303", "Kiran Air", "Pune", "Hyderabad", 18, 2800));
    }

    public void displayFlights() {
        System.out.println("\n================ AVAILABLE FLIGHTS ================");
        System.out.printf("%-8s %-16s %-12s %-14s %-8s %s%n",
                "Code", "Airline", "Source", "Destination", "Seats", "Base Fare");
        for (Flight flight : flights) {
            flight.display();
        }
        System.out.println("===================================================");
    }

    private Flight findFlightByCode(String code) {
        for (Flight flight : flights) {
            if (flight.getCode().equalsIgnoreCase(code.trim())) {
                return flight;
            }
        }
        return null;
    }

    private boolean validateBookingDetails(String name, int age, int tickets) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Booking failed: Passenger name cannot be blank.");
            return false;
        }
        if (age <= 0) {
            System.out.println("Booking failed: Age must be greater than zero.");
            return false;
        }
        if (tickets <= 0) {
            System.out.println("Booking failed: Ticket count must be greater than zero.");
            return false;
        }
        return true;
    }

    private int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public void bookFlight() {
        displayFlights();
        System.out.print("Enter flight code: ");
        String code = scanner.nextLine().trim();
        Flight flight = findFlightByCode(code);
        if (flight == null) {
            System.out.println("Booking failed: Flight code does not exist.");
            return;
        }

        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine().trim();
        int age = readInteger("Enter age: ");

        System.out.print("Passenger type (Regular/Student): ");
        String type = scanner.nextLine().trim();

        int tickets = readInteger("Enter number of tickets: ");
        if (!validateBookingDetails(name, age, tickets)) {
            return;
        }
        if (!type.equalsIgnoreCase("Regular") && !type.equalsIgnoreCase("Student")) {
            System.out.println("Booking failed: Passenger type must be Regular or Student.");
            return;
        }
        if (!flight.hasSeats(tickets)) {
            System.out.println("Booking failed: Not enough seats available.");
            System.out.println("No booking was created. Available seats remain unchanged.");
            return;
        }

        Passenger passenger;
        if (type.equalsIgnoreCase("Student")) {
            passenger = new StudentPassenger(name, age);
        } else {
            passenger = new RegularPassenger(name, age);
        }

        // Only update seats and save the booking after every validation succeeds.
        Booking booking = new Booking(String.format("BK%d", nextBookingNumber),
                flight, passenger, tickets);
        flight.reserveSeats(tickets);
        bookings.add(booking);
        nextBookingNumber++;

        System.out.println("\nBooking confirmed!");
        booking.displaySummary();
    }

    public void viewLatestBooking() {
        if (bookings.isEmpty()) {
            System.out.println("No confirmed booking found. Please book a flight first.");
            return;
        }
        bookings.get(bookings.size() - 1).displaySummary();
    }

    public void run() {
        System.out.println("Welcome to Kiran Air Booking");
        boolean running = true;
        while (running) {
            System.out.println("\n1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Booking");
            System.out.println("4. Exit");
            int choice = readInteger("Enter choice: ");

            switch (choice) {
                case 1:
                    displayFlights();
                    break;
                case 2:
                    bookFlight();
                    break;
                case 3:
                    viewLatestBooking();
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using Kiran Air Booking!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new FlightBookingSystem().run();
    }
}
