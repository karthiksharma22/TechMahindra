import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeHomComponent } from './employee-hom.component';

describe('EmployeeHomComponent', () => {
  let component: EmployeeHomComponent;
  let fixture: ComponentFixture<EmployeeHomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeHomComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeHomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
