import { Component , Input} from '@angular/core';
import { Hotel } from '../model/hotel';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotel-item',
  imports: [RouterModule],
  templateUrl: './hotel-item.component.html',
  styleUrl: './hotel-item.component.css'
})
export class HotelItemComponent {
  @Input() hotel!: Hotel;
  constructor(private router: Router) {}

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}