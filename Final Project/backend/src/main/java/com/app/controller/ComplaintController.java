	package com.app.controller;
	
	
	import com.app.model.Complaint;
	import com.app.service.ComplaintService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	
	import java.util.List;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
	@RestController
	@RequestMapping("/api/complaints")
	public class ComplaintController {
	
	    @Autowired
	    private ComplaintService complaintService;
	
	    
	    @PostMapping("/create/{userId}")
	    public Complaint createComplaint(@PathVariable Long userId, @RequestBody Complaint complaint) {
	    	System.out.println("hello");
	        return complaintService.saveComplaint(complaint, userId);
	    }
	
	    // Get all complaints (GET request)
	    @GetMapping("/all")
	    public ResponseEntity<List<Complaint>> getAllComplaints() {
	        return ResponseEntity.ok(complaintService.getAllComplaints());
	    }
	
	    // Get complaints by location (GET request)
	    @GetMapping("/location/{locationId}")
	    public ResponseEntity<List<Complaint>> getComplaintsByLocation(@PathVariable Long locationId) {
	        return ResponseEntity.ok(complaintService.getComplaintsByLocation(locationId));
	    }
	
	    // Get complaints by registeredBy (GUEST/EMPLOYEE) (GET request)
	    @GetMapping("/registeredBy/{type}")
	    public ResponseEntity<List<Complaint>> getComplaintsByRegisteredBy(@PathVariable String type) {
	        return ResponseEntity.ok(complaintService.getComplaintsByRegisteredBy(Complaint.RegisteredBy.valueOf(type.toUpperCase())));
	    }
	}