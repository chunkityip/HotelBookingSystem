import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelBookingSystem system = new HotelBookingSystem();
        system.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("1. Choose the Hotel");
        System.out.println("(1 for Old Hotel: Alpha)");
        System.out.println("(2 for New Hotel: Beta)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Hotel oldHotel = new Hotel("Alpha");
                showHotelMenu(oldHotel, scanner);
                break;
            case 2:
                Hotel newHotel = new Hotel("Beta");
                showHotelMenu(newHotel, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void showHotelMenu(Hotel hotel, Scanner scanner) {
        System.out.println("Welcome to " + hotel.getName() + "!");
        System.out.println("Menu:");
        System.out.println("1. Book a Room");
        System.out.println("2. Check-in");
        System.out.println("3. Check-out");
        System.out.println("4. Manage Services");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Implement booking logic
                break;
            case 2:
                // Implement check-in logic
                break;
            case 3:
                // Implement check-out logic
                break;
            case 4:
                // Implement service management logic
                break;
            case 5:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

class Room {
    private String type;
    private int capacity;
    private double pricePerNight;
    private boolean isCleaned;

    public Room(String type, int capacity, double pricePerNight) {
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.isCleaned = true; // Assume rooms are initially cleaned
    }

    public boolean isCleaned() {
        return isCleaned;
    }

    public void markAsCleaned() {
        this.isCleaned = true;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}

class Booking {
    private Customer customer;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private List<Service> services;

    public Booking(Customer customer, Room room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.services = new ArrayList<>();
    }

    public double calculateTotalBill() {
        double totalBill = room.getPricePerNight();
        for (Service service : services) {
            totalBill += service.calculateCost();
        }
        return totalBill;
    }

    public void addService(Service service) {
        services.add(service);
    }
}

class Customer {
    private String name;
    private int loyaltyPoints;
    private List<Booking> bookings;

    public Customer(String name) {
        this.name = name;
        this.loyaltyPoints = 0;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

abstract class Service {
    protected double price;

    public Service(double price) {
        this.price = price;
    }

    public abstract double calculateCost();
}

class Breakfast extends Service {
    public Breakfast(double price) {
        super(price);
    }

    @Override
    public double calculateCost() {
        return price;
    }
}

class RoomService extends Service {
    public RoomService(double price) {
        super(price);
    }

    @Override
    public double calculateCost() {
        return price;
    }
}

class LaundryService extends Service {
    public LaundryService(double price) {
        super(price);
    }

    @Override
    public double calculateCost() {
        return price;
    }
}

class Hotel {
    private String name;
    private List<Booking> bookings;

    public Hotel(String name) {
        this.name = name;
        this.bookings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Other methods for managing bookings, services, etc.
}