import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DieSearchComponent } from './die-search.component';

describe('DieSearchComponent', () => {
  let component: DieSearchComponent;
  let fixture: ComponentFixture<DieSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DieSearchComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DieSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
