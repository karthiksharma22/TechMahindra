import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


 interface Room {
  id: number;
    floor: number;
    view: 'sea' | 'city';
    type: 'deluxe' | 'suite' | 'standard';
    status: 'available' | 'booked' | 'selected';
    price: number;
}

@Component({
  selector: 'app-room-selection',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterOutlet],
  templateUrl: './room-grid.component.html',
  styleUrls: ['./room-grid.component.css'] 
})

export class GridComponent implements OnInit{
  
    rooms: Room[] = [];
    filters = { floor: null, view: null, type: null };
    selectedRooms: Room[] = [];
  
    constructor(private http: HttpClient) {}
  
    ngOnInit(): void {
      this.fetchRooms();
    }
  
    fetchRooms(): void {
      this.http.get<Room[]>('http://localhost:2026/api/rooms/all').subscribe(data => {
        this.rooms = data;
      });
    }
  
    get filteredRooms(): Room[] {
      return this.rooms.filter(room =>
        (this.filters.floor === null || room.floor === this.filters.floor) &&
        (this.filters.view === null || room.view === this.filters.view) &&
        (this.filters.type === null || room.type === this.filters.type)
      );
    }
  
    toggleSelection(room: Room): void {
      if (room.status === 'booked') return;
  
      if (room.status === 'selected') {
        room.status = 'available';
        this.selectedRooms = this.selectedRooms.filter(r => r.id !== room.id);
      } else {
        room.status = 'selected';
        this.selectedRooms.push(room);
      }
    }
  
    get totalPrice(): number {
      return this.selectedRooms.reduce((sum, room) => sum + room.price, 0);
    }
  }