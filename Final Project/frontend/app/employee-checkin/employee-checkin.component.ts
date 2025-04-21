import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-checkin',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './employee-checkin.component.html',
  styleUrls: ['./employee-checkin.component.css']
})
export class EmployeeCheckinComponent {
  activeTab: string = 'check-in';
  isLoading: boolean = false;

  // Check-in Properties
  checkInBookingNumber: string = '';
  bookingDetails: any = null; // Added missing property
  checkInErrorMessage: string = '';
  checkInSuccessMessage: string = '';

  // New Booking Properties
  searchPhone: string = '';
  searchEmail: string = '';
  isExistingUser: boolean = true;
  userFound: boolean = false;
  user: any = { name: '', phone: '', email: '' };
  newBookingErrorMessage: string = '';
  newBookingSuccessMessage: string = '';

  // Checkout Properties
  checkoutRoomNumber: string = '';
  checkoutUserPhone: string = '';
  checkoutErrorMessage: string = '';
  checkoutSuccessMessage: string = '';

  constructor(private http: HttpClient, private router:Router) {}

  // Fixed missing method
  setActiveTab(tab: string) {
    this.activeTab = tab;
    this.resetMessages();
    this.bookingDetails = null; // Clear previous booking details
  }

  verifyBooking() {
    if (!this.checkInBookingNumber) {
      this.checkInErrorMessage = 'Please enter a valid booking number';
      return;
    }

    this.isLoading = true;
    // Fixed template literal syntax
    this.http.get(`http://localhost:2026/api/bookings/bookingNumber/${this.checkInBookingNumber}`)
      .subscribe({
        next: (response: any) => {
          this.bookingDetails = response;
          this.checkInErrorMessage = '';
          if (response.status === 'CHECKED_IN') {
            this.checkInErrorMessage = 'This booking is already checked in';
          }
        },
        error: (err) => {
          this.checkInErrorMessage = 'Verification failed: ' + (err.error?.message || err.message);
          this.bookingDetails = null;
        },
        complete: () => this.isLoading = false
      });
  }

  completeCheckIn() {
    if (!this.bookingDetails || this.bookingDetails.status === 'CHECKED_IN') return;

    this.isLoading = true;
    this.http.post(`http://localhost:2026/api/bookings/checkin`, {
      bookingNumber: this.checkInBookingNumber,
    },{ responseType: 'text' }).subscribe({
      next: () => {
        this.checkInSuccessMessage = 'Check-in completed successfully!';
        this.bookingDetails.status = 'CHECKED_IN'; // Update local status
        this.checkInErrorMessage = '';
      },
      error: (err) => {
        this.checkInErrorMessage = 'Check-in failed: ' + (err.error?.message || err.message);
      },
      complete: () => this.isLoading = false
    });
  }

  // Updated checkout handler to match template
  handleCheckout() {
    if (!this.checkoutRoomNumber || !this.checkoutUserPhone) {
      this.checkoutErrorMessage = 'Please enter valid details for checkout.';
      return;
    }

    this.isLoading = true;
    this.http.post('http://localhost:2026/api/bookings/checkout', {
      roomNumber: this.checkoutRoomNumber,
      phoneNumber: this.checkoutUserPhone
    }).subscribe({
      next: () => {
        this.checkoutSuccessMessage = 'Checkout successful!';
        this.checkoutErrorMessage = '';
      },
      error: (err) => {
        this.checkoutErrorMessage = 'Checkout failed: ' + err.message;
      },
      complete: () => this.isLoading = false
    });
  }

  private resetMessages() {
    this.checkInErrorMessage = '';
    this.checkInSuccessMessage = '';
    this.newBookingErrorMessage = '';
    this.newBookingSuccessMessage = '';
    this.checkoutErrorMessage = '';
    this.checkoutSuccessMessage = '';
  }
  toggleUserType(isExisting: boolean) {
    this.isExistingUser = isExisting;
    this.userFound = false;
    this.user = { name: '', phone: '', email: '' };
  }

  searchUser() {
    this.isLoading = true;
    this.http.get(`http://localhost:2026/api/users/email/${this.searchEmail}`)
      .subscribe({
        next: (response: any) => {
          if (response) {
            this.user = response;
            this.userFound = true;
            localStorage.setItem("checkinGuest",JSON.stringify(this.user))
          } else {
            this.newBookingErrorMessage = 'User not found.';
          }
        },
        error: () => {
          this.newBookingErrorMessage = 'Error searching user.';
        },
        complete: () => this.isLoading = false
      });
  }

  createBooking() {
    this.isLoading = true;
    if (!this.userFound) {
      this.http.post(`http://localhost:2026/api/users/create`, this.user, { responseType: 'json' })
        .subscribe({
          next: (createdUser: any) => {
            this.user = createdUser; // Store newly created user
            localStorage.setItem("checkinGuest",JSON.stringify(this.user)) 
            this.navigateToBooking(); // Proceed to booking
          },
          error: (err) => {
            this.newBookingErrorMessage = 'User creation failed: ' + (err.error?.message || err.message);
            this.isLoading = false;
          }
        });
    }else {
      // If user already exists, directly proceed to booking
      this.navigateToBooking();
    }
  }
  
  // New helper function to navigate to bookings
  private navigateToBooking() {
    this.router.navigate(['/bookingform',1]);
    this.isLoading = false;
  }
    
}