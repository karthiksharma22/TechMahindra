package com.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "room_number", nullable = false, length = 50)
    private String roomNumber;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType type;

    @Column(name = "has_sea_view", nullable = false)
    private boolean hasSeaView;

    @Enumerated(EnumType.STRING)
    @Column(name = "occ_status", nullable = false)
    private OccupancyStatus occStatus;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac_non", nullable = false)
    private AcType acNon;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "clean_status", nullable = false)
    private CleanStatus cleanStatus;

    @Column(name = "times_booked", columnDefinition = "INT DEFAULT 0")
    private int timesBooked = 0;

    @Column(name = "price", nullable = false, precision = 10)
    private double price;

    // Enum Definitions
    public enum RoomType {
        STANDARD, DELUXE, SUITE
    }

    public enum OccupancyStatus {
        AVAILABLE, BOOKED, SELECTED, NOT_AVAILABLE
    }

    public enum AcType {
        AC, NON_AC
    }

    public enum CleanStatus {
        GREEN, ORANGE, RED
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public RoomType getType() { return type; }
    public void setType(RoomType type) { this.type = type; }

    public boolean isHasSeaView() { return hasSeaView; }
    public void setHasSeaView(boolean hasSeaView) { this.hasSeaView = hasSeaView; }

    public OccupancyStatus getOccStatus() { return occStatus; }
    public void setOccStatus(OccupancyStatus occStatus) { this.occStatus = occStatus; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public AcType getAcNon() { return acNon; }
    public void setAcNon(AcType acNon) { this.acNon = acNon; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public CleanStatus getCleanStatus() { return cleanStatus; }
    public void setCleanStatus(CleanStatus cleanStatus) { this.cleanStatus = cleanStatus; }

    public int getTimesBooked() { return timesBooked; }
    public void setTimesBooked(int timesBooked) { this.timesBooked = timesBooked; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
