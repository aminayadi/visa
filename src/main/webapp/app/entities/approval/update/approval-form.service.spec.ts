import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../approval.test-samples';

import { ApprovalFormService } from './approval-form.service';

describe('Approval Form Service', () => {
  let service: ApprovalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApprovalFormService);
  });

  describe('Service methods', () => {
    describe('createApprovalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApprovalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            decission: expect.any(Object),
            remarks: expect.any(Object),
            createddate: expect.any(Object),
            updatedate: expect.any(Object),
            requests: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });

      it('passing IApproval should create a new form with FormGroup', () => {
        const formGroup = service.createApprovalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            decission: expect.any(Object),
            remarks: expect.any(Object),
            createddate: expect.any(Object),
            updatedate: expect.any(Object),
            requests: expect.any(Object),
            users: expect.any(Object),
          })
        );
      });
    });

    describe('getApproval', () => {
      it('should return NewApproval for default Approval initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createApprovalFormGroup(sampleWithNewData);

        const approval = service.getApproval(formGroup) as any;

        expect(approval).toMatchObject(sampleWithNewData);
      });

      it('should return NewApproval for empty Approval initial value', () => {
        const formGroup = service.createApprovalFormGroup();

        const approval = service.getApproval(formGroup) as any;

        expect(approval).toMatchObject({});
      });

      it('should return IApproval', () => {
        const formGroup = service.createApprovalFormGroup(sampleWithRequiredData);

        const approval = service.getApproval(formGroup) as any;

        expect(approval).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApproval should not enable id FormControl', () => {
        const formGroup = service.createApprovalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApproval should disable id FormControl', () => {
        const formGroup = service.createApprovalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
