import dayjs from 'dayjs/esm';

import { IPerson, NewPerson } from './person.model';

export const sampleWithRequiredData: IPerson = {
  id: '48b4a7bc-7213-4181-9656-aa525381b37f',
  diplomaticmission: 'Usability Borders Chicken',
  nationality: 'deposit Concrete',
  requestparty: 'Mouse',
  identity: 'invoice success',
  birthday: dayjs('2022-11-22T23:46'),
  passporttype: 'Tuna Personal',
  passportnumber: 'Forint District',
  expirationdate: dayjs('2022-11-23T16:31'),
  visatype: 'Berkshire Wyoming Track',
  reason: 'SDD withdrawal',
  otherreason: 'CSS radical',
  guest: 'Generic',
  adress: 'XML',
  administation: 'Sleek',
  automaticcheck: 'Philippines',
  manualcheck: 'projection SSL Integration',
  entries: 'Rapid Tactics',
  exits: 'Investment Small',
  lastmove: 'zero invoice virtual',
  summary: 'Rustic',
};

export const sampleWithPartialData: IPerson = {
  id: '5ac9c7d8-e4a0-45b1-a34f-25b516ad5cce',
  diplomaticmission: 'California Buckinghamshire best-of-breed',
  nationality: 'harness secondary Advanced',
  requestparty: 'Executive Delaware',
  identity: 'red',
  birthday: dayjs('2022-11-23T04:55'),
  passporttype: 'Refined Wooden',
  passportnumber: 'Loan Investor',
  expirationdate: dayjs('2022-11-22T21:48'),
  visatype: 'input',
  reason: 'green',
  otherreason: 'Afghanistan Consultant Future-proofed',
  guest: 'quantify Missouri Shoes',
  adress: 'initiatives deposit invoice',
  administation: 'blockchains website',
  automaticcheck: 'calculate Euro pixel',
  manualcheck: 'Incredible',
  entries: 'enterprise parse bluetooth',
  exits: 'JSON Cheese synthesizing',
  lastmove: 'Quality',
  summary: 'Wooden integrated hacking',
};

export const sampleWithFullData: IPerson = {
  id: '01ec8c5d-a3d2-4c4f-ae51-c3813ec383d1',
  diplomaticmission: 'Avon',
  nationality: 'magenta',
  requestparty: 'Avon panel index',
  identity: 'drive',
  birthday: dayjs('2022-11-22T23:34'),
  passporttype: 'streamline Buckinghamshire THX',
  passportnumber: 'Web Consultant Investment',
  expirationdate: dayjs('2022-11-23T03:53'),
  visatype: 'Senior Checking port',
  reason: 'THX',
  otherreason: 'Concrete Keyboard action-items',
  guest: 'Investment Fresh',
  adress: 'Universal edge',
  administation: 'Borders program',
  automaticcheck: 'optimal',
  manualcheck: 'XML',
  entries: 'killer Tuna',
  exits: 'projection Division',
  lastmove: 'Granite',
  summary: 'Home grey Plastic',
};

export const sampleWithNewData: NewPerson = {
  diplomaticmission: 'system Points',
  nationality: 'Frozen',
  requestparty: 'Radial Loan input',
  identity: 'synergies',
  birthday: dayjs('2022-11-22T22:05'),
  passporttype: 'THX Market',
  passportnumber: 'Administrator unleash',
  expirationdate: dayjs('2022-11-23T04:34'),
  visatype: 'Music portals deposit',
  reason: 'navigate Cook',
  otherreason: 'next-generation',
  guest: 'Ergonomic',
  adress: 'Web',
  administation: 'invoice card',
  automaticcheck: 'Serbia Industrial Franc',
  manualcheck: 'feed Refined',
  entries: 'Accounts Knolls',
  exits: 'Dirham Bermuda Tunisian',
  lastmove: 'auxiliary and',
  summary: 'override',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
