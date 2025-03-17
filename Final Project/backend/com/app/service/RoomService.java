package com.app.service;

import com.app.model.Room;
import com.app.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Create a Room
    public Room createRoom(Long locationId, Room room) {
        room.setLocationId(locationId);
        return roomRepository.save(room);
    }

    // Get Room by ID
    public Optional<Room> getRoomById(Long locationId, Long id) {
        return roomRepository.findById(id)
                .filter(room -> room.getLocationId().equals(locationId));
    }

    // Get All Rooms for a Location
    public List<Room> getAllRooms(Long locationId) {
        return roomRepository.findByLocationId(locationId);
    }

    // Update Room
    public Room updateRoom(Long locationId, Long id, Room roomDetails) {
        return roomRepository.findById(id)
                .filter(room -> room.getLocationId().equals(locationId))
                .map(room -> {
                    if (roomDetails.getRoomNumber() != null) {
                        room.setRoomNumber(roomDetails.getRoomNumber());
                    }
                    if (roomDetails.getCapacity() > 0) {
                        room.setCapacity(roomDetails.getCapacity());
                    }
                    if (roomDetails.getAcNon() != null) {
                        room.setAcNon(roomDetails.getAcNon());
                    }
                    if (roomDetails.getCleanStatus() != null) {
                        room.setCleanStatus(roomDetails.getCleanStatus());
                    }
                    if (roomDetails.getOccStatus() != null) {
                        room.setOccStatus(roomDetails.getOccStatus());
                    }
                    if (roomDetails.getPrice() > 0) {
                        room.setPrice(roomDetails.getPrice());
                    }
                    return roomRepository.save(room);
                }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    // Delete Room
    public void deleteRoom(Long locationId, Long id) {
        roomRepository.findById(id)
                .filter(room -> room.getLocationId().equals(locationId))
                .ifPresentOrElse(roomRepository::delete,
                        () -> { throw new RuntimeException("Room not found"); });
    }

    // Filter by Room Number
    public List<Room> getRoomsByRoomNumber(Long locationId, String roomNumber) {
        return roomRepository.findByLocationIdAndRoomNumber(locationId, roomNumber);
    }

    // Filter by Capacity
    public List<Room> getRoomsByCapacity(Long locationId, int capacity) {
        return roomRepository.findByLocationIdAndCapacity(locationId, capacity);
    }

    // Filter by AC Type
    public List<Room> getRoomsByAcNon(Long locationId, Room.AcType acNon) {
        return roomRepository.findByLocationIdAndAcNon(locationId, acNon);
    }

    // Filter by Clean Status
    public List<Room> getRoomsByCleanStatus(Long locationId, Room.CleanStatus cleanStatus) {
        return roomRepository.findByLocationIdAndCleanStatus(locationId, cleanStatus);
    }

    // Filter by Occupancy Status
    public List<Room> getRoomsByOccStatus(Long locationId, Room.OccupancyStatus occStatus) {
        return roomRepository.findByLocationIdAndOccStatus(locationId, occStatus);
    }

    // Filter by Price (Less than or Equal)
    public List<Room> getRoomsByPrice(Long locationId, double price) {
        return roomRepository.findByLocationIdAndPriceLessThanEqual(locationId, price);
    }
}
