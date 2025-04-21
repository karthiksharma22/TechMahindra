import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  standalone: true,
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  imports: [CommonModule, FormsModule]
})
export class CheckoutComponent implements OnInit {
  checkoutRoomNumber: string = '';
  checkoutemail: string = '';
  checkoutErrorMessage: string = '';
  checkoutSuccessMessage: string = '';
  isLoading: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {}

  handleCheckout() {
    if (!this.checkoutRoomNumber || !this.checkoutemail) {
      this.checkoutErrorMessage = 'Please enter valid details for checkout.';
      return;
    }

    this.isLoading = true;

    this.http.post('http://localhost:2026/api/bookings/checkout', {
      roomNumber: this.checkoutRoomNumber,
      email: this.checkoutemail
    }, { responseType: 'text' }).subscribe({
      next: (checkoutResponse) => {
        console.log('Checkout response:', checkoutResponse);
        this.checkoutSuccessMessage = 'Checkout successful!';
        this.checkoutErrorMessage = '';

       
        const taskData = {
          title: `Clean Room ${this.checkoutRoomNumber}`,
          description: `Ensure room ${this.checkoutRoomNumber} is cleaned and ready for use.`,
          status: "PENDING",
          dueDate: "2025-03-20"
        };

       
        this.http.post(`http://localhost:2026/api/tasks/assign-auto-task/1/cleaner`, taskData).subscribe({
          next: (taskResponse) => {
            console.log('Auto task assigned:', taskResponse);
            this.checkoutSuccessMessage = "Check Out Completed! and Cleaning Task Assigned for Room"
          },
          error: (taskError) => {
            console.error('Error assigning task:', taskError);
            this.checkoutErrorMessage = 'Task assignment failed: ' + (taskError.error?.message || taskError.message);
          }
        });
      },
      error: (err) => {
        console.error('Checkout failed:', err);
        this.checkoutErrorMessage = 'Checkout failed: ' + (err.error || err.message);
      },
      complete: () => this.isLoading = false
    });
  }
}
