import dayjs from 'dayjs/esm';
import { IRequest } from 'app/entities/request/request.model';
import { IUser } from 'app/entities/user/user.model';

export interface IApproval {
  id: string;
  decission?: string | null;
  remarks?: string | null;
  createddate?: dayjs.Dayjs | null;
  updatedate?: dayjs.Dayjs | null;
  requests?: Pick<IRequest, 'id'>[] | null;
  users?: Pick<IUser, 'id' | 'login'>[] | null;
}

export type NewApproval = Omit<IApproval, 'id'> & { id: null };
