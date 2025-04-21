import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router,ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-bookrooms',
  imports: [CommonModule,RouterModule],
  templateUrl: './bookrooms.component.html',
  styleUrl: './bookrooms.component.css'
})
export class BookroomsComponent {
  constructor(private router: Router,private route:ActivatedRoute) {} 
  hotelId: number | null = null;
  rooms = [
    {
      image:
        'https://cdn.sanity.io/images/ocl5w36p/prod3/001112f182df62934565a53070caea6ccca82d81-1400x1120.jpg',
      type: 'Executive Room',
      size: '2500sft',
      guests: 'Upto 3 guests',
      beds: '2+ Beds',
      offers: [
        {
          title: 'Happy Stays - Limited Time Offer',
          description: 'Indulge in moments of pure bliss with the one you hold dear',
          details: [
            'Rate is inclusive of Breakfast, Internet and VAT',
            'Access to the gym and J Wellness Circle',
            '10% discount on Food and Beverages',
          ],
          memberRate: 300,
          standardRate: 400,
        },
        {
          title: 'Best Available Rate- Room Only',
          description: 'Indulge in moments of pure bliss with the one you hold dear',
          details: [
            'Accommodation on room-only basis',
            'Inclusive of standard Wi-Fi',
            'Inclusive of VAT',
          ],
          memberRate: 250,
          standardRate: 300,
        },
      ],
    },

    {
      image:
        'https://cdn.sanity.io/images/ocl5w36p/prod3/001112f182df62934565a53070caea6ccca82d81-1400x1120.jpg',
      type: 'Executive Room',
      size: '2500sft',
      guests: 'Upto 3 guests',
      beds: '2+ Beds',
      offers: [
        {
          title: 'Romantic Stays - Limited Time Offer',
          description: 'Indulge in moments of pure bliss with the one you hold dear',
          details: [
            'Rate is inclusive of Breakfast, Internet and VAT',
            'Access to the gym and J Wellness Circle',
            '10% discount on Food and Beverages',
          ],
          memberRate: 300,
          standardRate: 400,
        },
        {
          title: 'Best Available Rate- Room Only',
          description: 'Indulge in moments of pure bliss with the one you hold dear',
          details: [
            'Accommodation on room-only basis',
            'Inclusive of standard Wi-Fi',
            'Inclusive of VAT',
          ],
          memberRate: 250,
          standardRate: 300,
        },
      ],
    },
    // You can add more rooms here
  ];
  bookRoom(roomType: string) {
    this.router.navigate(['/roomselection'], { queryParams: { type: roomType } });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.hotelId = Number(params.get('id')); // Convert to number
      
    });
  }
}