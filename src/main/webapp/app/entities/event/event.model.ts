import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface IEvent {
  id: string;
  title?: string | null;
  location?: string | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  notes?: string | null;
  sendPushNotification?: boolean | null;
  sendEmailNotification?: boolean | null;
  notificationTime?: dayjs.Dayjs | null;
  status?: string | null;
  userId?: Pick<IUser, 'id'> | null;
  tagId?: ITag | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
