import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  imports:[CommonModule]
})
export class CheckoutComponent implements OnInit {
  roomNumber: string='';
  userId: number=4;
  locationId: number=1;
  message: string='';

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    // Initialization logic if needed
  }

  checkout() {
    const checkoutData = {
      roomNumber: this.roomNumber,
      userId: this.userId,
      locationId: this.locationId
    };

    this.http.post('/api/bookings/checkout', checkoutData).subscribe(
      (response: any) => {
        this.message = response;
        if (this.message === 'Checkout successful') {
          this.autoAssignTask();
        }
      },
      (error) => {
        this.message = error.error;
      }
    );
  }

  autoAssignTask() {
    // After successful checkout, auto-assign the task
    const taskData = {
      locationId: this.locationId,
      taskName: 'Cleaning', // Example task name
      description: 'Task assigned after checkout',
      status: 'Pending'
    };

    this.http.post(`/api/tasks/assign-cleaning-task/${this.locationId}`, taskData).subscribe(
      (response: any) => {
        console.log('Task assigned successfully:', response);
      },
      (error) => {
        console.error('Error assigning task:', error);
      }
    );
  }
}
