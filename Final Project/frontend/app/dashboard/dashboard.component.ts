import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Chart, registerables, ChartType } from 'chart.js';

// Register Chart.js components globally
Chart.register(...registerables);



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  employees: any[] = [];
  tasks: any[] = [];
  rooms: any[] = [];
  inventory: any[] = [];

  // Chart instances
  employeeDeptChart: any;
  employeeRoleChart: any;
  employeeShiftChart: any;
  employeeSalaryChart: any;
  taskStatusChart: any;
  taskDeptChart: any;
  taskCompletionTrendChart: any;
  roomOccupancyChart: any;
  roomCleaningChart: any;
  roomBookingTrendChart: any;
  inventoryStockChart: any;
  inventoryLowStockChart: any;
  inventoryUsageTrendChart: any;

  // ViewChild references for canvas elements
  @ViewChild('employeeDeptChart') employeeDeptChartRef!: ElementRef;
  @ViewChild('employeeRoleChart') employeeRoleChartRef!: ElementRef;
  @ViewChild('employeeShiftChart') employeeShiftChartRef!: ElementRef;
  @ViewChild('employeeSalaryChart') employeeSalaryChartRef!: ElementRef;

  @ViewChild('taskStatusChart') taskStatusChartRef!: ElementRef;
  @ViewChild('taskDeptChart') taskDeptChartRef!: ElementRef;
  @ViewChild('taskCompletionTrendChart') taskCompletionTrendChartRef!: ElementRef;

  @ViewChild('roomOccupancyChart') roomOccupancyChartRef!: ElementRef;
  @ViewChild('roomCleaningChart') roomCleaningChartRef!: ElementRef;
  @ViewChild('roomBookingTrendChart') roomBookingTrendChartRef!: ElementRef;

  @ViewChild('inventoryStockChart') inventoryStockChartRef!: ElementRef;
  @ViewChild('inventoryLowStockChart') inventoryLowStockChartRef!: ElementRef;
  @ViewChild('inventoryUsageTrendChart') inventoryUsageTrendChartRef!: ElementRef;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const locationId = 1;

    // Fetch employees
    this.http.get(`http://localhost:2026/api/employees/all/${locationId}`)
      .subscribe({ next: (data: any) => { this.employees = data; }, error: (err) => console.error(err) });

    // Fetch tasks
    this.http.get(`http://localhost:2026/api/tasks/all/${locationId}`)
      .subscribe({ next: (data: any) => { this.tasks = data; }, error: (err) => console.error(err) });

    // Fetch rooms
    this.http.get(`http://localhost:2026/api/rooms/location/${locationId}`)
      .subscribe({ next: (data: any) => { this.rooms = data; }, error: (err) => console.error(err) });

    // Fetch inventory
    this.http.get(`http://localhost:2026/api/inventory/all/${locationId}`)
      .subscribe({ next: (data: any) => { this.inventory = data; }, error: (err) => console.error(err) });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (this.employeeDeptChartRef) this.createEmployeeDeptChart();
      if (this.employeeRoleChartRef) this.createEmployeeRoleChart();
      if (this.employeeShiftChartRef) this.createEmployeeShiftChart();
      if (this.employeeSalaryChartRef) this.createEmployeeSalaryChart();
      if (this.taskStatusChartRef) this.createTaskStatusChart();
      if (this.taskDeptChartRef) this.createTaskDeptChart();
      if (this.taskCompletionTrendChartRef) this.createTaskCompletionTrendChart();
      if (this.roomOccupancyChartRef) this.createRoomOccupancyChart();
      if (this.roomCleaningChartRef) this.createRoomCleaningChart();
      if (this.roomBookingTrendChartRef) this.createRoomBookingTrendChart();
      if (this.inventoryStockChartRef) this.createInventoryStockChart();
      if (this.inventoryLowStockChartRef) this.createInventoryLowStockChart();
      if (this.inventoryUsageTrendChartRef) this.createInventoryUsageTrendChart();
    }, 500);
  }
  

  // Employee Charts
  createEmployeeDeptChart(): void {
    const uniqueDepartments = [...new Set(this.employees.map(emp => emp.department))];
    this.createChart(this.employeeDeptChartRef, 'bar', uniqueDepartments, this.countBy(this.employees, 'department'), 'Employees per Department');
  }


  //employee availability chart
  createEmployeeRoleChart(): void {
    const uniqueRoles = [...new Set(this.employees.map(emp => emp.workStatus))];  // Extract unique roles
    const roleCounts = uniqueRoles.map(role => this.employees.filter(emp => emp.workStatus === role).length);  // Count each role
  
    this.createChart(this.employeeRoleChartRef, 'pie', uniqueRoles, roleCounts, 'Employee Availability Status');
  }
  
