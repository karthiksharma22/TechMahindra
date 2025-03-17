package com.app.service;

import com.app.model.Inventory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.app.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private JavaMailSender mailSender;

    // Save or update inventory for a specific location
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // Get all inventory items for a specific location
    public List<Inventory> getAllInventoryByLocation(Long locationId) {
        return inventoryRepository.findByLocationId(locationId);
    }

    // Get inventory by ID and location
    public Optional<Inventory> getInventoryByIdAndLocation(Long inventoryId, Long locationId) {
        return inventoryRepository.findByIdAndLocationId(inventoryId, locationId);
    }

    // Delete inventory by ID and location
    public boolean deleteInventoryByLocation(Long inventoryId, Long locationId) {
        Optional<Inventory> inventory = inventoryRepository.findByIdAndLocationId(inventoryId, locationId);
        if (inventory.isPresent()) {
            inventoryRepository.delete(inventory.get());
            return true;
        }
        return false;
    }

    // Update stock quantity for a specific location
    public Inventory updateStockQuantityByLocation(Long inventoryId, Long locationId, int quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByIdAndLocationId(inventoryId, locationId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            inv.setStockQuantity(inv.getStockQuantity() + quantity);
            return inventoryRepository.save(inv);
        }
        return null;
    }

    // Get inventory items by category and location
    public List<Inventory> getInventoryByCategoryAndLocation(String category, Long locationId) {
        return inventoryRepository.findByCategoryAndLocationId(category, locationId);
    }
    
    public Inventory updateInventory(Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryRepository.findByIdAndLocationId(inventory.getId(), inventory.getLocationId());

        if (existingInventory.isPresent()) {
            Inventory updatedInventory = existingInventory.get();
            updatedInventory.setName(inventory.getName());
            updatedInventory.setCategory(inventory.getCategory());
            updatedInventory.setDescription(inventory.getDescription());
            updatedInventory.setPrice(inventory.getPrice());
            updatedInventory.setStockQuantity(inventory.getStockQuantity());
            
            return inventoryRepository.save(updatedInventory);
        } else {
            throw new RuntimeException("Inventory item not found for location: " + inventory.getLocationId());
        }
    }
    
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom("your-email@gmail.com");

        mailSender.send(message);
    }
}
