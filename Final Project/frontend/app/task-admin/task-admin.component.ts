import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common'; 
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 

interface Employee {
  employeeId: number;
  name: string;
  shiftTiming: string;
  department: string;
  role: string;
}

interface Task {
  taskId: number;
  title: string;
  description: string;
  dueDate: string;
  status: string;
  assignedEmployeeId: number;
  category: string;
}

@Component({
  selector: 'app-task-admin',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './task-admin.component.html',
  styleUrl: './task-admin.component.css'
})
export class TaskAdminComponent implements OnInit {
  taskForm: FormGroup;
  employees: Employee[] = [];
  filteredEmployees: Employee[] = [];
  tasks: Task[] = [];
  filteredTasks: Task[] = [];
  selectedStatus: string = '';
  readonly locationId = 1; // Fixed location ID
  isEditing = false;
  editingTaskId: number | null = null;

  // Updated categories to match existing roles
  categories: string[] = [
    'Manager',
    'Receptionist',
    'Cleaner',
    'CEO',
    'Front Desk Helper',
    'Housekeeper',
    'Chef',
    'Maintenance Staff'
  ];

  selectedCategory: string = '';

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      dueDate: ['', Validators.required],
      category: ['', Validators.required],
      assignedEmployeeId: ['', Validators.required],
      status: ['PENDING', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchEmployees();
    this.fetchTasks();
  }

  fetchEmployees(): void {
    this.http.get<Employee[]>(`http://localhost:2026/api/employees/workstatus/active/location/${this.locationId}`).subscribe(
      (data) => { 
        this.employees = data;
        this.filteredEmployees = [...this.employees];
      },
      (error) => { console.error('Error fetching employees:', error); }
    );
  }

  fetchTasks(): void {
    this.http.get<Task[]>(`http://localhost:2026/api/tasks/all/${this.locationId}`).subscribe(
      (data) => {
        this.tasks = data;
        this.filteredTasks = [...this.tasks];
      },
      (error) => { console.error('Error fetching tasks:', error); }
    );
  }

  getEmployeeName(employeeId: number): string {
    const employee = this.employees.find(emp => emp.employeeId === employeeId);
    return employee ? employee.name : 'Unknown';
  }

  createTask(): void {
    if (this.taskForm.valid) {
      const taskData = { ...this.taskForm.value, status: 'PENDING' };
      this.http.post<Task>(`http://localhost:2026/api/tasks/create/${this.locationId}`, taskData).subscribe(
        (response) => {
          console.log('Task created successfully:', response);
          this.fetchTasks();
          this.taskForm.reset();
        },
        (error) => { console.error('Error creating task:', error); }
      );
    }
  }

  editTask(task: Task): void {
    this.isEditing = true;
    this.editingTaskId = task.taskId;
    this.taskForm.patchValue({
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
      category: task.category,
      assignedEmployeeId: task.assignedEmployeeId
    });
  }

  updateTask(): void {
    if (this.taskForm.valid && this.editingTaskId !== null) {
      const updatedTask = { ...this.taskForm.value };
      console.log(updatedTask)
      this.http.put<Task>(`http://localhost:2026/api/tasks/${this.locationId}/${this.editingTaskId}`, updatedTask)
        .subscribe(
          (response) => {
            console.log('Task updated successfully:', response);
            this.fetchTasks();
            this.cancelEdit();
          },
          (error) => { console.error('Error updating task:', error); }
        );
    }
  }

  deleteTask(taskId: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.http.delete(`http://localhost:2026/api/tasks/${this.locationId}/${taskId}`)
        .subscribe(
          () => {
            console.log('Task deleted successfully');
            this.fetchTasks();
          },
          (error) => { console.error('Error deleting task:', error); }
        );
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.editingTaskId = null;
    this.taskForm.reset();
  }

  filterTasks(): void {
    this.filteredTasks = this.selectedStatus
      ? this.tasks.filter(task => task.status === this.selectedStatus)
      : [...this.tasks];
  }

  isTaskOverdue(task: Task): boolean {
    const dueDate = new Date(task.dueDate);
    const today = new Date();
    return task.status === 'PENDING' && dueDate < today;
  }

  onCategoryChange(): void {
    const selectedCategory = this.taskForm.get('category')?.value;
    if (selectedCategory) {
      // Filter employees whose role matches the selected category (case-insensitive)
      this.filteredEmployees = this.employees.filter(emp =>
        emp.role.toLowerCase() === selectedCategory.toLowerCase()
      );
    } else {
      this.filteredEmployees = [...this.employees];
    }
  }
}