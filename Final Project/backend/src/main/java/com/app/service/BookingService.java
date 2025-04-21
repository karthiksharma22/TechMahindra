package com.app.service;

import com.app.model.Booking;
import com.app.model.Room;
import com.app.repository.BookingRepository;
import com.app.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository,UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository =userRepository;
    }

    // Create a new booking
    public Booking createBooking(Booking booking) {
    	
    	Long roomId = booking.getRoomId();
        
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }

        // Fetch room details using roomId
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check if the room is available before booking
        if (room.getOccStatus() != Room.OccupancyStatus.AVAILABLE) {
            throw new RuntimeException("Room is not available for booking");
        }

        // Proceed with booking
        Booking savedBooking = bookingRepository.save(booking);

        // Update Room Status to NOT_AVAILABLE after successful booking
        room.setOccStatus(Room.OccupancyStatus.NOT_AVAILABLE);
        roomRepository.save(room); 
        return savedBooking;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get a booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Get a booking by Booking Number
    public Optional<Booking> getBookingByNumber(String bookingNumber) {
        return bookingRepository.findByBookingNumber(bookingNumber);
    }

    // Fix: Use bookingNumber instead of bookingId
    @Transactional
    public String completeCheckIn(String bookingNumber) {
        Optional<Booking> bookingOptional = bookingRepository.findByBookingNumber(bookingNumber);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            // Fetch the room ID from the booking
            Long roomId = booking.getRoomId(); // Assuming Booking has a getRoomId() method
            
            if (roomId == null) {
                return "Room information is missing in booking details";
            }

            // Fetch room details using the roomId
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            // Update Room Status
            room.setOccStatus(Room.OccupancyStatus.NOT_AVAILABLE);
            roomRepository.save(room);
 
            // Update Booking Status
            booking.setStatus(Booking.BookingStatus.CHECKED_IN);
            bookingRepository.save(booking);

            return "Check-in successful";
        } else {
            return "Booking not found";
        }
    }
    
 // Check-out logic
    public String completeCheckOut(Long locationId, String roomNumber, String email) {
        try {
        	List<Room> rooms =  roomRepository.findByLocationIdAndRoomNumber(locationId,roomNumber);
        	System.out.println("rooms are fetched 1");
            Long roomIdLong = rooms.get(0).getId();
            System.out.println(roomIdLong+" these are the room di long");
            // Fetch userId using email
            Optional<Long> userIdOpt = userRepository.findUserIdByEmail(email);
            System.out.println("usedidopt");
            if (userIdOpt.isEmpty()) {
                return "User not found for email: " + email;
            } 
            Long userId = userIdOpt.get();
            System.out.println(userId+" these are the user di long");
            
            // Fetch booking using roomId and userId
            Optional<Booking> bookingOptional = bookingRepository.findByRoomIdAndUserId(roomIdLong, userId);
            if (bookingOptional.isEmpty()) {
                return "Booking not found";
            }
            System.out.println("bookingopt");
            Booking booking = bookingOptional.get();
            if (booking.getStatus() == Booking.BookingStatus.CHECKED_OUT) {
                return "Guest already checked out";
            }
            System.out.println("hello");
            // Update booking status
            booking.setStatus(Booking.BookingStatus.CHECKED_OUT);
            bookingRepository.save(booking);
            System.out.println("bookingopt 3");
            // Update room status only if locationId is valid
            if (locationId != null) {
            	System.out.println("inside");
                List<Room> rooms1 = roomRepository.findByLocationIdAndRoomNumber(locationId, roomNumber);
                System.out.println("rooms fetched");
                if (!rooms1.isEmpty()) {
                    for (Room room1 : rooms1) {
                        room1.setOccStatus(Room.OccupancyStatus.AVAILABLE);
                        roomRepository.save(room1);
                    }
                } else {
                    return "No rooms found at the given location";
                }
            } else {
                return "Invalid Location ID";
            }

            return "Checkout successful";
        } catch (NumberFormatException e) {
            return "Invalid room number format: " + roomNumber;
        } catch (Exception e) {
            return "Error processing checkout: " + e.getMessage();
        }
    }
}