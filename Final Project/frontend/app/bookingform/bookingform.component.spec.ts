import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingformComponent } from './bookingform.component';

describe('BookingformComponent', () => {
  let component: BookingformComponent;
  let fixture: ComponentFixture<BookingformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingformComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
