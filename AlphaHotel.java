public class AlphaHotel extends Hotel {
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
        // Implementation
    }

    @Override
    public void updateRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
        // Implementation
    }

    @Override
    public void removeRoomType(String roomType) {
        // Implementation
    }
}