//employee shift doughnut chart
  createEmployeeShiftChart(): void {
    const uniqueShifts = [...new Set(this.employees.map(emp => emp.shiftTiming))];
    this.createChart(this.employeeShiftChartRef, 'doughnut', uniqueShifts, this.countBy(this.employees, 'shiftTiming'), 'Shift Distribution');
  }

  createEmployeeSalaryChart(): void {
    if (!this.employees || this.employees.length === 0) {
      console.error("No employee data available for Salary Chart.");
      return;
    }
  
    // Calculate total salaries per department
    const salaryByDepartment = this.employees.reduce((acc, emp) => {
      acc[emp.department] = (acc[emp.department] || 0) + emp.salary;
      return acc;
    }, {} as Record<string, number>);
  
    const departments = Object.keys(salaryByDepartment); // Department names
    const totalSalaries = departments.map(dept => salaryByDepartment[dept]); // Corresponding total salaries

  
    if (!this.employeeSalaryChartRef || !this.employeeSalaryChartRef.nativeElement) {
      console.error("Canvas reference is not available for Salary Chart.");
      return;
    }
    this.createChart(this.employeeSalaryChartRef, 'line', departments, totalSalaries, 'Total Salary Per Department');
  }
  

  // Task Charts
  createTaskStatusChart(): void {
    this.createChart(this.taskStatusChartRef, 'doughnut', ['Completed', 'In Progress', 'Pending'], [
      this.tasks.filter(t => t.status === 'COMPLETED').length,
      this.tasks.filter(t => t.status === 'IN_PROGRESS').length,
      this.tasks.filter(t => t.status === 'PENDING').length
    ], 'Task Status');
  }

  createTaskDeptChart(): void {
    if (!this.tasks || this.tasks.length === 0) {
      console.error("No task data available for Task Department Chart.");
      return;
    }
  
    // Count tasks assigned to each employee (by assignedEmployeeId)
    const taskCountsByEmployee = this.tasks.reduce((acc, task) => {
      const employeeId = task.assignedEmployeeId;
      acc[employeeId] = (acc[employeeId] || 0) + 1;
      return acc;
    }, {} as Record<number, number>);
  
    const employeeIds = Object.keys(taskCountsByEmployee).map(id => Number(id)); // Convert keys to numbers
    const taskCounts = employeeIds.map(id => taskCountsByEmployee[id]);
  
    console.log("Employee IDs:", employeeIds);
    console.log("Task Counts per Employee:", taskCounts);
  
    if (!this.taskDeptChartRef || !this.taskDeptChartRef.nativeElement) {
      console.error("Canvas reference is not available for Task Department Chart.");
      return;
    }
  
    // Create a Bar Chart where:
    // X-axis = Employee IDs
    // Y-axis = Number of tasks assigned to them
    this.createChart(this.taskDeptChartRef, 'bar', employeeIds.map(id => `Emp ${id}`), taskCounts, 'Tasks Per Employee');
  }
  

  createTaskCompletionTrendChart(): void {
    if (!this.tasks || this.tasks.length === 0) {
      console.error("No task data available for Task Completion Trend Chart.");
      return;
    }
  
    // Count tasks by due date
    const taskCountsByDate = this.tasks.reduce((acc, task) => {
      const dateKey = new Date(task.dueDate).toISOString().split("T")[0]; // Format YYYY-MM-DD
      acc[dateKey] = (acc[dateKey] || 0) + 1;
      return acc;
    }, {} as Record<string, number>);
  
    const dueDates = Object.keys(taskCountsByDate).sort(); // Sort dates
    const taskCounts = dueDates.map(date => taskCountsByDate[date]);
  
    console.log("Task Due Dates:", dueDates);
    console.log("Task Counts per Date:", taskCounts);
  
    if (!this.taskCompletionTrendChartRef || !this.taskCompletionTrendChartRef.nativeElement) {
      console.error("Canvas reference is not available for Task Completion Trend Chart.");
      return;
    }
  
    // Create the Line Chart to show task completion trend
    this.createChart(this.taskCompletionTrendChartRef, 'line', dueDates, taskCounts, 'Number of Tasks Due');
  }
  

  // Room Charts
  createRoomOccupancyChart(): void {
    this.createChart(this.roomOccupancyChartRef, 'pie', ['Available', 'Occupied'], [
      this.rooms.filter(r => r.occStatus === 'AVAILABLE').length,
      this.rooms.filter(r => r.occStatus === 'NOT_AVAILABLE').length
    ], 'Room Occupancy');
  }

  createRoomCleaningChart(): void {
    this.createChart(this.roomCleaningChartRef, 'bar', ['Clean', 'Needs Cleaning'], [
      this.rooms.filter(r => r.cleanStatus === 'GREEN').length,
      this.rooms.filter(r => r.cleanStatus === 'RED').length
    ], 'Room Cleaning Status');
  }

  createRoomBookingTrendChart(): void {
    this.createChart(this.roomBookingTrendChartRef, 'line', this.rooms.map(r => r.id), this.rooms.map(r => r.timesBooked), 'Room Booking Trend');
  }

  // Inventory Charts
  createInventoryStockChart(): void {
    const stockByCategory = this.inventory.reduce((acc, item) => {
      acc[item.category] = (acc[item.category] || 0) + item.stockQuantity;
      return acc;
    }, {} as Record<string, number>);
  
    this.createChart(this.inventoryStockChartRef, 'bar', Object.keys(stockByCategory), Object.values(stockByCategory), 'Stock Levels');
  }
  

  createInventoryLowStockChart(): void {
    this.createChart(this.inventoryLowStockChartRef, 'doughnut', ['Low Stock', 'In Stock'], [
      this.inventory.filter(i => i.stockQuantity < 10).length,
      this.inventory.filter(i => i.stockQuantity >= 10).length
    ], 'Low Stock Items');
  }

  createInventoryUsageTrendChart(): void {
    const stockValueByCategory = this.inventory.reduce((acc, item) => {
      acc[item.category] = (acc[item.category] || 0) + item.price * item.stockQuantity;
      return acc;
    }, {} as Record<string, number>);
  
    this.createChart(this.inventoryUsageTrendChartRef, 'line', Object.keys(stockValueByCategory), Object.values(stockValueByCategory), 'Stock Value Over Time');
  }
  

  // Helper Functions
  countBy(array: any[], key: string): number[] {
    const counts = array.reduce((acc, obj) => { acc[obj[key]] = (acc[obj[key]] || 0) + 1; return acc; }, {});
    return Object.values(counts);
  }

  createChart(ref: ElementRef, type: ChartType, labels: any[], data: number[], label: string): void {
    if (!ref?.nativeElement) return;

    const canvas = ref.nativeElement;
  const ctx = canvas.getContext('2d');

  // Handle high DPI displays
  const dpr = window.devicePixelRatio || 1;
  const rect = canvas.getBoundingClientRect();
  canvas.width = rect.width * dpr;
  canvas.height = rect.height * dpr;
  ctx.scale(dpr, dpr);
  
    // Dark theme color palette
    const colors = {
      gold: '#d4af37',
      teal: '#2c7873',
      burgundy: '#8e354a',
      blue: '#34495e',
      lightGold: '#f5e5a3',
      darkGold: '#b8982e',
    };
  
    // Assign colors based on chart type
    let backgroundColor, borderColor;
    switch (type) {
      case 'bar':
        backgroundColor = [colors.gold, colors.teal, colors.burgundy, colors.blue];
        borderColor = [colors.darkGold, colors.teal, colors.burgundy, colors.blue];
        break;
      case 'pie':
      case 'doughnut':
        backgroundColor = [colors.gold, colors.teal, colors.burgundy, colors.blue, colors.lightGold];
        borderColor = [colors.darkGold, colors.teal, colors.burgundy, colors.blue, colors.lightGold];
        break;
      case 'line':
        backgroundColor = colors.gold;
        borderColor = colors.darkGold;
        break;
      default:
        backgroundColor = colors.gold;
        borderColor = colors.darkGold;
    }
  
    new Chart(ref.nativeElement.getContext('2d'), {
      type,
      data: {
        labels,
        datasets: [
          {
            label,
            data,
            backgroundColor,
            borderColor,
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            grid: {
              color: 'rgba(255, 255, 255, 0.1)', // Light grid lines
            },
            ticks: {
              color: '#ffffff', // Light text
            },
          },
          x: {
            grid: {
              color: 'rgba(255, 255, 255, 0.1)', // Light grid lines
            },
            ticks: {
              color: '#ffffff', // Light text
            },
          },
        },
        plugins: {
          legend: {
            labels: {
              color: '#ffffff', // Light text
            },
          },
        },
      },
    });
  }

  downloadReport(): void {
    const originalStyles = document.querySelectorAll('style, link[rel="stylesheet"]'); // Store original styles
    const printStyles = document.createElement('style'); // Create a new style element for print-specific styles
    document.title = "Dashboard Report"
    // Add print-specific styles to hide everything except the dashboard-container
    printStyles.innerHTML = `
      @media print {
      body * {
        visibility: hidden;
      }
      .dashboard-container, .dashboard-container * {
        visibility: visible;
      }
      .dashboard-container {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        background-color: #1a1a1a !important; /* Force dark background */
        color: #ffffff !important; /* Force light text */
      }
      .chart-card {
        background-color: #2d2d2d !important; /* Force dark card background */
        border-left: 5px solid #d4af37 !important; /* Force gold accent border */
        page-break-inside: avoid; /* Prevent charts from being split across pages */
      }
      .download-button {
        display: none; /* Hide the download button in the PDF */
      }
      body {
        -webkit-print-color-adjust: exact; /* Chrome, Safari */
        print-color-adjust: exact; /* Standard */
      }
    }
    `;

    // Append the print styles to the document head
    document.head.appendChild(printStyles);

    // Trigger the print dialog
    window.print();

    // Remove the print styles and restore the original styles
    document.head.removeChild(printStyles);
  }
}
