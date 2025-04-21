import { AfterViewInit, Component, ElementRef, ViewChild, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
  imports: [CommonModule],  // ✅ Import CommonModule to use *ngFor
  standalone: true,
})
export class HomepageComponent {
  
}
