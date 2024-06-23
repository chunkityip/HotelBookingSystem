import java.text.SimpleDateFormat;
import java.util.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class HotelBookingSystem {
    private int ticketNumber = 1;
    private static final int CHECK_IN_HOUR = 15;
    private static final int CHECK_OUT_HOUR = 12;

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Hotel Booking System!");
            System.out.println("1. Choose the Hotel");
            System.out.println("(1 for Old Hotel: Alpha)");
            System.out.println("(2 for New Hotel: Beta)");
            int choice = getIntInput(scanner);

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
    }

    private void showHotelMenu(Hotel hotel, Scanner scanner, List<Customer> customers) {
        boolean exit = false;
        while (!exit) {
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
            System.out.println("9. Add Room Type (Admin)");
            System.out.println("10. Update Room Type (Admin)");
            System.out.println("11. Remove Room Type (Admin)");
            System.out.println("12. Exit");
            System.out.println("Enter your choice:");

            int choice = getIntInput(scanner);
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
                    addRoomType(scanner, hotel);
                    break;
                case 10:
                    updateRoomType(scanner, hotel);
                    break;
                case 11:
                    removeRoomType(scanner, hotel);
                    break;
                case 12:
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayAvailableRooms(Hotel hotel) {
        List<Room> availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.print(room.getNumber() + " : ");
            System.out.print(room.getTypeString() + " , ");
        }
        System.out.println("\n");
    }

    private void bookRoom(Hotel hotel, Scanner scanner, List<Customer> customers) {
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
            customer.setDiscount(calculateLoyaltyDiscount(customer));
            customer.setTicket(ticketNumber);

            roomToBook.setBooked(true);

            System.out.println("Here is your information. Please make sure the information is correct:");
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

    private void checkIn(Scanner scanner, List<Customer> customers) {
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
        int customerTicket = getIntInput(scanner);

        Customer customer = findCustomerByTicket(customerTicket, customers);

        if (customer != null) {
            customer.setCheckInTime(currentDate);
            customer.incrementNumberOfStays(); // Increment number of stays on check-in
            System.out.println("Check-in complete");
            System.out.println("Check-in time: " + currentDate);
        } else {
            System.out.println("Invalid ticket number. Unable to proceed with check-in.");
        }
    }

    private void checkOut(Scanner scanner, List<Customer> customers, Hotel hotel) {
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
        int customerTicket = getIntInput(scanner);

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

    private void manageServices(Scanner scanner) {
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

    private void manageRoomRates(Scanner scanner, Hotel hotel) {
        System.out.println("Manage Room Rates");
        System.out.println("-----------------");
        System.out.println("Enter the room number to change the price:");
        String roomNumber = scanner.next();
        Room room = hotel.getRoomByNumber(roomNumber);

        if (room != null) {
            System.out.println("Current price per night for room " + roomNumber + ": " + room.getPricePerNight());
            System.out.println("Enter the new price:");
            double newPrice = getDoubleInput(scanner);
            room.changePrice(newPrice);
            System.out.println("Price updated successfully.");
        } else {
            System.out.println("Room not found.");
        }
    }

    private void applySeasonalPricing(Scanner scanner, Hotel hotel) {
        System.out.println("Apply Seasonal Pricing");
        System.out.println("----------------------");
        System.out.println("Enter the season (winter/summer):");
        String season = scanner.next();

        for (Room room : hotel.getRooms()) {
            room.applySeasonalPricing(season);
        }
        System.out.println("Seasonal pricing applied successfully.");
    }

    private void addRoomType(Scanner scanner, Hotel hotel) {
        System.out.println("Add Room Type");
        System.out.println("----------------------");
        System.out.println("Enter the room type:");
        String roomType = scanner.next();
        System.out.println("Enter the room capacity:");
        int capacity = getIntInput(scanner);
        System.out.println("Enter the price per night:");
        double pricePerNight = getDoubleInput(scanner);
        System.out.println("Enter the number of rooms:");
        int numberOfRooms = getIntInput(scanner);

        hotel.addRoomType(roomType, capacity, pricePerNight, numberOfRooms);
        System.out.println("Room type added successfully.");
    }

    private void updateRoomType(Scanner scanner, Hotel hotel) {
        System.out.println("Update Room Type");
        System.out.println("----------------------");
        System.out.println("Enter the old room type:");
        String oldRoomType = scanner.next();
        System.out.println("Enter the new room type:");
        String newRoomType = scanner.next();
        System.out.println("Enter the new room capacity:");
        int capacity = getIntInput(scanner);
        System.out.println("Enter the new price per night:");
        double pricePerNight = getDoubleInput(scanner);

        hotel.updateRoomType(oldRoomType, newRoomType, capacity, pricePerNight);
        System.out.println("Room type updated successfully.");
    }

    private void removeRoomType(Scanner scanner, Hotel hotel) {
        System.out.println("Remove Room Type");
        System.out.println("----------------------");
        System.out.println("Enter the room type to remove:");
        String roomType = scanner.next();

        hotel.removeRoomType(roomType);
        System.out.println("Room type removed successfully.");
    }

    private double calculateServiceCost(String serviceChoice) {
        switch (serviceChoice) {
            case "1":
                return 10.0;
            case "2":
                return 20.0;
            case "3":
                return 5.0;
            default:
                return 0;
        }
    }

    private String getServiceDescription(String serviceChoice) {
        switch (serviceChoice) {
            case "1":
                return "Breakfast";
            case "2":
                return "Customer Service";
            case "3":
                return "Laundry Service";
            default:
                return "";
        }
    }

    private double calculateLoyaltyDiscount(Customer customer) {
        int numberOfStays = customer.getNumberOfStays();
        return Math.min(numberOfStays * 1.0, 20.0);  // 1% per stay, max 20%
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
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour >= CHECK_IN_HOUR;
    }

    private boolean isValidCheckOutTime() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour < CHECK_OUT_HOUR;
    }

    // Automatically cancel bookings if not checked in by 12 pm on the day of booking
    private void cancelBookingsIfNotCheckedIn(List<Customer> customers, Hotel hotel) {
        Date currentDate = new Date();
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

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

    private int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer:");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a decimal number:");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
