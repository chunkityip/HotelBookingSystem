import java.util.ArrayList;
import java.util.List;

public abstract class Hotel {
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

    public List<Room> getRooms() {
        return rooms;
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