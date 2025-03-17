package com.app.repository;

import com.app.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // Find a booking by its booking number
    Optional<Booking> findByBookingNumber(String bookingNumber);
    
    
    
        // Find a booking by roomId and userId
        Optional<Booking> findByRoomIdAndUserId(Long roomId, Long userId);
    
        // Find all bookings for a specific user
        List<Booking> findByUserId(Long userId);
    
        // Find all bookings for a specific room
        List<Booking> findByRoomId(Long roomId);
}