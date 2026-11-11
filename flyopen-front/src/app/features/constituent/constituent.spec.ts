import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Constituent } from './constituent';

describe('Constituent', () => {
  let component: Constituent;
  let fixture: ComponentFixture<Constituent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Constituent],
    }).compileComponents();

    fixture = TestBed.createComponent(Constituent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
