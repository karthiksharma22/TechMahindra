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
  selector: 'app-issues',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './issues.component.html',
  styleUrl: './issues.component.css'
})
export class IssuesComponent implements OnInit {
  
    complaintForm: FormGroup;
    complaints: Issues[] = [];  // Now fetched dynamically

    editMode = false;
    editIndex = -1;
    searchTerm = '';
    sortBy = 'id';

    private readonly LOCATION_ID = 1;
  
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

      
    }

    
  
    submitComplaint() {
      if (this.complaintForm.valid) {
        const userData = JSON.parse(localStorage.getItem('userData') || "{}");
        const userId = userData?.userId || 0;

        const complaintData = {
          ...this.complaintForm.value,
          userId: userId,
          locationId: this.LOCATION_ID,
          createdAt: new Date(this.complaintForm.value.createdAt)
        };
        console.log(complaintData)
        this.http.post<Issues>(`http://localhost:2026/api/complaints/create/${userId}`, complaintData).subscribe({
          next: (response: Issues) => {
            console.log('Complaint saved:', response);
            this.complaints.push(response); // Add to list dynamically
            this.resetForm();
            alert("Complaint filed Successfully! The Management will review it")
          },
          error: (err) => {console.error('Error saving complaint:', err); 
            alert("Please Login/ Relogin ")}
          
        });
      }
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

      this.http.put(`http://localhost:2026/api/complaints/assign/${selectedComplaint.id}`, selectedComplaint).subscribe({
        next: (response) => {
          console.log('Complaint assigned:', response);
          selectedComplaint.status = 'Assigned';
          this.complaints[index] = selectedComplaint;
        },
        error: (err) => console.error('Error assigning complaint:', err)
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