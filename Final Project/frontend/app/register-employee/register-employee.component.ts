import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register-employee',
  templateUrl: './register-employee.component.html',
  styleUrls: ['./register-employee.component.css'], // Optional: Add styling here
  imports: [CommonModule, FormsModule]
})
export class RegisterEmployeeComponent {
  employee = this.getEmptyEmployee(); // Initialize empty form data

  locationId: number = 1; // Default location ID

  constructor(private http: HttpClient) {}

  registerEmployee(event: Event) {
    event.preventDefault(); // Prevent page refresh

    const apiUrl = `http://localhost:2026/api/employees/register/${this.locationId}`;
    console.log(this.employee);

    this.http.post(apiUrl, this.employee).subscribe(
      (response) => {
        console.log('Employee registered:', response);
        alert('Employee registered successfully!');
        
        // ✅ Reset the form
        this.employee = this.getEmptyEmployee();
      },
      (error) => {
        console.error('Error registering employee:', error);
        alert('Error registering employee!');
      }
    );
  }

  // Helper function to return an empty employee object
  getEmptyEmployee() {
    return {
      name: '',
      email: '',
      phone: '',
      role: '',
      department: '',
      salary: 0,
      shiftTiming: '',
      workStatus: '',
      dateOfJoining: '',
      password: ''
    };
  }
}
