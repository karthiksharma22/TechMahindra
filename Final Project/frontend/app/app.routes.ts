import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';

// Import your standalone components
import { TaskAdminComponent } from './task-admin/task-admin.component';
import { InventoryAdminComponent } from './inventory-admin/inventory-admin.component';
import { TaskListComponent } from './task-list/task-list.component';
import { HomepageComponent } from './homepage/homepage.component';
import { BookroomsComponent } from './bookrooms/bookrooms.component';
import { BookingFormComponent } from './bookingform/bookingform.component';
import { AdminEmployeeComponent } from './admin-employee/admin-employee.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RegisterEmployeeComponent } from './register-employee/register-employee.component';
import { RoomSelectionComponent } from './room-selection/room-selection.component';
import { EmployeeHomComponent } from './employee-hom/employee-hom.component';
import { ComplaintsComponent } from './complaints/complaints.component';
import { BookingComponent } from './bookings/bookings.component';
import { HotelsComponent } from './hotels/hotels.component';
import { EmployeeCheckinComponent } from './employee-checkin/employee-checkin.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { IssuesComponent } from './issues/issues.component';
import { IssueAdminComponent } from './issue-admin/issue-admin.component';



export const routes: Routes = [
  { path: '', redirectTo: "/homepage", pathMatch: 'full' }, // Default route
  { path: 'task-admin', component: TaskAdminComponent },
  { path: 'inventory-admin', component: InventoryAdminComponent },
  { path: 'task-list', component: TaskListComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'bookrooms', component: BookroomsComponent },

  { path: 'admin-employee', component: AdminEmployeeComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'register-employee', component: RegisterEmployeeComponent },
  { path: 'room', component: RoomSelectionComponent },
  { path: 'emp-home', component: EmployeeHomComponent },
  { path: 'complaint', component: ComplaintsComponent },
  { path: 'bookings/:id', component: BookroomsComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'issues', component: IssuesComponent },
  { path: 'issues-admin', component: IssueAdminComponent },

  { path: 'bookingform/:id', component: BookingFormComponent },
  { path: 'room-selection/:id', component: RoomSelectionComponent },
  { path: 'hotels', component: HotelsComponent },
  { path: 'checkin', component: EmployeeCheckinComponent },
  
  


  { path: '**', redirectTo: 'homepage' } // Wildcard route for unknown paths
];
