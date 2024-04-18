import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DieDetailComponent } from './die-detail.component';

describe('DieDetailComponent', () => {
  let component: DieDetailComponent;
  let fixture: ComponentFixture<DieDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DieDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DieDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
