public class BetaHotel extends Hotel {
    public BetaHotel() {
        super("Beta");
    }

    @Override
    protected void initializeRooms() {
        addRoomType("Double Standard", 2, 150.0, 4);
        addRoomType("Deluxe Double", 2, 200.0, 4);
        addRoomType("Junior Suite", 2, 300.0, 1);
        addRoomType("Grand Suite", 2, 400.0, 1);
        addRoomType("Family Room", 3, 225.0, 1);
    }

    @Override
    protected Room createRoom(String roomType, int capacity, double pricePerNight, String number) {
        return new BetaRoom(roomType, capacity, pricePerNight, number);
    }
}