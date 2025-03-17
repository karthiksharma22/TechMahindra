import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { interval } from 'rxjs';
import { TaskListComponent } from '../task-list/task-list.component';

@Component({
  selector: 'app-employee-hom',
  imports: [TaskListComponent,CommonModule,RouterModule],
  templateUrl: './employee-hom.component.html',
  styleUrl: './employee-hom.component.css'
})
export class EmployeeHomComponent implements OnInit {
  currentTime: Date = new Date();
  currentDate: Date = new Date();
  greeting: string = '';
  employee: any = null;
  
  // Daily quote
  quote = {
    text: '',
    author: ''
  };
  
  // Stats placeholders (to be replaced with actual data from services)
  taskStats = { pending: 0 };

  
  // Collection of motivational quotes
  quotes = [
    { text: "Success is not final, failure is not fatal: It is the courage to continue that counts.", author: "Winston Churchill" },
    { text: "Believe you can and you're halfway there.", author: "Theodore Roosevelt" },
    { text: "The only way to do great work is to love what you do.", author: "Steve Jobs" },
    { text: "Don't watch the clock; do what it does. Keep going.", author: "Sam Levenson" },
    { text: "The future belongs to those who believe in the beauty of their dreams.", author: "Eleanor Roosevelt" },
    { text: "It does not matter how slowly you go as long as you do not stop.", author: "Confucius" },
    { text: "Quality is not an act, it is a habit.", author: "Aristotle" },
    { text: "The best way to predict the future is to create it.", author: "Peter Drucker" },
    { text: "Luxury is in each detail.", author: "Hubert de Givenchy" },
    { text: "Excellence is not a skill, it's an attitude.", author: "Ralph Marston" },
    { text: "True hospitality consists of giving the best of yourself to your guests.", author: "Eleanor Roosevelt" },
    { text: "What you do today can improve all your tomorrows.", author: "Ralph Marston" },
    { text: "The difference between ordinary and extraordinary is that little extra.", author: "Jimmy Johnson" },
    { text: "The key to success is to focus on goals, not obstacles.", author: "Unknown" },
    { text: "Service is the rent we pay for being. It is the very purpose of life, and not something you do in your spare time.", author: "Marian Wright Edelman" }
  ];

  constructor(private cdRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    // Set initial values
    this.updateGreeting();
    this.setRandomQuote();
    

    // Set up a random employee for demonstration
    this.employee = JSON.parse(localStorage.getItem("employeeData") || "");

    // Set random stats for demonstration purposes
    this.taskStats.pending = Math.floor(Math.random() * 20) + 1;
    this.cdRef.detectChanges();
  }

  // Update greeting based on the current time
  updateGreeting(): void {
    const hour = new Date().getHours();
    if (hour < 12) {
      this.greeting = 'Good Morning';
    } else if (hour < 18) {
      this.greeting = 'Good Afternoon';
    } else {
      this.greeting = 'Good Evening';
    }
  }

  // Select a random quote from the collection
  setRandomQuote(): void {
    const randomIndex = Math.floor(Math.random() * this.quotes.length);
    this.quote = this.quotes[randomIndex];
  }

}
