package com.app.controller;

import com.app.model.Inventory;
import jakarta.mail.MessagingException;
import com.app.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Create or update inventory for a specific location
    @PostMapping("/create/{locationId}")
    public ResponseEntity<Inventory> createInventory(@PathVariable Long locationId, @RequestBody Inventory inventory) {
        inventory.setLocationId(locationId);  // Ensure locationId is set
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        return ResponseEntity.ok(savedInventory);
    }
    
    @PutMapping("/update/{id}/{locationId}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long id,
            @PathVariable Long locationId,
            @RequestBody Inventory inventory) {
        
        inventory.setId(id); // Ensure ID is set
        inventory.setLocationId(locationId); // Ensure locationId is set

        Inventory updatedInventory = inventoryService.updateInventory(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

 
    // Get all inventory items for a specific location
    @GetMapping("/all/{locationId}")
    public ResponseEntity<List<Inventory>> getAllInventory(@PathVariable Long locationId) {
        List<Inventory> inventoryList = inventoryService.getAllInventoryByLocation(locationId);
        return ResponseEntity.ok(inventoryList);
    }

    // Get inventory by ID for a specific location
    @GetMapping("/{locationId}/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long locationId, @PathVariable Long id) {
        Optional<Inventory> inventoryOptional = inventoryService.getInventoryByIdAndLocation(id, locationId);
        return inventoryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete inventory by ID for a specific location
    @DeleteMapping("/{locationId}/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long locationId, @PathVariable Long id) {
        if (inventoryService.deleteInventoryByLocation(id, locationId)) {
            return ResponseEntity.ok("Inventory item deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    // Update stock quantity for an inventory item at a specific location
    @PatchMapping("/{locationId}/{id}/updateStock")
    public ResponseEntity<Inventory> updateStockQuantity(@PathVariable Long locationId, @PathVariable Long id, @RequestParam int quantity) {
        Inventory updatedInventory = inventoryService.updateStockQuantityByLocation(id, locationId, quantity);
        if (updatedInventory != null) {
            return ResponseEntity.ok(updatedInventory);
        }
        return ResponseEntity.notFound().build();
    }

    // Get inventory items by category for a specific location
    @GetMapping("/category/{locationId}/{category}")
    public ResponseEntity<List<Inventory>> getInventoryByCategory(@PathVariable Long locationId, @PathVariable String category) {
        List<Inventory> inventoryList = inventoryService.getInventoryByCategoryAndLocation(category, locationId);
        return inventoryList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(inventoryList);
    }
     
    @PostMapping("/sendEmail/{locationId}/{id}")
    public ResponseEntity<String> sendEmail(@PathVariable Long locationId, @PathVariable Long id) {
        Optional<Inventory> inventoryOptional = inventoryService.getInventoryByIdAndLocation(id, locationId);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (inventory.getStockQuantity() < inventory.getMinThreshold()) {
                try {
                	String subject = "Urgent: Stock Level Alert for " + inventory.getName();

                	String body = "Dear Supplier,\n\n"
                	        + "We hope this message finds you well.\n\n"
                	        + "We are writing to inform you that the stock for the item **" + inventory.getName() + "** has fallen below the minimum threshold. This may affect our ability to fulfill upcoming orders and maintain optimal inventory levels.\n\n"
                	        + "Current Stock Level: **" + inventory.getStockQuantity() + "** units\n"
                	        + "Minimum Threshold: **" + inventory.getMinThreshold() + "** units\n\n"
                	        + "We kindly request that you review your inventory levels and restock the item as soon as possible to avoid any disruptions in supply. Your prompt attention to this matter is greatly appreciated.\n\n"
                	        + "If you require any further information or assistance, please do not hesitate to contact us.\n\n"
                	        + "Thank you for your cooperation and continued partnership.\n\n"
                	        + "Best regards,\n"
                	        + "Inventory Management Team\n"
                	        + "Glampinn Valley Hotels\n";
                	       

                    inventoryService.sendEmail(inventory.getSupplierEmail(), subject, body);
                    return ResponseEntity.ok("Email sent successfully to supplier.");
                } catch (MessagingException e) {
                    return ResponseEntity.internalServerError().body("Failed to send email.");
                }
            } else {
                return ResponseEntity.ok("Stock is above the minimum threshold. No email sent.");
            }
        }

        return ResponseEntity.notFound().build();
    }
}
