import { IUser } from 'app/entities/user/user.model';

export interface ITag {
  id: string;
  name?: string | null;
  iconName?: string | null;
  color?: string | null;
  userId?: Pick<IUser, 'id'> | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: null };
