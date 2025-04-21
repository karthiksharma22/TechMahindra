import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-complaints',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './complaints.component.html',
  styleUrls: ['./complaints.component.css']
})
export class ComplaintsComponent {
  fb = inject(FormBuilder);
  complaintForm: FormGroup = this.fb.group({
    registeredBy: ['', Validators.required],
    userId: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
    type: ['', Validators.required],
    description: ['', [Validators.required, Validators.minLength(10)]],
    status: ['Pending', Validators.required],
    locationId: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
  });

  registeredByOptions = ['GUEST', 'EMPLOYEE'];

  submitComplaint() {
    if (this.complaintForm.valid) {
      console.log('Complaint Data:', this.complaintForm.value);
      // TODO: Implement API call to register complaint
    }
  }
}