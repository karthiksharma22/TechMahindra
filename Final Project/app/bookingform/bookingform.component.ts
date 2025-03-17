import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-booking-form',
  templateUrl: './bookingform.component.html',
  styleUrls: ['./bookingform.component.css'],
  imports: [ReactiveFormsModule],
})
export class BookingFormComponent implements OnInit {
  @Input() bookingData: any; // Receive data from parent
  bookingForm!: FormGroup; 
  hotelId: number | null = null;

  constructor(private fb: FormBuilder, private route:ActivatedRoute) {}

  user:any = null

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('userData')||"")
    this.bookingForm = this.fb.group({
      userName: [this.bookingData?.userName || this.user?.username || ""],
      numberOfPeople: [this.bookingData?.numberOfPeople || 0],
      startDate: [this.bookingData?.startDate || ''],
      endDate: [this.bookingData?.endDate || ''],
      numberOfAdults: [this.bookingData?.numberOfAdults || 0],
      childrenCount: [this.bookingData?.childrenCount || 0]
    });

    this.route.paramMap.subscribe(params => {
      this.hotelId = Number(params.get('id')); 
      console.log('Hotel ID:', this.hotelId);
    });
  }

  onSubmit() {
    const bookingData = this.bookingForm.value;
    bookingData.userId = JSON.parse(localStorage.getItem('userData')||"")?.userId
    bookingData.roomId = 105
    bookingData.locationId = this.hotelId
    console.log(bookingData)
  fetch('http://localhost:2026/api/bookings', {  
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(bookingData)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Failed to create booking');
    }
    return response.json();
  })
  .then(data => {
    console.log('Booking Created Successfully:', data);
    alert('Booking confirmed!');
  })
  .catch(error => {
    console.error('Error:', error);
    alert('Booking failed. Please try again.');
  });
  }

   response = {
    "id": 2,
    "bookingNumber": "BOOK-F8C19245",
    "userId": 1,
    "locationId": 5,
    "roomId": 12,
    "userName": "John Doe",
    "startDate": "2025-03-20T00:00:00.000+00:00",
    "endDate": "2025-03-25T00:00:00.000+00:00",
    "numberOfAdults": 2,
    "numberOfChildren": 1,
    "numberOfPeople": 3,
    "status": "PENDING"
  }

  copyToClipboard() {
    navigator.clipboard.writeText(this.response.bookingNumber).then(() => {
      alert('Copied: ' + this.response.bookingNumber);
    }).catch(err => {
      console.error('Error copying to clipboard', err);
    });
  }
}
