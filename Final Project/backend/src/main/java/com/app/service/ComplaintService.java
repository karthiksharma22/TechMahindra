package com.app.service;

import com.app.model.Complaint;
import com.app.model.UserModel;
import com.app.repository.ComplaintRepository;
import com.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint saveComplaint(Complaint complaint, Long userId) {
    	
        // Ensure User Exists Before Saving Complaint
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        // Set Created Date and User
        complaint.setCreatedAt(new Date());
        complaint.setUserId(userId);

        // Ensure Location ID is Set
        if (complaint.getLocationId() == null) {
            throw new RuntimeException("Location ID cannot be null");
        }

        // Ensure RegisteredBy is Set (GUEST or EMPLOYEE)
        if (complaint.getRegisteredBy() == null) {
            throw new RuntimeException("RegisteredBy field cannot be null");
        }

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getComplaintsByLocation(Long locationId) {
        return complaintRepository.findByLocationId(locationId);
    }

    public List<Complaint> getComplaintsByRegisteredBy(Complaint.RegisteredBy type) {
        return complaintRepository.findByRegisteredBy(type);
    }
}