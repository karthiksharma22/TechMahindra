package com.app.repository;

import com.app.model.Complaint;
import com.app.model.Complaint.RegisteredBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByLocationId(Long locationId);
    List<Complaint> findByRegisteredBy(RegisteredBy registeredBy);
}