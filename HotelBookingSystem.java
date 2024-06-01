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
                Hotel alphaHotel = new AlphaHotel(); // Instantiate AlphaHotel, a concrete subclass of Hotel
                showHotelMenu(alphaHotel, scanner);
                break;
            case 2:
                Hotel betaHotel = new BetaHotel(); // Instantiate BetaHotel, a concrete subclass of Hotel
                showHotelMenu(betaHotel, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } // Close the run method here

    public void showHotelMenu(Hotel hotel, Scanner scanner) {
        boolean exit = false; // Flag to control the loop
        do {
            System.out.println("Welcome to " + hotel.getName() + "!");
            System.out.println("Menu:");
            System.out.println("1. Available rooms");
            System.out.println("2. Cleaning rooms (Admin)");
            System.out.println("3. Book a Room");
            System.out.println("4. Check-in");
            System.out.println("5. Check-out");
            System.out.println("6. Manage Services");
            System.out.println("7. Exit");
            System.out.println("Enter your choice:");
    
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayAvailableRooms(hotel);
                    break;
                case 2:
                    // Implement cleaning room (Admin) logic
                    break;
                case 3:
                    // Implement booking a room logic
                    break;
                case 4:
                    // Implement check-in logic
                    break;
                case 5:
                    // Implement check-out logic
                    break;
                case 6:
                    // Implement managing services logic
                    break;
                case 7:
                    System.out.println("Exiting...");
                    exit = true; // Set the flag to true to exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!exit);
    }
    

    public void displayAvailableRooms(Hotel hotel) {
        List<Room> availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.print(room.getNumber() + " : ");
            System.out.print(room.getTypeString() + " , ");
        }
        System.out.println("\n");
    }


    class Room {
        private String type;
        private int capacity;
        private double pricePerNight;
        private boolean cleaned;
        private boolean booked;
        private String number;

        public Room(String type, int capacity, double pricePerNight, String number) {
            this.type = type;
            this.capacity = capacity;
            this.pricePerNight = pricePerNight;
            this.cleaned = true; // Assume rooms are initially cleaned
            this.booked = false; // Assume rooms are initially not booked
            this.number = number;
        }

        public boolean isCleaned() {
            return cleaned;
        }

        public boolean isBooked() {
            return booked;
        }

        public String getNumber() {
            return number;
        }

        public String getTypeString() {
            return type;
        }
    }

    class Booking {
        private Customer primaryCustomer;
        private Customer guest;
        private Room room;
        private Date checkInDate;
        private Date checkOutDate;
        private List<Service> services;

        public Booking(Customer primaryCustomer, Customer guest, Room room, Date checkInDate, Date checkOutDate) {
            this.primaryCustomer = primaryCustomer;
            this.guest = guest;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.services = new ArrayList<>();
        }

        public Customer getPrimaryCustomer() {
            return primaryCustomer;
        }

        public void setPrimaryCustomer(Customer primaryCustomer) {
            this.primaryCustomer = primaryCustomer;
        }

        public Customer getGuest() {
            return guest;
        }

        public void setGuest(Customer guest) {
            this.guest = guest;
        }

        public Room getRoom() {
            return room;
        }

        public void setRoom(Room room) {
            this.room = room;
        }

        public Date getCheckInDate() {
            return checkInDate;
        }

        public void setCheckInDate(Date checkInDate) {
            this.checkInDate = checkInDate;
        }

        public Date getCheckOutDate() {
            return checkOutDate;
        }

        public void setCheckOutDate(Date checkOutDate) {
            this.checkOutDate = checkOutDate;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
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

    abstract class Hotel {
        private String name;
        List<Room> rooms;

        public Hotel(String name) {
            this.name = name;
            this.rooms = new ArrayList<>();
            initializeRooms(); // Initialize rooms for the hotel
        }

        protected abstract void initializeRooms(); // Abstract method to be implemented by subclasses

        public String getName() { // Define getName() method in the abstract class
            return name;
        }
        
        // Method to add a new room type
        public abstract void addRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms);
    
        // Method to update a room type
        public abstract void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms);
    
        // Method to remove a room type
        public abstract void removeRoomType(String roomType);
    
        // Method to get available rooms for booking
        public List<Room> getAvailableRooms() {
            // Logic to retrieve available rooms (e.g., rooms that are cleaned and not booked)
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : rooms) {
                if (room.isCleaned() && !room.isBooked()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }
    
        // Other methods for managing bookings, etc.
    }

    class AlphaHotel extends Hotel {
        public AlphaHotel() {
            super("Alpha");
        }
    
        @Override
        protected void initializeRooms() {
            // Add Double Standard rooms at 2f
            rooms.add(new Room("Double Standard", 2, 150.0, "201"));
            rooms.add(new Room("Double Standard", 2, 150.0, "202"));

            // Add Deluxe Double rooms at 3f
            rooms.add(new Room("Deluxe Double", 2, 200.0, "301"));
            rooms.add(new Room("Deluxe Double", 2, 200.0, "302"));

            // Add Junior Suite room at 4f
            rooms.add(new Room("Junior Suite", 2, 300.0, "401"));

            // Add Grand Suite room at 5f
            rooms.add(new Room("Grand Suite", 2, 400.0, "501"));

            // Add new type: Family at 6f
            rooms.add(new Room("Family Room", 3, 225.0, "601"));
        }
    
        @Override
        public void addRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
            // Logic to add a new room type to Alpha Hotel
        }
    
        @Override
        public void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
            // Logic to update a room type in Alpha Hotel
        }
    
        @Override
        public void removeRoomType(String roomType) {
            // Logic to remove a room type from Alpha Hotel
        }
    }
    
    class BetaHotel extends Hotel {
        public BetaHotel() {
            super("Beta");
        }
    
        @Override
        protected void initializeRooms() {
            // Add Double Standard rooms at 2f
            rooms.add(new Room("Double Standard", 2, 150.0, "201"));
            rooms.add(new Room("Double Standard", 2, 150.0, "202"));
            rooms.add(new Room("Double Standard", 2, 150.0, "203"));
            rooms.add(new Room("Double Standard", 2, 150.0, "204"));


            // Add Deluxe Double rooms at 3f
            rooms.add(new Room("Deluxe Double", 2, 200.0, "301"));
            rooms.add(new Room("Deluxe Double", 2, 200.0, "302"));
            rooms.add(new Room("Deluxe Double", 2, 200.0, "303"));
            rooms.add(new Room("Deluxe Double", 2, 200.0, "304"));

            //Add Family Room at 4f
            rooms.add(new Room("Family Room", 3, 225.0, "404"));
            rooms.add(new Room("Family Room", 3, 225.0, "405"));

            // Add Junior Suite room at 5f
            rooms.add(new Room("Junior Suite", 2, 300.0, "501"));

            // Add Grand Suite room at 6f
            rooms.add(new Room("Grand Suite", 2, 400.0, "601"));
        }
    
        @Override
        public void addRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
            // Logic to add a new room type to Beta Hotel
        }
    
        @Override
        public void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
            // Logic to update a room type in Beta Hotel
        }
    
        @Override
        public void removeRoomType(String roomType) {
            // Logic to remove a room type from Beta Hotel
        }
    }
}