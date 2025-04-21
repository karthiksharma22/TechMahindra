package com.app.repository;

import com.app.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    List<Inventory> findByLocationId(Long locationId);

    Optional<Inventory> findByIdAndLocationId(Long inventoryId, Long locationId);

    List<Inventory> findByCategoryAndLocationId(String category, Long locationId);
}
