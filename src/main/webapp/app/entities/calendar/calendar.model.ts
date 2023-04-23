import dayjs from 'dayjs/esm';

export interface Calendar {
  weekDays: string[];
  month: string;
  year: number;
  weeks: Week[]
}

export interface Week {
  weekNumber: number;
  days: Day[]
}

export interface Day {
  day: number;
  targetMonth: boolean;
  hasEvents: boolean;
}

export class EventDTO {
  public title?: string | null;
  public location?: string | null;
  public startTime?: dayjs.Dayjs | null;
  public endTime?: dayjs.Dayjs | null;
  public notes?: string | null;
  public sendPushNotification?: boolean | null;
  public sendEmailNotification?: boolean | null;
  public notificationTime?: dayjs.Dayjs | null;
  public isAllDay?: boolean | null;
  public eventDate?: dayjs.Dayjs | null;
  public status?: string | null;
  public tagId?: string | null;
}
