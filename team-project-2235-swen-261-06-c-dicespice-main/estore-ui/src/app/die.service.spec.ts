import { TestBed } from '@angular/core/testing';

import { DieService } from './die.service';

describe('DieService', () => {
  let service: DieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
