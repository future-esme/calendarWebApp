import { IUser } from 'app/entities/user/user.model';

export interface IUserSettings {
  id: string;
  weekFirstDay?: string | null;
  weekNumber?: boolean | null;
  keepTrash?: boolean | null;
  emailLanguage?: string | null;
  userId?: Pick<IUser, 'id'> | null;
}

export type NewUserSettings = Omit<IUserSettings, 'id'> & { id: null };
