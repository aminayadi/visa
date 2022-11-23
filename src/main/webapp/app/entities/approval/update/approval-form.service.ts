import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApproval, NewApproval } from '../approval.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApproval for edit and NewApprovalFormGroupInput for create.
 */
type ApprovalFormGroupInput = IApproval | PartialWithRequiredKeyOf<NewApproval>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IApproval | NewApproval> = Omit<T, 'createddate' | 'updatedate'> & {
  createddate?: string | null;
  updatedate?: string | null;
};

type ApprovalFormRawValue = FormValueOf<IApproval>;

type NewApprovalFormRawValue = FormValueOf<NewApproval>;

type ApprovalFormDefaults = Pick<NewApproval, 'id' | 'createddate' | 'updatedate' | 'requests' | 'users'>;

type ApprovalFormGroupContent = {
  id: FormControl<ApprovalFormRawValue['id'] | NewApproval['id']>;
  decission: FormControl<ApprovalFormRawValue['decission']>;
  remarks: FormControl<ApprovalFormRawValue['remarks']>;
  createddate: FormControl<ApprovalFormRawValue['createddate']>;
  updatedate: FormControl<ApprovalFormRawValue['updatedate']>;
  requests: FormControl<ApprovalFormRawValue['requests']>;
  users: FormControl<ApprovalFormRawValue['users']>;
};

export type ApprovalFormGroup = FormGroup<ApprovalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApprovalFormService {
  createApprovalFormGroup(approval: ApprovalFormGroupInput = { id: null }): ApprovalFormGroup {
    const approvalRawValue = this.convertApprovalToApprovalRawValue({
      ...this.getFormDefaults(),
      ...approval,
    });
    return new FormGroup<ApprovalFormGroupContent>({
      id: new FormControl(
        { value: approvalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      decission: new FormControl(approvalRawValue.decission, {
        validators: [Validators.required],
      }),
      remarks: new FormControl(approvalRawValue.remarks),
      createddate: new FormControl(approvalRawValue.createddate, {
        validators: [Validators.required],
      }),
      updatedate: new FormControl(approvalRawValue.updatedate, {
        validators: [Validators.required],
      }),
      requests: new FormControl(approvalRawValue.requests ?? []),
      users: new FormControl(approvalRawValue.users ?? []),
    });
  }

  getApproval(form: ApprovalFormGroup): IApproval | NewApproval {
    return this.convertApprovalRawValueToApproval(form.getRawValue() as ApprovalFormRawValue | NewApprovalFormRawValue);
  }

  resetForm(form: ApprovalFormGroup, approval: ApprovalFormGroupInput): void {
    const approvalRawValue = this.convertApprovalToApprovalRawValue({ ...this.getFormDefaults(), ...approval });
    form.reset(
      {
        ...approvalRawValue,
        id: { value: approvalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApprovalFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createddate: currentTime,
      updatedate: currentTime,
      requests: [],
      users: [],
    };
  }

  private convertApprovalRawValueToApproval(rawApproval: ApprovalFormRawValue | NewApprovalFormRawValue): IApproval | NewApproval {
    return {
      ...rawApproval,
      createddate: dayjs(rawApproval.createddate, DATE_TIME_FORMAT),
      updatedate: dayjs(rawApproval.updatedate, DATE_TIME_FORMAT),
    };
  }

  private convertApprovalToApprovalRawValue(
    approval: IApproval | (Partial<NewApproval> & ApprovalFormDefaults)
  ): ApprovalFormRawValue | PartialWithRequiredKeyOf<NewApprovalFormRawValue> {
    return {
      ...approval,
      createddate: approval.createddate ? approval.createddate.format(DATE_TIME_FORMAT) : undefined,
      updatedate: approval.updatedate ? approval.updatedate.format(DATE_TIME_FORMAT) : undefined,
      requests: approval.requests ?? [],
      users: approval.users ?? [],
    };
  }
}
