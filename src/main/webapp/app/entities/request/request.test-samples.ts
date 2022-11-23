import dayjs from 'dayjs/esm';

import { IRequest, NewRequest } from './request.model';

export const sampleWithRequiredData: IRequest = {
  id: 'e3f0b75f-4dcd-4a52-8038-3e8f26aa67b9',
  requesttype: 'Baht system ADP',
  createddate: dayjs('2022-11-23T12:23'),
  updatedate: dayjs('2022-11-23T07:27'),
  status: 'e-business connecting Inlet',
};

export const sampleWithPartialData: IRequest = {
  id: 'f155ac5b-52c6-4fed-8203-ef3cdd92adc3',
  requesttype: 'Brand HTTP Nevada',
  createddate: dayjs('2022-11-22T21:22'),
  updatedate: dayjs('2022-11-22T22:03'),
  status: 'Computer Maine',
};

export const sampleWithFullData: IRequest = {
  id: 'c7b5654b-a088-4090-8fe1-873123102456',
  requesttype: 'SCSI system',
  createddate: dayjs('2022-11-23T09:42'),
  updatedate: dayjs('2022-11-23T10:47'),
  status: 'Keyboard',
};

export const sampleWithNewData: NewRequest = {
  requesttype: 'Streamlined Bedfordshire',
  createddate: dayjs('2022-11-22T16:56'),
  updatedate: dayjs('2022-11-22T23:10'),
  status: 'multi-byte Honduras bypassing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
