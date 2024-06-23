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

    public void addRoomType(String roomType, int capacity, double pricePerNight, int numberOfRooms) {
        int nextRoomNumber = rooms.size() + 1;
        for (int i = 0; i < numberOfRooms; i++) {
            rooms.add(createRoom(roomType, capacity, pricePerNight, String.valueOf(nextRoomNumber++)));
        }
    }

    public void updateRoomType(String oldRoomType, String newRoomType, int capacity, double pricePerNight) {
        for (Room room : rooms) {
            if (room.getTypeString().equals(oldRoomType) && !room.isBooked()) {
                room.updateRoom(newRoomType, capacity, pricePerNight);
            }
        }
    }

    public void removeRoomType(String roomType) {
        List<Room> roomsToRemove = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getTypeString().equals(roomType) && !room.isBooked()) {
                roomsToRemove.add(room);
            }
        }
        rooms.removeAll(roomsToRemove);
    }

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

    protected abstract Room createRoom(String roomType, int capacity, double pricePerNight, String number);

    private String generateRoomNumber() {
        return String.valueOf(rooms.size() + 1);
    }
}
