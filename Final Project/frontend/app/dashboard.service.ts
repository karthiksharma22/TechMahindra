import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private baseUrls: Record<string, string> = {
    employees: '/api/employees/all/',
    inventory: '/api/inventory/all/',
    location: '/api/locations/all',
    room: '/api/rooms/all',
    tasks: '/api/tasks/all/',
    users: '/api/users/all'
  };

  constructor(private http: HttpClient) {console.log('DashboardService Initialized');}

  fetchData(tableName: string, locationId?: number): Observable<any> {
    if (!this.baseUrls[tableName]) {
      throw new Error(`Invalid table name: ${tableName}`);
    }

    let url = this.baseUrls[tableName];
    if (locationId && tableName !== 'location' && tableName !== 'users') {
      url += locationId;
    }
    return this.http.get(url);
  }
}
