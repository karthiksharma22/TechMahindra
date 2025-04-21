import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports:[CommonModule]
})
export class NavbarComponent {
  showMenu: boolean = false;
  showPopup: boolean = false;
  showSignup: boolean = false;

  toggleMenu(): void {
    this.showMenu = !this.showMenu;
  }

  closeMenu(): void {
    this.showMenu = false;
  }

  togglePopup(): void {
    this.showPopup = !this.showPopup;
  }

  switchForm(type: string): void {
    this.showSignup = type === 'signup';
  }
}