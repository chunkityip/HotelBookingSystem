import java.util.Date;

public class Customer {
    private String name;
    private double discount;
    private int ticket;
    private int customerId;
    private String roomNumber;
    private Date checkInTime;
    private Date checkOutTime;
    private int numberOfStays;

    public Customer(String name, double discount, int ticket, int customerId, String roomNumber) {
        this.name = name;
        this.discount = discount;
        this.ticket = ticket;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.numberOfStays = 0;  // Initialize number of stays
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

    public int getNumberOfStays() {
        return numberOfStays;
    }

    public void incrementNumberOfStays() {
        this.numberOfStays++;
    }
}