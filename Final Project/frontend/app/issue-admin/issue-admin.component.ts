
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Issues {
  id: number;
  category: string;
  description: string;
  status: string;
  createdAt: Date;
  userId: number;
  locationId: number;
  registeredBy: string;
}

@Component({
  selector: 'app-issue-admin',
  imports: [CommonModule,],
  templateUrl: './issue-admin.component.html',
  styleUrl: './issue-admin.component.css'
})
export class IssueAdminComponent {

  complaintForm: FormGroup;
      complaints: Issues[] = [];  // Now fetched dynamically
  
      editMode = false;
      editIndex = -1;
      searchTerm = '';
      sortBy = 'id';
  
      private readonly LOCATION_ID = JSON.parse(localStorage.getItem('locationId')||'1');
    
      constructor(private fb: FormBuilder, private http: HttpClient) {
        this.complaintForm = this.fb.group({
          category: ['', Validators.required],
          description: ['', Validators.required],
          status: ['', Validators.required],
          createdAt: ['', Validators.required],
          registeredBy: ['', Validators.required]
        });
      }

      
  ngOnInit(): void {
    // Set default date to current date and time
    const now = new Date();
    const localDatetime = new Date(now.getTime() - now.getTimezoneOffset() * 60000)
      .toISOString()
      .slice(0, 16);
    
    this.complaintForm.patchValue({
      createdAt: localDatetime,
      status: 'Pending'
    });

    // Fetch complaints from backend
    this.fetchComplaints();
  }

  /** Fetch complaints from backend */
  fetchComplaints() {
    this.http.get<Issues[]>('http://localhost:2026/api/complaints/all').subscribe({
      next: (data) => {
        this.complaints = data;
        console.log(data)
      },
      error: (err) => console.error('Error fetching complaints:', err)
    });
  }

  editComplaint(index: number) {
    const selectedComplaint = this.complaints[index];
    const localDatetime = new Date(selectedComplaint.createdAt.getTime() - selectedComplaint.createdAt.getTimezoneOffset() * 60000)
      .toISOString()
      .slice(0, 16);
    
    this.complaintForm.setValue({
      category: selectedComplaint.category,
      description: selectedComplaint.description,
      status: selectedComplaint.status,
      createdAt: localDatetime,
      registeredBy: selectedComplaint.registeredBy
    });

    this.editMode = true;
    this.editIndex = index;
  }

  AssignComplaint(index: number) {
    const selectedComplaint = this.complaints[index];

    const taskBody = {
      title: `Task for Complaint #${selectedComplaint.id}`, 
      description: selectedComplaint.description,
      status: 'PENDING', 
      dueDate: new Date().toISOString().split('T')[0], 
      assignedEmployeeId: null, 
      locationId: this.LOCATION_ID
    };
  
    const apiUrl = `http://localhost:2026/api/tasks/assign-auto-task/${this.LOCATION_ID}/${selectedComplaint.category}`;
  
    this.http.post(apiUrl, taskBody).subscribe({
      next: (response) => {
        console.log('Task auto-assigned:', response);
        selectedComplaint.status = 'Assigned'; // Update the status in UI
        this.complaints[index] = selectedComplaint;
      },
      error: (err) => console.error('Error auto-assigning task:', err)
    });
  }

  resetForm() {
    this.complaintForm.reset();
    const now = new Date();
    const localDatetime = new Date(now.getTime() - now.getTimezoneOffset() * 60000)
      .toISOString()
      .slice(0, 16);
    
    this.complaintForm.patchValue({
      createdAt: localDatetime,
      status: 'Pending'
    });

    this.editMode = false;
    this.editIndex = -1;
  }

  getNextId(): number {
    return this.complaints.length > 0 
      ? Math.max(...this.complaints.map(complaint => complaint.id)) + 1 
      : 1;
  }

  searchComplaints(term: string) {
    this.searchTerm = term.toLowerCase();
  }

  sortComplaints(property: string) {
    this.sortBy = property;
  }


}
