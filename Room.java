public abstract class Room {
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

    public void updateRoom(String newType, int newCapacity, double newPricePerNight) {
        this.type = newType;
        this.capacity = newCapacity;
        this.pricePerNight = newPricePerNight;
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
