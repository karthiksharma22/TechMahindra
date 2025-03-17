import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-employee-checkin',
    standalone: true,
    imports: [CommonModule, FormsModule, HttpClientModule],
    templateUrl: './employee-checkin.component.html',
    styleUrls: ['./employee-checkin.component.css']
  })
  export class EmployeeCheckinComponent {
    isBooking = false;
    bookingNumber = '';
    bookingDetails: any = null;
    errorMessage = '';
  
    newBooking: any = {
      userId: '',
      locationId: '',
      roomId: '',
      userName: '',
      startDate: '',
      endDate: '',
      numberOfAdults: 1,
      numberOfChildren: 0,
      numberOfPeople: 1
    };
  
    constructor(private http: HttpClient) {}
  
    toggleView() {
      this.isBooking = !this.isBooking;
      this.resetForms();
    }
  
    verifyBooking() {
      this.http.get(`http://localhost:2026/api/bookings/bookingNumber/${this.bookingNumber}`)
        .subscribe({
          next: (response: any) => {
            this.bookingDetails = response;
            this.errorMessage = '';
          },
          error: (err) => {
            this.errorMessage = 'Booking not found or invalid booking number';
            this.bookingDetails = null;
          }
        });
    }
  
    createBooking() {
      this.http.post('http://localhost:2026/api/bookings', this.newBooking)
        .subscribe({
          next: (response: any) => {
            alert(`Booking created successfully! Booking Number: ${response.bookingNumber}`);
            this.resetForms();
            this.toggleView();
          },
          error: (err) => {
            this.errorMessage = 'Error creating booking: ' + err.message;
          }
        });
    }
    completeCheckIn() {
      if (!this.bookingDetails || !this.bookingDetails.bookingNumber) {
        this.errorMessage = 'Invalid booking details!';
        return;
      }
    
      const checkInData = {
        bookingNumber: this.bookingDetails.bookingNumber,
        roomId: this.bookingDetails.roomId
      };
    
      this.http.post('http://localhost:2026/api/bookings/checkin', checkInData, { responseType: 'json' })

        .subscribe({
          next: () => {
            alert('Check-in successful!');
            this.resetForms();
          },
          error: (err) => {
            this.errorMessage = 'Check-in failed: ' + err.message;
          }
        });
    }
    
  
    private resetForms() {
      this.bookingNumber = '';
      this.newBooking = {
        userId: '',
        locationId: '',
        roomId: '',
        userName: '',
        startDate: '',
        endDate: '',
        numberOfAdults: 1,
        numberOfChildren: 0,
        numberOfPeople: 1
      };
      this.bookingDetails = null;
      this.errorMessage = '';
    }
  
    updateNumberOfPeople() {
      this.newBooking.numberOfPeople = 
        this.newBooking.numberOfAdults + this.newBooking.numberOfChildren;
    }
  }