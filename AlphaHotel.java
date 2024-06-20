public class AlphaHotel extends Hotel {
    public AlphaHotel() {
        super("Alpha");
    }

    @Override
    protected void initializeRooms() {
        addRoomType("Double Standard", 2, 150.0, 2);
        addRoomType("Deluxe Double", 2, 200.0, 2);
        addRoomType("Junior Suite", 2, 300.0, 1);
        addRoomType("Grand Suite", 2, 400.0, 1);
        addRoomType("Family Room", 3, 225.0, 1);
    }

    @Override
    protected Room createRoom(String roomType, int capacity, double pricePerNight, String number) {
        return new AlphaRoom(roomType, capacity, pricePerNight, number);
    }
}