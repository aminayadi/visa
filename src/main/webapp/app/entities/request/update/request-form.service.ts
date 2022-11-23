import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRequest, NewRequest } from '../request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequest for edit and NewRequestFormGroupInput for create.
 */
type RequestFormGroupInput = IRequest | PartialWithRequiredKeyOf<NewRequest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRequest | NewRequest> = Omit<T, 'createddate' | 'updatedate'> & {
  createddate?: string | null;
  updatedate?: string | null;
};

type RequestFormRawValue = FormValueOf<IRequest>;

type NewRequestFormRawValue = FormValueOf<NewRequest>;

type RequestFormDefaults = Pick<NewRequest, 'id' | 'createddate' | 'updatedate'>;

type RequestFormGroupContent = {
  id: FormControl<RequestFormRawValue['id'] | NewRequest['id']>;
  requesttype: FormControl<RequestFormRawValue['requesttype']>;
  createddate: FormControl<RequestFormRawValue['createddate']>;
  updatedate: FormControl<RequestFormRawValue['updatedate']>;
  status: FormControl<RequestFormRawValue['status']>;
  user: FormControl<RequestFormRawValue['user']>;
};

export type RequestFormGroup = FormGroup<RequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestFormService {
  createRequestFormGroup(request: RequestFormGroupInput = { id: null }): RequestFormGroup {
    const requestRawValue = this.convertRequestToRequestRawValue({
      ...this.getFormDefaults(),
      ...request,
    });
    return new FormGroup<RequestFormGroupContent>({
      id: new FormControl(
        { value: requestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      requesttype: new FormControl(requestRawValue.requesttype, {
        validators: [Validators.required],
      }),
      createddate: new FormControl(requestRawValue.createddate, {
        validators: [Validators.required],
      }),
      updatedate: new FormControl(requestRawValue.updatedate, {
        validators: [Validators.required],
      }),
      status: new FormControl(requestRawValue.status, {
        validators: [Validators.required],
      }),
      user: new FormControl(requestRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getRequest(form: RequestFormGroup): IRequest | NewRequest {
    return this.convertRequestRawValueToRequest(form.getRawValue() as RequestFormRawValue | NewRequestFormRawValue);
  }

  resetForm(form: RequestFormGroup, request: RequestFormGroupInput): void {
    const requestRawValue = this.convertRequestToRequestRawValue({ ...this.getFormDefaults(), ...request });
    form.reset(
      {
        ...requestRawValue,
        id: { value: requestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RequestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createddate: currentTime,
      updatedate: currentTime,
    };
  }

  private convertRequestRawValueToRequest(rawRequest: RequestFormRawValue | NewRequestFormRawValue): IRequest | NewRequest {
    return {
      ...rawRequest,
      createddate: dayjs(rawRequest.createddate, DATE_TIME_FORMAT),
      updatedate: dayjs(rawRequest.updatedate, DATE_TIME_FORMAT),
    };
  }

  private convertRequestToRequestRawValue(
    request: IRequest | (Partial<NewRequest> & RequestFormDefaults)
  ): RequestFormRawValue | PartialWithRequiredKeyOf<NewRequestFormRawValue> {
    return {
      ...request,
      createddate: request.createddate ? request.createddate.format(DATE_TIME_FORMAT) : undefined,
      updatedate: request.updatedate ? request.updatedate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
