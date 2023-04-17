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
