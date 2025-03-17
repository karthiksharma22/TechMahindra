package com.app.service;

import com.app.model.Booking;
import com.app.model.Room;
import com.app.repository.BookingRepository;
import com.app.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    // Create a new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
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
    public String completeCheckIn(String bookingNumber, String roomId) {
        Optional<Booking> bookingOptional = bookingRepository.findByBookingNumber(bookingNumber);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            if (roomId == null || roomId.isEmpty()) {
                return "Room information is missing in request";
            }

            // Fetch room details using the given roomId
            Room room = roomRepository.findById(Long.parseLong(roomId))
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
  public String completeCheckOut(Long locationId, String roomNumber, Long userId) {
      try {
          Long roomIdLong = Long.parseLong(roomNumber); 
          Optional<Booking> bookingOptional = bookingRepository.findByRoomIdAndUserId(roomIdLong, userId);

          if (!bookingOptional.isPresent()) {
              return "Booking not found";
          }

          Booking booking = bookingOptional.get();
          if (booking.getStatus() == Booking.BookingStatus.CHECKED_OUT) {
              return "Guest already checked out";
          }

          // Update booking status
          booking.setStatus(Booking.BookingStatus.CHECKED_OUT);
          bookingRepository.save(booking);

          // Update room status only if locationId is valid
          if (locationId != null) {
              List<Room> rooms = roomRepository.findByLocationIdAndRoomNumber(locationId, roomNumber);
              if (!rooms.isEmpty()) {
                  for (Room room : rooms) {
                      room.setOccStatus(Room.OccupancyStatus.AVAILABLE);
                      roomRepository.save(room);
                  }
              } else {
                  return "No rooms found at the given location";
              }
          } else {
              return "Invalid Location ID";
          }

          return "Checkout successful";
      } catch (NumberFormatException e) {
          return "Invalid room number format";
      }
  }
}