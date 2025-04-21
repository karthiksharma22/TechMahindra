import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Hotel } from '../model/hotel';
import { HotelItemComponent } from '../hotel-item/hotel-item.component';


@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule, HotelItemComponent], 
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css']
})
export class HotelsComponent {
  searchQuery: string = '';
  hotels: Hotel[] = [];
  filteredHotels: Hotel[] = [];

  constructor() {
    this.hotels = [
      new Hotel(1, 'Crystal Haven', 'Eldoria', "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlV_bJf_wG9EAP_78aqVDERnuReNV6VdjCufHb93Ll37qqUuYmzFlZxVtdaVGJhtlDiUk&usqp=CAU", "Resort", ['Infinity Pool', 'Private Beach'], ['Moonlight Bay', 'Starfall Cliffs']),
      new Hotel(2, 'Skyline Oasis', 'Zyphoria', "https://www.fodors.com/wp-content/uploads/2018/09/bellagio-hertel.jpg", "Luxury", ['Rooftop Lounge', 'Helipad'], ['Cloudspire Tower', 'Sunset Boulevard']),
      new Hotel(3, 'Ember Lodge', 'Vulcanis', "https://geekytourist.com/wp-content/uploads/2018/03/fictional-hotels-hotel-transylvania1.png", "Adventure", ['Hot Springs', 'Volcano Hikes'], ['Lava Falls', 'Flame Canyon']),
      new Hotel(4, 'Aurora Cabin', 'Nordara', "https://d3f9k0n15ckvhe.cloudfront.net/wp-content/uploads/2017/05/Web-Hotel_Real-Life-Inspiration-for-Fictional-Places.jpg", "Cabin", ['Northern Lights View', 'Ice Bar'], ['Frozen Lake', 'Glacier Peaks']),
      new Hotel(5, 'Bates Motel', 'Solara Dunes', "https://ew.com/thmb/kBjISZv9lNDGBjUJFa0kVgf-FFs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/batesmotel-2000-3d8082c099aa4297a95fd2dfb7d9a259.jpg", "Desert Retreat", ['Camel Rides', 'Oasis Spa'], ['Whispering Dunes', 'Celestial Ruins']),
      new Hotel(6, 'Verdant Escape', 'Evergrove', "https://media.duneceramics.com/public/hotel-el-resplandor-1718700419umPwM.jpg", "Eco Resort", ['Treehouse Stays', 'Organic Dining'], ['Emerald Falls', 'Hidden Hollow']),
  ];
  this.filteredHotels = this.hotels;
  
  }

  filterHotels(category: string) {
    this.filteredHotels = category === 'All' ? this.hotels : this.hotels.filter(hotel => hotel.category === category);
  }
}