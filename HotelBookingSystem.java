import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HotelBookingSystem {

    private int ticketNumber = 1; // Initialize the ticket number

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

        // Create an empty list of customers
         List<Customer> customers = new ArrayList<>();
         

        switch (choice) {
            case 1:
                Hotel alphaHotel = new AlphaHotel();
                showHotelMenu(alphaHotel, scanner , customers);
                break;
            case 2:
                Hotel betaHotel = new BetaHotel();
                showHotelMenu(betaHotel, scanner , customers);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void showHotelMenu(Hotel hotel, Scanner scanner, List<Customer> customers) {
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
                    // Implement display logic
                    displayAvailableRooms(hotel);
                    break;
                case 2:
                    // Implement cleaning room (Admin) logic
                    hotel.cleanRooms();
                    break;
                case 3:
                    // Implement booking a room logic
                    bookRoom(hotel, scanner , customers);
                    break;
                case 4:
                    // Implement check-in logic
                    checkIn(scanner, customers);
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

    public void bookRoom(Hotel hotel, Scanner scanner, List<Customer> customers) {
        // Display available rooms
        displayAvailableRooms(hotel);
    
        System.out.println("Please enter which room you would like to book:");
        String roomNumber = scanner.next();
    
        // Check if the room is available
        Room roomToBook = hotel.getRoomByNumber(roomNumber);
        if (roomToBook != null && roomToBook.isCleaned() && !roomToBook.isBooked()) {
            System.out.println("Please enter your name:");
            String name = scanner.next();
    
            System.out.println("Please enter the guest name if any:");
            String guestName = scanner.next();
    
            // Calculate total cost
            double totalCost = roomToBook.getPricePerNight();
            System.out.println("Here is our service:");
            System.out.println("1. Breakfast: $10 per day");
            System.out.println("2. Customer Service: $20 per meal");
            System.out.println("3. Laundry Service: $5 per item");
            System.out.println("-----------------------------------------------");
            System.out.println("Please enter the service number, or 'no' if none:");
            String serviceChoice = scanner.next();
            if (!serviceChoice.equalsIgnoreCase("no")) {
                // Calculate service cost and update total cost
                double serviceCost = calculateServiceCost(serviceChoice);
                totalCost += serviceCost;
                System.out.println("Service: " + serviceChoice);
            }
    
            // Create Customer object and set its attributes
            Customer customer = new Customer(name, 0, ticketNumber++, 0); // Assuming loyaltyDiscount is not used here
            customer.setName(name);
            customer.setDiscount(calculateLoyaltyDiscount(hotel)); // Set discount based on loyalty
            // Set other attributes as needed
            customer.setTicket(ticketNumber); // Set the ticket number
    
            // Print booking information
            System.out.println("Here is your information. Please make sure the information is collected:");
            System.out.println("Name: " + customer.getName());
            System.out.println("Guest: " + guestName);
            if (!serviceChoice.equalsIgnoreCase("no")) {
                System.out.println("Service: " + getServiceDescription(serviceChoice));
            }
            System.out.println("Total Cost: " + totalCost);
            System.out.println("Thank you for your booking!");
    
            // Print customer's ticket
            System.out.println("Here is your ticket: " + customer.getTicket());
    
            // Print loyalty discount information
            System.out.println("Loyalty Discount: Since you visited " + customer.getDiscount() + " times, you have a $" + customer.getDiscount() + " discount");
    
            // Add the customer to the list
            customers.add(customer);
        } else {
            System.out.println("The room is not available for booking.");
        }
    }

    // Method to calculate service cost
    public double calculateServiceCost(String serviceChoice) {
        double serviceCost = 0;
        switch (serviceChoice) {
            case "1":
                serviceCost = 10.0;
                break;
            case "2":
                serviceCost = 20.0;
                break;
            case "3":
                serviceCost = 5.0;
                break;
            default:
                break;
        }
        return serviceCost;
    }

    // Method to get service description
    public String getServiceDescription(String serviceChoice) {
        String serviceDescription = "";
        switch (serviceChoice) {
            case "1":
                serviceDescription = "Breakfast";
                break;
            case "2":
                serviceDescription = "Customer Service";
                break;
            case "3":
                serviceDescription = "Laundry Service";
                break;
            default:
                break;
        }
        return serviceDescription;
    }

    // Method to calculate loyalty discount
    public int calculateLoyaltyDiscount(Hotel hotel) {
        // Assume loyalty discount calculation logic here
        return 0; // Placeholder, replace with actual calculation
    }

    public void checkIn(Scanner scanner, List<Customer> customers) {
        System.out.println("\nCheck-in");
        System.out.println("--------------");
        System.out.println("Please enter your ticket:");
        int customerTicket = scanner.nextInt();
    
        // Retrieve customer object based on ticket number
        iNT ticket = customer.getTicket(); <------
    
        if (customer != null) {
            // Get current date and time
            Date currentDate = new Date();
            System.out.println("Check-in complete");
            System.out.println("Check-in time: " + currentDate);
        } else {
            System.out.println("Invalid ticket number. Unable to proceed with check-in.");
        }
    }
    
    // Helper method to find customer by ticket number
    private Customer findCustomerByTicket(int ticketNumber, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getTicket() == ticketNumber) {
                return customer; // Return the customer object if ticket number matches
            }
        }
        return null; // Return null if customer with the given ticket number is not found
    }


    static abstract class Room {
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

        public void setCleaned(boolean cleaned) {
            this.cleaned = cleaned;
        }

        // Add getPricePerNight method to your Room class
        public double getPricePerNight() {
            return pricePerNight;
        }
    }

    public static class Customer {
        //private String type; // "guest" or "primary"
        private String name; // Customer's name
        private double discount; // Discounts based on loyalty
        private int ticket; // Ticket number
        private int customerId; // Unique customer ID

        // Constructor
        public Customer(String name, double discount, int ticket, int customerId) {
            this.name = name;
            this.discount = discount;
            this.ticket = ticket;
            this.customerId = customerId;
        }

        /* 
        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
        */

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public int getTicket() {
            return ticket;
        }

        public void setTicket(int ticket) {
            this.ticket = ticket;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }
    }

    static class AlphaRoom extends Room {
        public AlphaRoom(String type, int capacity, double pricePerNight, String number) {
            super(type, capacity, pricePerNight, number);
        }
    }

    static class BetaRoom extends Room {
        public BetaRoom(String type, int capacity, double pricePerNight, String number) {
            super(type, capacity, pricePerNight, number);
        }
    }

    static abstract class Hotel {
        private String name;
        protected List<Room> rooms;

        public Hotel(String name) {
            this.name = name;
            this.rooms = new ArrayList<>();
            initializeRooms(); // Initialize rooms for the hotel
        }

        protected abstract void initializeRooms(); // Abstract method to be implemented by subclasses

        public String getName() {
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

        // Method to clean rooms
        public void cleanRooms() {
            for (Room room : rooms) {
                if (!room.isCleaned()) {
                    System.out.println(room.getNumber() + "is cleaning!");
                    room.setCleaned(true); // Mark the room as cleaned
                } else {
                    System.out.println("Room " + room.getNumber() + " is cleaned!");
                }
            }
            System.out.println("-----------------");
        }

        public Room getRoomByNumber(String roomNumber) {
            for (Room room : rooms) {
                if (room.getNumber().equals(roomNumber)) {
                    return room;
                }
            }
            return null; // Return null if room not found
        }
    }

    static class AlphaHotel extends Hotel {
        public AlphaHotel() {
            super("Alpha");
        }

        @Override
        protected void initializeRooms() {
            // Add rooms for Alpha Hotel
            rooms.add(new AlphaRoom("Double Standard", 2, 150.0, "201"));
            rooms.add(new AlphaRoom("Double Standard", 2, 150.0, "202"));
            rooms.add(new AlphaRoom("Deluxe Double", 2, 200.0, "301"));
            rooms.add(new AlphaRoom("Deluxe Double", 2, 200.0, "302"));
            rooms.add(new AlphaRoom("Junior Suite", 2, 300.0, "401"));
            rooms.add(new AlphaRoom("Grand Suite", 2, 400.0, "501"));
            rooms.add(new AlphaRoom("Family Room", 3, 225.0, "601"));
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

    static class BetaHotel extends Hotel {
        public BetaHotel() {
            super("Beta");
        }

        @Override
        protected void initializeRooms() {
            // Add rooms for Beta Hotel
            rooms.add(new BetaRoom("Double Standard", 2, 150.0, "201"));
            rooms.add(new BetaRoom("Double Standard", 2, 150.0, "202"));
            rooms.add(new BetaRoom("Deluxe Double", 2, 200.0, "301"));
            rooms.add(new BetaRoom("Deluxe Double", 2, 200.0, "302"));
            rooms.add(new BetaRoom("Junior Suite", 2, 300.0, "401"));
            rooms.add(new BetaRoom("Grand Suite", 2, 400.0, "501"));
            rooms.add(new BetaRoom("Family Room", 3, 225.0, "601"));
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