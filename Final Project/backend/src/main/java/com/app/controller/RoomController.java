package com.app.controller;

import com.app.model.Room;
import com.app.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations/{locationId}/rooms")
public class RoomController { 

    private final RoomService roomService;
 
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Create a Room
    @PostMapping
    public ResponseEntity<Room> createRoom(@PathVariable Long locationId, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(locationId, room));
    }

    // Get Room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long locationId, @PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(locationId, id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get All Rooms for a Location
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(@PathVariable Long locationId) {
        return ResponseEntity.ok(roomService.getAllRooms(locationId));
    }

    // Update Room
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long locationId, @PathVariable Long id, @RequestBody Room roomDetails) {
        try {
            return ResponseEntity.ok(roomService.updateRoom(locationId, id, roomDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Room
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long locationId, @PathVariable Long id) {
        roomService.deleteRoom(locationId, id);
        return ResponseEntity.noContent().build();
    }

    // Filter by Room Number
    @GetMapping("/filter/roomNumber/{roomNumber}")
    public ResponseEntity<List<Room>> getRoomsByRoomNumber(@PathVariable Long locationId, @PathVariable String roomNumber) {
        return ResponseEntity.ok(roomService.getRoomsByRoomNumber(locationId, roomNumber));
    }

    // Filter by Capacity
    @GetMapping("/filter/capacity/{capacity}")
    public ResponseEntity<List<Room>> getRoomsByCapacity(@PathVariable Long locationId, @PathVariable int capacity) {
        return ResponseEntity.ok(roomService.getRoomsByCapacity(locationId, capacity));
    }

    // Filter by AC Type
    @GetMapping("/filter/acType/{acNon}")
    public ResponseEntity<List<Room>> getRoomsByAcNon(@PathVariable Long locationId, @PathVariable Room.AcType acNon) {
        return ResponseEntity.ok(roomService.getRoomsByAcNon(locationId, acNon));
    }

    // Filter by Clean Status
    @GetMapping("/filter/cleanStatus/{cleanStatus}")
    public ResponseEntity<List<Room>> getRoomsByCleanStatus(@PathVariable Long locationId, @PathVariable Room.CleanStatus cleanStatus) {
        return ResponseEntity.ok(roomService.getRoomsByCleanStatus(locationId, cleanStatus));
    }

    // Filter by Occupancy Status
    @GetMapping("/filter/occStatus/{occStatus}")
    public ResponseEntity<List<Room>> getRoomsByOccStatus(@PathVariable Long locationId, @PathVariable Room.OccupancyStatus occStatus) {
        return ResponseEntity.ok(roomService.getRoomsByOccStatus(locationId, occStatus));
    }

    // Filter by Price (Less than or Equal)
    @GetMapping("/filter/price/{price}")
    public ResponseEntity<List<Room>> getRoomsByPrice(@PathVariable Long locationId, @PathVariable double price) {
        return ResponseEntity.ok(roomService.getRoomsByPrice(locationId, price));
    }
}
