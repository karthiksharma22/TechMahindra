import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute,RouterModule } from '@angular/router';


@Component({
  selector: 'app-booking-form',
  templateUrl: './bookingform.component.html',
  styleUrls: ['./bookingform.component.css'],
  imports: [ReactiveFormsModule,CommonModule,RouterModule],
})
export class BookingFormComponent implements OnInit {
  @Input() bookingData: any; // Receive data from parent
  bookingForm!: FormGroup; 
  hotelId: number | null = null;

  constructor(private fb: FormBuilder, private route:ActivatedRoute) {}

  user:any = null
  guestUser:any = {}
  bookingResponse: any = null;
  isLoading: boolean = false;
  
  roomId = 18;

  ngOnInit(): void {
    this.user = localStorage.getItem('userData')? JSON.parse(localStorage.getItem('userData')||"") : {}
    const checkinGuestData = localStorage.getItem('checkinGuest');
    this.guestUser = checkinGuestData && checkinGuestData.trim() !== "" ? JSON.parse(checkinGuestData) : {}; 
    
    this.bookingForm = this.fb.group({
      userName: [ this.guestUser?.username? this.guestUser?.username : this.bookingData?.userName || this.user?.username || ""],
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

    this.isLoading = true;
    const bookingData = this.bookingForm.value;
    bookingData.userId = this.user?.userId||this.guestUser?.userId
    bookingData.roomId = this.roomId+1;
    this.roomId += 1
    bookingData.locationId = this.hotelId
    console.log(bookingData)
    console.log(this.user.email || this.guestUser.email)
  fetch(`http://localhost:2026/api/bookings/${this.user.email || this.guestUser.email}`, {  
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
    
    this.bookingResponse = data;
    localStorage.removeItem('checkinGuest')
    this.isLoading = false;
  })
  .catch(error => {
    console.error('Error:', error);
    this.isLoading = false;
    alert('Booking failed. Please try again.');
  });
  }



  copyToClipboard() {
    if (this.bookingResponse) {
      navigator.clipboard.writeText(this.bookingResponse.bookingNumber);
    }
  }
}
