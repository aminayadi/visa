import dayjs from 'dayjs/esm';

import { IApproval, NewApproval } from './approval.model';

export const sampleWithRequiredData: IApproval = {
  id: '41c099d8-3570-491e-b353-d00b286e724a',
  decission: 'Handmade Unit',
  createddate: dayjs('2022-11-23T10:49'),
  updatedate: dayjs('2022-11-22T20:40'),
};

export const sampleWithPartialData: IApproval = {
  id: '87000166-9b67-42e9-a74a-a0a664ffa4bc',
  decission: 'Producer',
  createddate: dayjs('2022-11-22T20:02'),
  updatedate: dayjs('2022-11-23T12:00'),
};

export const sampleWithFullData: IApproval = {
  id: '747b6847-7db3-4dd7-894c-09b4804d0f4c',
  decission: 'background',
  remarks: 'Car',
  createddate: dayjs('2022-11-23T12:14'),
  updatedate: dayjs('2022-11-23T14:32'),
};

export const sampleWithNewData: NewApproval = {
  decission: 'Loan Reverse-engineered migration',
  createddate: dayjs('2022-11-23T13:04'),
  updatedate: dayjs('2022-11-23T03:19'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
