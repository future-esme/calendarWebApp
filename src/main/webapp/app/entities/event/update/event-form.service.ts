import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEvent, NewEvent } from '../event.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvent for edit and NewEventFormGroupInput for create.
 */
type EventFormGroupInput = IEvent | PartialWithRequiredKeyOf<NewEvent>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEvent | NewEvent> = Omit<T, 'startTime' | 'endTime' | 'notificationTime'> & {
  startTime?: string | null;
  endTime?: string | null;
  notificationTime?: string | null;
};

type EventFormRawValue = FormValueOf<IEvent>;

type NewEventFormRawValue = FormValueOf<NewEvent>;

type EventFormDefaults = Pick<
  NewEvent,
  'id' | 'startTime' | 'endTime' | 'sendPushNotification' | 'sendEmailNotification' | 'notificationTime'
>;

type EventFormGroupContent = {
  id: FormControl<EventFormRawValue['id'] | NewEvent['id']>;
  title: FormControl<EventFormRawValue['title']>;
  location: FormControl<EventFormRawValue['location']>;
  startTime: FormControl<EventFormRawValue['startTime']>;
  endTime: FormControl<EventFormRawValue['endTime']>;
  notes: FormControl<EventFormRawValue['notes']>;
  sendPushNotification: FormControl<EventFormRawValue['sendPushNotification']>;
  sendEmailNotification: FormControl<EventFormRawValue['sendEmailNotification']>;
  notificationTime: FormControl<EventFormRawValue['notificationTime']>;
  status: FormControl<EventFormRawValue['status']>;
  userId: FormControl<EventFormRawValue['userId']>;
  tagId: FormControl<EventFormRawValue['tagId']>;
};

export type EventFormGroup = FormGroup<EventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormService {
  createEventFormGroup(event: EventFormGroupInput = { id: null }): EventFormGroup {
    const eventRawValue = this.convertEventToEventRawValue({
      ...this.getFormDefaults(),
      ...event,
    });
    return new FormGroup<EventFormGroupContent>({
      id: new FormControl(
        { value: eventRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(eventRawValue.title, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      location: new FormControl(eventRawValue.location, {
        validators: [Validators.maxLength(255)],
      }),
      startTime: new FormControl(eventRawValue.startTime, {
        validators: [Validators.required],
      }),
      endTime: new FormControl(eventRawValue.endTime, {
        validators: [Validators.required],
      }),
      notes: new FormControl(eventRawValue.notes, {
        validators: [Validators.maxLength(1024)],
      }),
      sendPushNotification: new FormControl(eventRawValue.sendPushNotification),
      sendEmailNotification: new FormControl(eventRawValue.sendEmailNotification),
      notificationTime: new FormControl(eventRawValue.notificationTime),
      status: new FormControl(eventRawValue.status, {
        validators: [Validators.maxLength(50)],
      }),
      userId: new FormControl(eventRawValue.userId),
      tagId: new FormControl(eventRawValue.tagId),
    });
  }

  getEvent(form: EventFormGroup): IEvent | NewEvent {
    return this.convertEventRawValueToEvent(form.getRawValue() as EventFormRawValue | NewEventFormRawValue);
  }

  resetForm(form: EventFormGroup, event: EventFormGroupInput): void {
    const eventRawValue = this.convertEventToEventRawValue({ ...this.getFormDefaults(), ...event });
    form.reset(
      {
        ...eventRawValue,
        id: { value: eventRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startTime: currentTime,
      endTime: currentTime,
      sendPushNotification: false,
      sendEmailNotification: false,
      notificationTime: currentTime,
    };
  }

  private convertEventRawValueToEvent(rawEvent: EventFormRawValue | NewEventFormRawValue): IEvent | NewEvent {
    return {
      ...rawEvent,
      startTime: dayjs(rawEvent.startTime, DATE_TIME_FORMAT),
      endTime: dayjs(rawEvent.endTime, DATE_TIME_FORMAT),
      notificationTime: dayjs(rawEvent.notificationTime, DATE_TIME_FORMAT),
    };
  }

  private convertEventToEventRawValue(
    event: IEvent | (Partial<NewEvent> & EventFormDefaults)
  ): EventFormRawValue | PartialWithRequiredKeyOf<NewEventFormRawValue> {
    return {
      ...event,
      startTime: event.startTime ? event.startTime.format(DATE_TIME_FORMAT) : undefined,
      endTime: event.endTime ? event.endTime.format(DATE_TIME_FORMAT) : undefined,
      notificationTime: event.notificationTime ? event.notificationTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
