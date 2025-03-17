import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ViewportScroller } from '@angular/common';

// Define the Employee interface
interface Employee {
  employeeId: number;
  name: string;
  email: string;
  password: string;
  role: string;
  workStatus: string;
  shiftTiming: string;
  locationId: number;
}

@Component({
  selector: 'app-admin-employee',
  templateUrl: './admin-employee.component.html',
  styleUrls: ['./admin-employee.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
})
export class AdminEmployeeComponent implements OnInit {
  employeeForm: FormGroup;
  employeeList: Employee[] = [];
  filteredEmployees: Employee[] = []; // Store filtered employees
  selectedLocationId: number = 1;
  selectedEmployee: Employee | null = null;
  workStatusOptions: string[] = ['ACTIVE', 'INACTIVE', 'ON LEAVE', 'VACATION'];
  searchTerm: string = ''; // Search term for filtering employees

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
      workStatus: ['', Validators.required],
      shiftTiming: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchEmployees();
  }

  // Fetch employees for the selected location
  fetchEmployees(): void {
    this.http
      .get<Employee[]>(`http://localhost:2026/api/employees/all/${this.selectedLocationId}`)
      .subscribe(
        (data) => {
          this.employeeList = data;
          this.filteredEmployees = data; // Initialize filtered employees
        },
        (error) => {
          console.error('Error fetching employees:', error);
        }
      );
  }

  // Filter employees based on the search term
  filterEmployees(): void {
    if (!this.searchTerm) {
      this.filteredEmployees = this.employeeList; // Show all employees if no search term
    } else {
      this.filteredEmployees = this.employeeList.filter(
        (employee) =>
          employee.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          employee.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          employee.role.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  }

  // Select an employee for editing
  selectEmployee(employee: Employee): void {
    this.selectedEmployee = employee;
    this.employeeForm.patchValue({
      name: employee.name,
      email: employee.email,
      role: employee.role,
      workStatus: employee.workStatus,
      shiftTiming: employee.shiftTiming,
    });
  }

  // Update employee data
  updateEmployee(): void {
    if (this.selectedEmployee) {
      const updatedEmployee: Employee = {
        ...this.selectedEmployee,
        ...this.employeeForm.value,
      };

      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      this.http
        .put<Employee>(
          `http://localhost:2026/api/employees/update/${this.selectedEmployee.employeeId}/location/${this.selectedLocationId}`,
          updatedEmployee,
          { headers }
        )
        .subscribe(
          (response) => {
            console.log('Employee updated successfully:', response);
            this.fetchEmployees(); // Refresh the employee list
            this.resetForm(); // Reset the form
          },
          (error) => {
            console.error('Error updating employee:', error);
          }
        );
    }
  }

  // Set employee work status
  setWorkStatus(employee: Employee, workStatus: string): void {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http
      .put<Employee>(
        `http://localhost:2026/api/employees/update/${employee.employeeId}/location/${this.selectedLocationId}`,
        { workStatus },
        { headers }
      )
      .subscribe(
        (response) => {
          console.log('Work status updated successfully:', response);
          this.fetchEmployees(); // Refresh the employee list
        },
        (error) => {
          console.error('Error updating work status:', error);
        }
      );
  }

  // Reset the form
  resetForm(): void {
    this.employeeForm.reset();
    this.selectedEmployee = null;
  }
}