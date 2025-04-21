package com.app.controller;

import com.app.model.Booking;
import com.app.repository.BookingRepository;
import com.app.repository.UserRepository;

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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    // Create a new booking
    @PostMapping("/{email}") 
    public ResponseEntity<Booking> createBooking(@PathVariable String email,@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Booking Successful");
        message.setText("Dear Guest, your Booking has been successfully completed. Thank you for choosing our service! \n\n This is your Booking ID :   "+savedBooking.getBookingNumber());
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
    public ResponseEntity<String> checkInGuest(@RequestBody Map<String, String> request) {
        String bookingNumber = request.get("bookingNumber");

        if (bookingNumber == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing bookingNumber");
        }

        String response = bookingService.completeCheckIn(bookingNumber);
        return response.equals("Check-in successful")
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } 
     
    @PostMapping("/checkout")
    public ResponseEntity<String> checkOutGuest(@RequestBody Map<String, String> request) {
        String roomNumber = request.get("roomNumber");
        String email = request.get("email");

        if (roomNumber == null || email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields: roomNumber or email");
        }

        try {
            // Fetch userId from UserRepository
        	Optional<Long> userIdOpt = userRepository.findUserIdByEmail(email);
            if (userIdOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for email: " + email);
            }
            Long userId = userIdOpt.get();
            	System.out.println("this is user id "+userId);
            	
            // Fetch locationId from BookingRepository
            Optional<Long> locationIdOpt = bookingRepository.getLocationIdByUserId(userId);
            
            if (locationIdOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Checkin Pending! Please Check in! Checkout Not Possible" + userId);
            } 
            Long locationId = locationIdOpt.get();
System.out.println("This is location id "+locationId);
            // Proceed with checkout
            String response = bookingService.completeCheckOut(locationId, roomNumber, email);
            System.out.println("this is response"+response);
            return response.equalsIgnoreCase("Checkout successful") 
                    ? ResponseEntity.ok(response) 
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing checkout: " + e.getMessage());
        }
    }
}