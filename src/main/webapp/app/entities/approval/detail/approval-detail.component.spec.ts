import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApprovalDetailComponent } from './approval-detail.component';

describe('Approval Management Detail Component', () => {
  let comp: ApprovalDetailComponent;
  let fixture: ComponentFixture<ApprovalDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApprovalDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ approval: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ApprovalDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApprovalDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load approval on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.approval).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
