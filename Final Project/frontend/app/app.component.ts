import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { CommonModule } from '@angular/common';
import { BookroomsComponent } from "./bookrooms/bookrooms.component";
import { BookingFormComponent } from "./bookingform/bookingform.component";
import { InventoryAdminComponent } from './inventory-admin/inventory-admin.component';
import { TaskAdminComponent } from "./task-admin/task-admin.component";
import { TaskListComponent } from "./task-list/task-list.component";
import { FooterComponent } from './footer/footer.component';
import { AdminEmployeeComponent } from "./admin-employee/admin-employee.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { EmpnavbarComponent } from "./empnavbar/empnavbar.component";


@Component({
  selector: 'app-root',
  standalone:true,
  imports: [RouterOutlet, NavbarComponent, HomepageComponent, BookroomsComponent, BookingFormComponent, InventoryAdminComponent, TaskAdminComponent, TaskListComponent, FooterComponent, AdminEmployeeComponent, DashboardComponent, EmpnavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'HotelBookingFrontend';
}
