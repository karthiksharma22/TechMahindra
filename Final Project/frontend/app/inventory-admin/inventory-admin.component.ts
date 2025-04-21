import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 

interface Inventory {
  id: number;
  name: string;
  category: string;
  description: string;
  price: number;
  stockQuantity: number;
  locationId: number;
  minThreshold: number,
  supplierEmail: string
}

@Component({
  selector: 'app-inventory-admin',
  templateUrl: './inventory-admin.component.html',
  styleUrls: ['./inventory-admin.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule]
})
export class InventoryAdminComponent implements OnInit {
  inventoryForm: FormGroup;
  inventoryList: Inventory[] = [];
  selectedLocationId: number = 1;
  selectedInventory: Inventory | null = null;
  searchTerm: string = '';
  sortOption: string = '';
  isSendingEmail: boolean = false;

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.inventoryForm = this.fb.group({
      name: ['', Validators.required],
      category: ['', Validators.required],
      description: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      stockQuantity: [0, [Validators.required, Validators.min(0)]],
      minThreshold: [0, [Validators.required, Validators.min(0)]],
      supplierEmail: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.fetchInventory();
      // Add this line to check for low stock
  }

  fetchInventory(): void {
    this.http.get<Inventory[]>(`http://localhost:2026/api/inventory/all/${this.selectedLocationId}`).subscribe(
      (data) => {
        this.inventoryList = data;
        this.checkLowStockAndNotify();
      },
      (error) => {
        console.error('Error fetching inventory:', error);
      }
    );
  }

  // Check for low stock items and send email notifications
  checkLowStockAndNotify(): void {
    // Filter items with stock less than the minimum threshold
    
    const lowStockItems = this.inventoryList.filter(item => item.stockQuantity < item.minThreshold);
    
    this.isSendingEmail = true; 
    lowStockItems.forEach(item => {
      this.sendLowStockEmail(item);
    });
    this.isSendingEmail = false;
  }

  // Send email notification for low stock
  sendLowStockEmail(inventory: Inventory): void {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    
    // Make sure you're passing the locationId and inventory ID to the API
    this.http.post(
      `http://localhost:2026/api/inventory/sendEmail/${this.selectedLocationId}/${inventory.id}`,
      {},  // No body needed as everything is passed through URL
      { headers,responseType: 'text'   }
    ).subscribe(
      (response:string) => {
        console.log('Low stock email sent successfully:', response);
        
      },
      (error) => {
        console.error('Error sending low stock email:', error);
        
      }
    );
  }

  createOrUpdateInventory(): void {
    const inventoryData = this.inventoryForm.value;
    inventoryData.locationId = this.selectedLocationId;

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    if (this.selectedInventory) {
      // Update existing inventory
      this.http.put<Inventory>(
        `http://localhost:2026/api/inventory/update/${this.selectedInventory.id}/${this.selectedLocationId}`,
        inventoryData,
        { headers }
      ).subscribe(
        () => {
          this.fetchInventory(); // Refresh the inventory list
          this.resetForm(); // Reset the form
        },
        (error) => {
          console.error('Error updating inventory:', error);
        }
      );
    } else {
      // Create new inventory
      this.http.post<Inventory>(
        `http://localhost:2026/api/inventory/create/${this.selectedLocationId}`,
        inventoryData,
        { headers }
      ).subscribe(
        () => {
          this.fetchInventory(); // Refresh the inventory list
          this.resetForm(); // Reset the form
        },
        (error) => {
          console.error('Error creating inventory:', error);
        }
      );
    }
  }

  editInventory(inventory: Inventory): void {
    this.selectedInventory = inventory;
    this.inventoryForm.patchValue({
      name: inventory.name,
      category: inventory.category,
      description: inventory.description,
      price: inventory.price,
      stockQuantity: inventory.stockQuantity
    });
  }

  deleteInventory(id: number): void {
    this.http.delete(`http://localhost:2026/api/inventory/${this.selectedLocationId}/${id}`, { responseType: 'text' }).subscribe(
      (response) => {
        console.log(response); // Log the plain text response
        this.fetchInventory(); // Refresh the inventory list
      },
      (error) => {
        console.error('Error deleting inventory:', error);
      }
    );
  }

  updateStockQuantity(id: number, quantity: number): void {
    this.http.patch<Inventory>(`http://localhost:2026/api/inventory/${this.selectedLocationId}/${id}/updateStock?quantity=${quantity}`, {}).subscribe(
      (response) => {
        console.log('Stock quantity updated successfully:', response);
        this.fetchInventory(); // Refresh the inventory list
      },
      (error) => {
        console.error('Error updating stock quantity:', error);
      }
    );
  }

  resetForm(): void {
    this.inventoryForm.reset();
    this.selectedInventory = null;
  }

  // Get filtered inventory based on search and sorting options
  getFilteredInventory(): Inventory[] {
    let filteredList = this.inventoryList.filter(item =>
      item.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      item.category.toLowerCase().includes(this.searchTerm.toLowerCase())
    );

    if (this.sortOption === 'quantityAsc') {
      filteredList.sort((a, b) => a.stockQuantity - b.stockQuantity);
    } else if (this.sortOption === 'quantityDesc') {
      filteredList.sort((a, b) => b.stockQuantity - a.stockQuantity);
    } else if (this.sortOption === 'category') {
      filteredList.sort((a, b) => a.category.localeCompare(b.category));
    }

    return filteredList;
  }
}
