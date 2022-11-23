import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPerson, NewPerson } from '../person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerson for edit and NewPersonFormGroupInput for create.
 */
type PersonFormGroupInput = IPerson | PartialWithRequiredKeyOf<NewPerson>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPerson | NewPerson> = Omit<T, 'birthday' | 'expirationdate'> & {
  birthday?: string | null;
  expirationdate?: string | null;
};

type PersonFormRawValue = FormValueOf<IPerson>;

type NewPersonFormRawValue = FormValueOf<NewPerson>;

type PersonFormDefaults = Pick<NewPerson, 'id' | 'birthday' | 'expirationdate' | 'requests'>;

type PersonFormGroupContent = {
  id: FormControl<PersonFormRawValue['id'] | NewPerson['id']>;
  diplomaticmission: FormControl<PersonFormRawValue['diplomaticmission']>;
  nationality: FormControl<PersonFormRawValue['nationality']>;
  requestparty: FormControl<PersonFormRawValue['requestparty']>;
  identity: FormControl<PersonFormRawValue['identity']>;
  birthday: FormControl<PersonFormRawValue['birthday']>;
  passporttype: FormControl<PersonFormRawValue['passporttype']>;
  passportnumber: FormControl<PersonFormRawValue['passportnumber']>;
  expirationdate: FormControl<PersonFormRawValue['expirationdate']>;
  visatype: FormControl<PersonFormRawValue['visatype']>;
  reason: FormControl<PersonFormRawValue['reason']>;
  otherreason: FormControl<PersonFormRawValue['otherreason']>;
  guest: FormControl<PersonFormRawValue['guest']>;
  adress: FormControl<PersonFormRawValue['adress']>;
  administation: FormControl<PersonFormRawValue['administation']>;
  automaticcheck: FormControl<PersonFormRawValue['automaticcheck']>;
  manualcheck: FormControl<PersonFormRawValue['manualcheck']>;
  entries: FormControl<PersonFormRawValue['entries']>;
  exits: FormControl<PersonFormRawValue['exits']>;
  lastmove: FormControl<PersonFormRawValue['lastmove']>;
  summary: FormControl<PersonFormRawValue['summary']>;
  requests: FormControl<PersonFormRawValue['requests']>;
};

export type PersonFormGroup = FormGroup<PersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonFormService {
  createPersonFormGroup(person: PersonFormGroupInput = { id: null }): PersonFormGroup {
    const personRawValue = this.convertPersonToPersonRawValue({
      ...this.getFormDefaults(),
      ...person,
    });
    return new FormGroup<PersonFormGroupContent>({
      id: new FormControl(
        { value: personRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      diplomaticmission: new FormControl(personRawValue.diplomaticmission, {
        validators: [Validators.required],
      }),
      nationality: new FormControl(personRawValue.nationality, {
        validators: [Validators.required],
      }),
      requestparty: new FormControl(personRawValue.requestparty, {
        validators: [Validators.required],
      }),
      identity: new FormControl(personRawValue.identity, {
        validators: [Validators.required],
      }),
      birthday: new FormControl(personRawValue.birthday, {
        validators: [Validators.required],
      }),
      passporttype: new FormControl(personRawValue.passporttype, {
        validators: [Validators.required],
      }),
      passportnumber: new FormControl(personRawValue.passportnumber, {
        validators: [Validators.required],
      }),
      expirationdate: new FormControl(personRawValue.expirationdate, {
        validators: [Validators.required],
      }),
      visatype: new FormControl(personRawValue.visatype, {
        validators: [Validators.required],
      }),
      reason: new FormControl(personRawValue.reason, {
        validators: [Validators.required],
      }),
      otherreason: new FormControl(personRawValue.otherreason, {
        validators: [Validators.required],
      }),
      guest: new FormControl(personRawValue.guest, {
        validators: [Validators.required],
      }),
      adress: new FormControl(personRawValue.adress, {
        validators: [Validators.required],
      }),
      administation: new FormControl(personRawValue.administation, {
        validators: [Validators.required],
      }),
      automaticcheck: new FormControl(personRawValue.automaticcheck, {
        validators: [Validators.required],
      }),
      manualcheck: new FormControl(personRawValue.manualcheck, {
        validators: [Validators.required],
      }),
      entries: new FormControl(personRawValue.entries, {
        validators: [Validators.required],
      }),
      exits: new FormControl(personRawValue.exits, {
        validators: [Validators.required],
      }),
      lastmove: new FormControl(personRawValue.lastmove, {
        validators: [Validators.required],
      }),
      summary: new FormControl(personRawValue.summary, {
        validators: [Validators.required],
      }),
      requests: new FormControl(personRawValue.requests ?? []),
    });
  }

  getPerson(form: PersonFormGroup): IPerson | NewPerson {
    return this.convertPersonRawValueToPerson(form.getRawValue() as PersonFormRawValue | NewPersonFormRawValue);
  }

  resetForm(form: PersonFormGroup, person: PersonFormGroupInput): void {
    const personRawValue = this.convertPersonToPersonRawValue({ ...this.getFormDefaults(), ...person });
    form.reset(
      {
        ...personRawValue,
        id: { value: personRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      birthday: currentTime,
      expirationdate: currentTime,
      requests: [],
    };
  }

  private convertPersonRawValueToPerson(rawPerson: PersonFormRawValue | NewPersonFormRawValue): IPerson | NewPerson {
    return {
      ...rawPerson,
      birthday: dayjs(rawPerson.birthday, DATE_TIME_FORMAT),
      expirationdate: dayjs(rawPerson.expirationdate, DATE_TIME_FORMAT),
    };
  }

  private convertPersonToPersonRawValue(
    person: IPerson | (Partial<NewPerson> & PersonFormDefaults)
  ): PersonFormRawValue | PartialWithRequiredKeyOf<NewPersonFormRawValue> {
    return {
      ...person,
      birthday: person.birthday ? person.birthday.format(DATE_TIME_FORMAT) : undefined,
      expirationdate: person.expirationdate ? person.expirationdate.format(DATE_TIME_FORMAT) : undefined,
      requests: person.requests ?? [],
    };
  }
}
