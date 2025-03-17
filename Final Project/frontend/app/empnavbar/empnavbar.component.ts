import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-empnavbar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet, RouterModule],
  templateUrl: './empnavbar.component.html',
  styleUrls: ['./empnavbar.component.css']
})
export class EmpnavbarComponent implements OnInit {
  isMenuOpen = false;
  isDropdownOpen = false;
  isLoginPopupOpen = false;
  isManagerOrAdmin = false;
  isEmployeeLogin = true; // Default to employee login
  isRegistrationOpen = false; // Track registration form visibility
  isHomepage = false;
  employee: any = null;
  user: any = null;
  loginType: 'employee' | 'user' | null = null; // Track login type

  loginData = { email: '', password: '', locationId: '' };
  registrationData = {
    username: '',
    email: '',
    password: '',
    aadharNumber: '',
    age: 0,
    gender: 'male'
  };

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.loadLoginData();
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  openLoginPopup(): void {
    this.isLoginPopupOpen = true;
    this.isEmployeeLogin = true; // Reset to employee login when popup opens
    this.isRegistrationOpen = false; // Reset registration form
  }

  closeLoginPopup(): void {
    this.isLoginPopupOpen = false;
  }

  toggleLoginType(): void {
    this.isEmployeeLogin = !this.isEmployeeLogin;
    this.isRegistrationOpen = false; // Close registration form when toggling login type
  }

  toggleRegistration(): void {
    this.isRegistrationOpen = !this.isRegistrationOpen;
  }

  checkRoute(): void {
    this.isHomepage = this.router.url === '/homepage'; 
  }

  registerUser(): void {
    const apiUrl = `http://localhost:2026/api/users/create`;

    this.http
      .post(apiUrl, this.registrationData, { responseType: 'json' })
      .subscribe(response => {
        alert('User registration successful');
        this.closeLoginPopup();
      }, error => {
        alert('Registration failed: ' + error.message);
      });
  }

  loginEmployee(): void {
    const apiUrl = `http://localhost:2026/api/employees/login/location/${this.loginData.locationId}`;

    this.http
      .post(apiUrl, null, {
        params: { email: this.loginData.email, password: this.loginData.password },
        responseType: 'json'
      })
      .subscribe(response => {
        alert('Employee login successful');
        localStorage.setItem('employeeData', JSON.stringify(response));
        localStorage.setItem('loginType', 'employee');

        this.employee = response;
        this.user = null;
        this.loginType = 'employee';

        this.isManagerOrAdmin = this.employee?.role?.toLowerCase() === 'manager' || 
                                this.employee?.role?.toLowerCase() === 'admin';

        this.closeLoginPopup();
      }, () => {
        alert('Invalid employee credentials');
      });
  }

  loginUser(): void {
    const apiUrl = `http://localhost:2026/api/users/login`;

    this.http
      .post(apiUrl, this.loginData, { responseType: 'json' })
      .subscribe(response => {
        alert('User login successful');
        localStorage.setItem('userData', JSON.stringify(response));
        localStorage.setItem('loginType', 'user');

        this.user = response;
        console.log(this.user)
        this.employee = null;
        this.loginType = 'user';

        this.closeLoginPopup();
      }, () => {
        alert('Invalid user credentials');
      });
  }

  logout(): void {
    localStorage.removeItem('employeeData');
    localStorage.removeItem('userData');
    localStorage.removeItem('loginType');
    
    this.employee = null;
    this.user = null;
    this.loginType = null;

    this.router.navigate(['/']); // Redirect to home
  }

  loadLoginData(): void {
    this.loginType = localStorage.getItem('loginType') as 'employee' | 'user' | null;

    if (this.loginType === 'employee') {
      this.employee = JSON.parse(localStorage.getItem('employeeData') || '{}');
      this.isManagerOrAdmin = this.employee?.role?.toLowerCase() === 'manager' || 
                              this.employee?.role?.toLowerCase() === 'admin';
    } else if (this.loginType === 'user') {
      this.user = JSON.parse(localStorage.getItem('userData') || '{}');
    }
  }
}
