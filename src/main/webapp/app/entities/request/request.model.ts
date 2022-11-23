import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface IRequest {
  id: string;
  requesttype?: string | null;
  createddate?: dayjs.Dayjs | null;
  updatedate?: dayjs.Dayjs | null;
  status?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewRequest = Omit<IRequest, 'id'> & { id: null };
