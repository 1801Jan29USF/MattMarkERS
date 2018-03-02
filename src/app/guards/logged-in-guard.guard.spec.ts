import { TestBed, async, inject } from '@angular/core/testing';

import { LoggedInGuardGuard } from './logged-in-guard.guard';

describe('LoggedInGuardGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoggedInGuardGuard]
    });
  });

  it('should ...', inject([LoggedInGuardGuard], (guard: LoggedInGuardGuard) => {
    expect(guard).toBeTruthy();
  }));
});
