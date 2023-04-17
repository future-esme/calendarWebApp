import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: '6347cbef-2d07-49a3-b48d-089ea4705d2d',
  name: 'firmware Hampshire',
  color: 'gold',
};

export const sampleWithPartialData: ITag = {
  id: '82bd668e-06cd-47a4-a235-8c683642e046',
  name: 'Ameliorated',
  color: 'purple',
};

export const sampleWithFullData: ITag = {
  id: 'fd212328-3133-4c42-acdb-60892dd840fc',
  name: 'synthesizing tan',
  iconName: 'Brazil US',
  color: 'maroon',
};

export const sampleWithNewData: NewTag = {
  name: 'Plain',
  color: 'azure',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
