import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HotelBookingSystem {

    private int ticketNumber = 1;
    private static final int CHECK_IN_HOUR = 15;  // set a variable that all room should be able to check in at 3 pm 
    private static final int CHECK_OUT_HOUR = 12; // set a variable that all room should be able to check in at 3 pm 

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

        List<Customer> customers = new ArrayList<>();

        switch (choice) {
            case 1:
                Hotel alphaHotel = new AlphaHotel();
                showHotelMenu(alphaHotel, scanner, customers);
                break;
            case 2:
                Hotel betaHotel = new BetaHotel();
                showHotelMenu(betaHotel, scanner, customers);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void showHotelMenu(Hotel hotel, Scanner scanner, List<Customer> customers) {
        boolean exit = false;
        do {
            System.out.println("Welcome to " + hotel.getName() + "!");
            System.out.println("Menu:");
            System.out.println("1. Available rooms");
            System.out.println("2. Cleaning rooms (Admin)");
            System.out.println("3. Book a Room");
            System.out.println("4. Check-in");
            System.out.println("5. Check-out");
            System.out.println("6. Manage Services");
            System.out.println("7. Manage Room Rates (Admin)");
            System.out.println("8. Apply Seasonal Pricing (Admin)");
            System.out.println("9. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayAvailableRooms(hotel);
                    break;
                case 2:
                    hotel.cleanRooms();
                    break;
                case 3:
                    bookRoom(hotel, scanner, customers);
                    break;
                case 4:
                    checkIn(scanner, customers);
                    break;
                case 5:
                    checkOut(scanner, customers, hotel);
                    break;
                case 6:
                    manageServices(scanner);
                    break;
                case 7:
                    manageRoomRates(scanner, hotel);
                    break;
                case 8:
                    applySeasonalPricing(scanner, hotel);
                    break;
                case 9:
                    System.out.println("Exiting...");
                    exit = true;
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
        displayAvailableRooms(hotel);
        System.out.println("Please enter which room you would like to book:");
        String roomNumber = scanner.next();

        Room roomToBook = hotel.getRoomByNumber(roomNumber);
        if (roomToBook != null && roomToBook.isCleaned() && !roomToBook.isBooked()) {
            System.out.println("Please enter your name:");
            String name = scanner.next();

            System.out.println("Please enter the guest name if any:");
            String guestName = scanner.next();

            double totalCost = roomToBook.getPricePerNight();
            System.out.println("Here is our service:");
            System.out.println("1. Breakfast: $10 per day");
            System.out.println("2. Customer Service: $20 per meal");
            System.out.println("3. Laundry Service: $5 per item");
            System.out.println("-----------------------------------------------");
            System.out.println("Please enter the service number, or 'no' if none:");
            String serviceChoice = scanner.next();
            if (!serviceChoice.equalsIgnoreCase("no")) {
                double serviceCost = calculateServiceCost(serviceChoice);
                totalCost += serviceCost;
                System.out.println("Service: " + serviceChoice);
            }

            Customer customer = new Customer(name, 0, ticketNumber++, 0, roomNumber);
            customer.setName(name);
            customer.setDiscount(calculateLoyaltyDiscount(hotel));
            customer.setTicket(ticketNumber);

            roomToBook.setBooked(true);

            System.out.println("Here is your information. Please make sure the information is collected:");
            System.out.println("Name: " + customer.getName());
            System.out.println("Guest: " + guestName);
            if (!serviceChoice.equalsIgnoreCase("no")) {
                System.out.println("Service: " + getServiceDescription(serviceChoice));
            }
            System.out.println("Total Cost: " + totalCost);
            System.out.println("Thank you for your booking!");
            System.out.println("Here is your ticket: " + customer.getTicket());
            System.out.println("Loyalty Discount: Since you visited " + customer.getDiscount() + " times, you have a $" + customer.getDiscount() + " discount");

            customers.add(customer);
        } else {
            System.out.println("The room is not available for booking.");
        }
    }

    public void checkIn(Scanner scanner, List<Customer> customers) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        String formattedDate = sdf.format(currentDate);

        if (!isValidCheckInTime()) {
            System.out.println("The current time is " + formattedDate + ". Check-in is only allowed after 3 pm.");
            return;
        }

        System.out.println("\nCheck-in");
        System.out.println("--------------");
        System.out.println("Please enter your ticket:");
        int customerTicket = scanner.nextInt();

        Customer customer = findCustomerByTicket(customerTicket, customers);

        if (customer != null) {
            customer.setCheckInTime(currentDate);
            System.out.println("Check-in complete");
            System.out.println("Check-in time: " + currentDate);
        } else {
            System.out.println("Invalid ticket number. Unable to proceed with check-in.");
        }
    }

    /*
     * Faceing a issue that checkOut() are not working:
     * To fix it : adjust the booking process to mark rooms as booked and associate them with customer tickets.
     * 
     * By adding new variable : private String roomNumber at Customer class
     */


    public void checkOut(Scanner scanner, List<Customer> customers, Hotel hotel) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        String formattedDate = sdf.format(currentDate);

        if (!isValidCheckOutTime()) {
            System.out.println("The current time is " + formattedDate + ". Check-out is only allowed before 12 noon.");
            return;
        }

        System.out.println("\nCheck-out");
        System.out.println("--------------");
        System.out.println("Please enter your ticket:");
        int customerTicket = scanner.nextInt();

        Customer customer = findCustomerByTicket(customerTicket, customers);

        if (customer != null) {
            String roomNumber = customer.getRoomNumber();
            Room roomToCheckout = hotel.getRoomByNumber(roomNumber);
            if (roomToCheckout != null && roomToCheckout.isBooked()) {
                roomToCheckout.setBooked(false);
                customer.setCheckOutTime(currentDate);
                System.out.println("Check-out complete");
                System.out.println("Thank you for staying with us!");
            } else {
                System.out.println("No booking found for this ticket.");
            }
        } else {
            System.out.println("Invalid ticket number. Unable to proceed with check-out.");
        }
    }

    public void manageServices(Scanner scanner) {
        System.out.println("Manage Services");
        System.out.println("------------------");
        System.out.println("1. Breakfast: $10 per day");
        System.out.println("2. Customer Service: $20 per meal");
        System.out.println("3. Laundry Service: $5 per item");
        System.out.println("Please enter the service number to add to your booking:");
        String serviceChoice = scanner.next();

        if (!serviceChoice.equalsIgnoreCase("no")) {
            double serviceCost = calculateServiceCost(serviceChoice);
            System.out.println("Service: " + getServiceDescription(serviceChoice) + " added. Cost: $" + serviceCost);
        } else {
            System.out.println("No service selected.");
        }
    }

    public void manageRoomRates(Scanner scanner, Hotel hotel) {
        System.out.println("Manage Room Rates");
        System.out.println("-----------------");
        System.out.println("Enter the room number to change the price:");
        String roomNumber = scanner.next();
        Room room = hotel.getRoomByNumber(roomNumber);

        if (room != null) {
            System.out.println("Current price per night for room " + roomNumber + ": " + room.getPricePerNight());
            System.out.println("Enter the new price:");
            double newPrice = scanner.nextDouble();
            room.changePrice(newPrice);
            System.out.println("Price updated successfully.");
        } else {
            System.out.println("Room not found.");
        }
    }

    public void applySeasonalPricing(Scanner scanner, Hotel hotel) {
        System.out.println("Apply Seasonal Pricing");
        System.out.println("----------------------");
        System.out.println("Enter the season (winter/summer):");
        String season = scanner.next();

        for (Room room : hotel.rooms) {
            room.applySeasonalPricing(season);
        }
        System.out.println("Seasonal pricing applied successfully.");
    }

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

    public int calculateLoyaltyDiscount(Hotel hotel) {
        return 0;
    }

    private Customer findCustomerByTicket(int ticketNumber, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getTicket() == ticketNumber) {
                return customer;
            }
        }
        return null;
    }

    private boolean isValidCheckInTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour >= CHECK_IN_HOUR;
    }

    private boolean isValidCheckOutTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < CHECK_OUT_HOUR;
    }

    // Automatically cancel bookings if not checked in by 12 pm on the day of booking
    private void cancelBookingsIfNotCheckedIn(List<Customer> customers, Hotel hotel) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (currentHour >= CHECK_OUT_HOUR) {
            List<Customer> customersToCancel = new ArrayList<>();
            for (Customer customer : customers) {
                if (customer.getCheckInTime() == null) {
                    String roomNumber = customer.getRoomNumber();
                    Room room = hotel.getRoomByNumber(roomNumber);
                    if (room != null && room.isBooked()) {
                        room.setBooked(false);
                        customersToCancel.add(customer);
                        System.out.println("Booking for ticket " + customer.getTicket() + " has been canceled due to no check-in by 12 pm.");
                    }
                }
            }
            customers.removeAll(customersToCancel);
        }
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
            this.cleaned = true;
            this.booked = false;
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

        public void setBooked(boolean booked) {
            this.booked = booked;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }

        public void setPricePerNight(double pricePerNight) {
            this.pricePerNight = pricePerNight;
        }

        public void changePrice(double newPrice) {
            this.pricePerNight = newPrice;
        }

        public void applySeasonalPricing(String season) {
            switch (season.toLowerCase()) {
                case "winter":
                    if (type.equals("Double Standard")) {
                        this.pricePerNight = 100.0;
                    } else if (type.equals("Deluxe Double")) {
                        this.pricePerNight = 150.0;
                    }
                    // Add more conditions for other room types
                    break;
                case "summer":
                    if (type.equals("Double Standard")) {
                        this.pricePerNight = 175.0;
                    } else if (type.equals("Deluxe Double")) {
                        this.pricePerNight = 225.0;
                    }
                    // Add more conditions for other room types
                    break;
                default:
                    break;
            }
        }
    }

    public static class Customer {
        private String name;
        private double discount;
        private int ticket;
        private int customerId;
        private String roomNumber;
        private Date checkInTime;
        private Date checkOutTime;

        public Customer(String name, double discount, int ticket, int customerId, String roomNumber) {
            this.name = name;
            this.discount = discount;
            this.ticket = ticket;
            this.customerId = customerId;
            this.roomNumber = roomNumber;
        }

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

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public Date getCheckInTime() {
            return checkInTime;
        }

        public void setCheckInTime(Date checkInTime) {
            this.checkInTime = checkInTime;
        }

        public Date getCheckOutTime() {
            return checkOutTime;
        }

        public void setCheckOutTime(Date checkOutTime) {
            this.checkOutTime = checkOutTime;
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
            initializeRooms();
        }

        protected abstract void initializeRooms();

        public String getName() {
            return name;
        }

        public abstract void addRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms);

        public abstract void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms);

        public abstract void removeRoomType(String roomType);

        public List<Room> getAvailableRooms() {
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : rooms) {
                if (room.isCleaned() && !room.isBooked()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

        public void cleanRooms() {
            for (Room room : rooms) {
                if (!room.isCleaned()) {
                    System.out.println(room.getNumber() + " is cleaning!");
                    room.setCleaned(true);
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
            return null;
        }
    }

    static class AlphaHotel extends Hotel {
        public AlphaHotel() {
            super("Alpha");
        }

        @Override
        protected void initializeRooms() {
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
        }

        @Override
        public void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
        }

        @Override
        public void removeRoomType(String roomType) {
        }
    }

    static class BetaHotel extends Hotel {
        public BetaHotel() {
            super("Beta");
        }

        @Override
        protected void initializeRooms() {
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
        }

        @Override
        public void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
        }

        @Override
        public void removeRoomType(String roomType) {
        }
    }
}
