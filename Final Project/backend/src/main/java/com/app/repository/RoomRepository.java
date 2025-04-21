package com.app.repository;

import com.app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByLocationId(Long locationId);

    List<Room> findByLocationIdAndRoomNumber(Long locationId, String roomNumber);

    List<Room> findByLocationIdAndCapacity(Long locationId, int capacity);

    List<Room> findByLocationIdAndAcNon(Long locationId, Room.AcType acNon);

    List<Room> findByLocationIdAndCleanStatus(Long locationId, Room.CleanStatus cleanStatus);

    List<Room> findByLocationIdAndOccStatus(Long locationId, Room.OccupancyStatus occStatus);

    List<Room> findByLocationIdAndPriceLessThanEqual(Long locationId, double price);
}
