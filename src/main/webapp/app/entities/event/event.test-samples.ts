import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 'a31c6a78-2023-404b-961a-533ca290ed9f',
  title: 'deposit',
  startTime: dayjs('2023-04-17T02:31'),
  endTime: dayjs('2023-04-16T18:15'),
};

export const sampleWithPartialData: IEvent = {
  id: 'da40d2dc-0b4a-4532-a77c-bb71d75ba91f',
  title: 'Ergonomic JBOD',
  startTime: dayjs('2023-04-17T01:03'),
  endTime: dayjs('2023-04-16T22:55'),
  notes: 'indexing Fords Planner',
  sendEmailNotification: false,
  notificationTime: dayjs('2023-04-16T13:32'),
  status: 'Money',
};

export const sampleWithFullData: IEvent = {
  id: '29c38ccb-048e-4c37-a2f8-d90d8b54e9f7',
  title: 'synthesize orange',
  location: 'Pizza',
  startTime: dayjs('2023-04-17T03:33'),
  endTime: dayjs('2023-04-16T23:40'),
  notes: 'quantifying',
  sendPushNotification: true,
  sendEmailNotification: true,
  notificationTime: dayjs('2023-04-16T20:00'),
  status: 'e-markets',
};

export const sampleWithNewData: NewEvent = {
  title: 'calculating niches Riyal',
  startTime: dayjs('2023-04-16T10:31'),
  endTime: dayjs('2023-04-16T10:15'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
