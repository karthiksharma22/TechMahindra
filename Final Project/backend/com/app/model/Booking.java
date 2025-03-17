package com.app.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {

    public enum BookingStatus {
        PENDING,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String bookingNumber;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long locationId;

    @Column(nullable = false)
    private long roomId;

    @Column(nullable = false)
    private String userName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int numberOfAdults;

    @Column(nullable = false)
    private int numberOfChildren;

    @Column(nullable = false)
    private int numberOfPeople;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING; // Default status

    @PrePersist
    public void generateBookingNumber() {
        if (this.bookingNumber == null || this.bookingNumber.isEmpty()) {
            this.bookingNumber = "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    // Default Constructor
    public Booking() {
        this.bookingNumber = "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Parameterized Constructor
    public Booking(long userId, long locationId, long roomId, String userName,
                   Date startDate, Date endDate, int numberOfAdults, int numberOfChildren, int numberOfPeople) {
        this.userId = userId;
        this.locationId = locationId;
        this.roomId = roomId;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfPeople = numberOfPeople;
        this.status = BookingStatus.PENDING; // Default status
        this.bookingNumber = "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookingNumber() { return bookingNumber; }
    public void setBookingNumber(String bookingNumber) { this.bookingNumber = bookingNumber; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getLocationId() { return locationId; }
    public void setLocationId(long locationId) { this.locationId = locationId; }

    public long getRoomId() { return roomId; }
    public void setRoomId(long roomId) { this.roomId = roomId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public int getNumberOfAdults() { return numberOfAdults; }
    public void setNumberOfAdults(int numberOfAdults) { this.numberOfAdults = numberOfAdults; }

    public int getNumberOfChildren() { return numberOfChildren; }
    public void setNumberOfChildren(int numberOfChildren) { this.numberOfChildren = numberOfChildren; }

    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
