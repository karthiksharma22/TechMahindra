import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueAdminComponent } from './issue-admin.component';

describe('IssueAdminComponent', () => {
  let component: IssueAdminComponent;
  let fixture: ComponentFixture<IssueAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssueAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
