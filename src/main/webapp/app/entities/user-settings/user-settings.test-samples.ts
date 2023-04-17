import { IUserSettings, NewUserSettings } from './user-settings.model';

export const sampleWithRequiredData: IUserSettings = {
  id: '13fc2680-eea3-42e7-ab57-498400284c3b',
  weekFirstDay: 'harness mobile',
};

export const sampleWithPartialData: IUserSettings = {
  id: '25171592-38f7-4f0a-b43b-48ae5aad3e0c',
  weekFirstDay: 'web-readiness copyin',
  emailLanguage: 'Saint inte',
};

export const sampleWithFullData: IUserSettings = {
  id: '7bc87592-7953-43f5-b7b4-60a0f29d8fee',
  weekFirstDay: 'web-enabled hacking',
  weekNumber: true,
  keepTrash: false,
  emailLanguage: 'Greece',
};

export const sampleWithNewData: NewUserSettings = {
  weekFirstDay: 'violet multi-byte Su',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
