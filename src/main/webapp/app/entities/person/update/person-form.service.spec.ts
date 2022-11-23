import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person.test-samples';

import { PersonFormService } from './person-form.service';

describe('Person Form Service', () => {
  let service: PersonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonFormService);
  });

  describe('Service methods', () => {
    describe('createPersonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diplomaticmission: expect.any(Object),
            nationality: expect.any(Object),
            requestparty: expect.any(Object),
            identity: expect.any(Object),
            birthday: expect.any(Object),
            passporttype: expect.any(Object),
            passportnumber: expect.any(Object),
            expirationdate: expect.any(Object),
            visatype: expect.any(Object),
            reason: expect.any(Object),
            otherreason: expect.any(Object),
            guest: expect.any(Object),
            adress: expect.any(Object),
            administation: expect.any(Object),
            automaticcheck: expect.any(Object),
            manualcheck: expect.any(Object),
            entries: expect.any(Object),
            exits: expect.any(Object),
            lastmove: expect.any(Object),
            summary: expect.any(Object),
            requests: expect.any(Object),
          })
        );
      });

      it('passing IPerson should create a new form with FormGroup', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diplomaticmission: expect.any(Object),
            nationality: expect.any(Object),
            requestparty: expect.any(Object),
            identity: expect.any(Object),
            birthday: expect.any(Object),
            passporttype: expect.any(Object),
            passportnumber: expect.any(Object),
            expirationdate: expect.any(Object),
            visatype: expect.any(Object),
            reason: expect.any(Object),
            otherreason: expect.any(Object),
            guest: expect.any(Object),
            adress: expect.any(Object),
            administation: expect.any(Object),
            automaticcheck: expect.any(Object),
            manualcheck: expect.any(Object),
            entries: expect.any(Object),
            exits: expect.any(Object),
            lastmove: expect.any(Object),
            summary: expect.any(Object),
            requests: expect.any(Object),
          })
        );
      });
    });

    describe('getPerson', () => {
      it('should return NewPerson for default Person initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonFormGroup(sampleWithNewData);

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerson for empty Person initial value', () => {
        const formGroup = service.createPersonFormGroup();

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject({});
      });

      it('should return IPerson', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerson should not enable id FormControl', () => {
        const formGroup = service.createPersonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerson should disable id FormControl', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
