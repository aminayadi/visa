import dayjs from 'dayjs/esm';
import { IRequest } from 'app/entities/request/request.model';

export interface IPerson {
  id: string;
  diplomaticmission?: string | null;
  nationality?: string | null;
  requestparty?: string | null;
  identity?: string | null;
  birthday?: dayjs.Dayjs | null;
  passporttype?: string | null;
  passportnumber?: string | null;
  expirationdate?: dayjs.Dayjs | null;
  visatype?: string | null;
  reason?: string | null;
  otherreason?: string | null;
  guest?: string | null;
  adress?: string | null;
  administation?: string | null;
  automaticcheck?: string | null;
  manualcheck?: string | null;
  entries?: string | null;
  exits?: string | null;
  lastmove?: string | null;
  summary?: string | null;
  requests?: Pick<IRequest, 'id'>[] | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };
