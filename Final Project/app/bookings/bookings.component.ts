import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-booking',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css'],
  imports:[CommonModule,FormsModule]
})
export class BookingComponent implements OnInit {
  bookings: any[] = [];
  apiUrl = 'http://localhost:2026/api/bookings';
  isEditing = false;
  selectedBookingId: number | null = null;

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

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.http.get<any[]>(this.apiUrl).subscribe(data => {
      this.bookings = data;
    });
  }

  getBookingById(id: number): void {
    this.http.get<any>(`${this.apiUrl}/${id}`).subscribe(data => {
      console.log('Booking Details:', data);
    });
  }

  getBookingByNumber(bookingNumber: string): void {
    this.http.get<any>(`${this.apiUrl}/bookingNumber/${bookingNumber}`).subscribe(data => {
      console.log('Booking Details:', data);
    });
  }

  addBooking(): void {
    this.http.post(this.apiUrl, this.newBooking).subscribe(() => {
      this.loadBookings();
      this.resetForm();
    });
  }

  editBooking(booking: any): void {
    this.isEditing = true;
    this.selectedBookingId = booking.id;
    this.newBooking = { ...booking };
  }

  updateBooking(): void {
    if (this.selectedBookingId) {
      this.http.put(`${this.apiUrl}/${this.selectedBookingId}`, this.newBooking).subscribe(() => {
        this.loadBookings();
        this.resetForm();
      });
    }
  }

  deleteBooking(id: number): void {
    if (confirm('Are you sure you want to delete this booking?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe(() => {
        this.loadBookings();
      });
    }
  }

  checkInGuest(bookingNumber: string, roomId: string): void {
    this.http.post(`${this.apiUrl}/checkin`, { bookingNumber, roomId }).subscribe(response => {
      console.log('Check-in Response:', response);
      this.loadBookings();
    });
  }

  resetForm(): void {
    this.isEditing = false;
    this.selectedBookingId = null;
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
  }
}
