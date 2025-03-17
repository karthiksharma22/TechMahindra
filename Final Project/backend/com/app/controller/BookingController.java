package com.app.controller;

import com.app.model.Booking;
import com.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")

public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private JavaMailSender emailSender;

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo("21eg105f29@anurag.edu.in");
        message.setSubject("Booking Successful");
        message.setText("Dear Guest, your check-in has been successfully completed. Thank you for choosing our service! \n This is your Booking ID"+savedBooking.getBookingNumber());

        emailSender.send(message);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get booking by Booking Number

    @GetMapping("/bookingNumber/{bookingNumber}")
    public ResponseEntity<Object> getBookingByNumber(@PathVariable String bookingNumber) {
        Optional<Booking> booking = bookingService.getBookingByNumber(bookingNumber);
        
        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    // Check-in Guest (Fix: Use bookingNumber instead of full Booking object)
    @PostMapping("/checkin")
    public ResponseEntity<Map<String, String>> checkInGuest(@RequestBody Map<String, String> request) {
        String bookingNumber = request.get("bookingNumber");
        String roomId = request.get("roomId");

        if (bookingNumber == null || roomId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Missing bookingNumber or roomId"));
        }

        String response = bookingService.completeCheckIn(bookingNumber, roomId);

        if ("Check-in successful".equals(response)) {
        	
            return ResponseEntity.ok(Map.of("message", "Check-in successful"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", response));
        }
    }
    
    @PostMapping("/checkout")
    public ResponseEntity<String> checkOutGuest(@RequestBody Map<String, String> request) {
        String roomNumber = request.get("roomNumber");
        String userIdStr = request.get("userId");
        String locationIdStr = request.get("locationId");

        // Validate required fields
        if (roomNumber == null || userIdStr == null || locationIdStr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields: roomNumber, userId, or locationId");
        }

        try {
            Long userId = Long.parseLong(userIdStr);
            Long locationId = Long.parseLong(locationIdStr);

            String response = bookingService.completeCheckOut(locationId, roomNumber, userId);
            return response.equalsIgnoreCase("Checkout successful") 
                    ? ResponseEntity.ok(response) 
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format for userId or locationId");
        }
    }
}