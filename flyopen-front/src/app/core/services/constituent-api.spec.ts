import { TestBed } from '@angular/core/testing';

import { ConstituentApi } from './constituent-api';

describe('ConstituentApi', () => {
  let service: ConstituentApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConstituentApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
