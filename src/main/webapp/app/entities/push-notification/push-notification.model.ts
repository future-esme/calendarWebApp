import { IUser } from 'app/entities/user/user.model';

export interface IPushNotification {
  id: string;
  title?: string | null;
  content?: string | null;
  userId?: Pick<IUser, 'id'> | null;
}

export type NewPushNotification = Omit<IPushNotification, 'id'> & { id: null };
