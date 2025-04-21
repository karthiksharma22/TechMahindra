import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// Define the Task interface
interface Task {
  taskId: number;
  assignedEmployeeId: number | null;
  description: string;
  dueDate: string;
  locationId: number;
  status: 'COMPLETED' | 'IN_PROGRESS' | 'PENDING';
  title: string;
  showUploadProof?: boolean;
  selectedFile?: File;
}

// Define the Employee interface
interface Employee {
  id: number;
  shiftTiming: string;
  workStatus: string;
}

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
})
export class TaskListComponent implements OnInit {
  taskForm: FormGroup;
  taskList: Task[] = [];
  selectedLocationId: number = 1;
  employee:any = '';
  selectedEmployeeId: number = 1;
  selectedTask: Task | null = null;
  searchTerm: string = '';
  sortOption: string = '';
  isShiftStarted: boolean = false; // Tracks if the shift has started
  isShiftTimeValid: boolean = false; // Tracks if the current time matches the shift time
  employeeShiftTiming: string = ''; // Stores the employee's shift timing

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.taskForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(200)]],
      description: ['', [Validators.required, Validators.maxLength(1000)]],
      dueDate: ['', Validators.required],
      status: ['PENDING', Validators.required],
      assignedEmployeeId: [null],
    });
  }

  ngOnInit(): void {
    // Fetch the latest employee data from localStorage
    const storedEmployee = localStorage.getItem("employeeData");
    if (storedEmployee) {
      this.employee = JSON.parse(storedEmployee);
      this.selectedEmployeeId = this.employee.employeeId;
    } else {
      console.error("No employee data found in localStorage.");
    }
  
    console.log("Selected Employee ID (ngOnInit):", this.selectedEmployeeId);
  
    this.fetchEmployeeDetails(); // Now it has the latest employeeId
    this.fetchTasks(); // Fetch tasks after updating employeeId
  }

  // Fetch employee details to get shift timing
  fetchEmployeeDetails(): void {
    this.employee = JSON.parse(localStorage.getItem("employeeData") || "{}");
    this.selectedEmployeeId = this.employee.employeeId;
    this.http
      .get<Employee>(
        `http://localhost:2026/api/employees/${this.selectedEmployeeId}/location/${this.selectedLocationId}`
      )
      .subscribe(
        (data) => {
          this.employeeShiftTiming = data.shiftTiming;
          this.checkShiftTiming(); // Check if the current time matches the shift timing
        },
        (error) => {
          console.error('Error fetching employee details:', error);
        }
      );
  }

  // Check if the current time matches the employee's shift timing
  checkShiftTiming(): void {
    const currentTime = new Date().getHours();
    const shiftTiming = this.employeeShiftTiming.toLowerCase();

    if (shiftTiming.includes('morning') && currentTime >= 6 && currentTime < 14) {
      this.isShiftTimeValid = true;
    } else if (shiftTiming.includes('afternoon') && currentTime >= 14 && currentTime < 22) {
      this.isShiftTimeValid = true;
    } else if (shiftTiming.includes('night') && (currentTime >= 22 || currentTime < 6)) {
      this.isShiftTimeValid = true;
    } else {
      this.isShiftTimeValid = false;
    }
  }

  // Start the shift
  startShift(): void {
    this.isShiftStarted = true; 
  }

  // Fetch tasks for the selected employee and location
  fetchTasks(): void {
    this.http
      .get<Task[]>(
        `http://localhost:2026/api/tasks/${this.selectedLocationId}/employee/${this.selectedEmployeeId}`
      )
      .subscribe(
        (data) => {
          this.taskList = data;
        },
        (error) => {
          console.error('Error fetching tasks:', error);
        }
      );
  }

  // Filter and sort tasks
  getFilteredTasks(): Task[] {
    let filteredList = this.taskList.filter(
      (task) =>
        task.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        task.description.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        task.status.toLowerCase().includes(this.searchTerm.toLowerCase())
    );

    if (this.sortOption === 'dueDateAsc') {
      filteredList.sort((a, b) => new Date(a.dueDate).getTime() - new Date(b.dueDate).getTime());
    } else if (this.sortOption === 'dueDateDesc') {
      filteredList.sort((a, b) => new Date(b.dueDate).getTime() - new Date(a.dueDate).getTime());
    } else if (this.sortOption === 'status') {
      filteredList.sort((a, b) => a.status.localeCompare(b.status));
    }

    return filteredList;
  }

  // Start a task
  startTask(task: Task): void {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http
      .put<Task>(
        `http://localhost:2026/api/tasks/accept/${this.selectedLocationId}/${this.selectedEmployeeId}/${task.taskId}`,
        {},
        { headers }
      )
      .subscribe(
        (updatedTask) => {
          console.log('Task started successfully:', updatedTask);
          this.fetchTasks();
        },
        (error) => {
          console.error('Error starting task:', error);
        }
      );
  }

  // Complete a task
  completeTask(task: Task): void {
    task.showUploadProof = true;
    if (task.selectedFile) {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      this.http
        .put<Task>(
          `http://localhost:2026/api/tasks/${this.selectedLocationId}/${task.taskId}/complete`,
          { status: 'COMPLETED' },
          { headers }
        )
        .subscribe(
          () => {
            this.fetchTasks();
          },
          (error) => {
            console.error('Error completing task:', error);
          }
        );
    }
  }

  // Handle file selection
  onFileSelected(event: Event, task: Task): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      task.selectedFile = input.files[0];
    }
  }

  // Upload proof
  uploadProof(task: Task): void {
    if (task.selectedFile) {
      console.log('File selected for upload:', task.selectedFile.name);
      alert(`Proof uploaded successfully: ${task.selectedFile.name}`);
      task.showUploadProof = false;
    } else {
      alert('Please select a file to upload.');
    }
  }
}