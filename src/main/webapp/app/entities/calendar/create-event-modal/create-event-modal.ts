import {Component, OnInit} from "@angular/core";
import {FormBuilder, Validators} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {CalendarService} from "../service/calendar.service";
import {EventDTO} from "../calendar.model";
import {ITag} from "../../user-tags/tag.model";
import dayjs from 'dayjs/esm';
import utc from 'dayjs/esm/plugin/utc';
import timezone from 'dayjs/esm/plugin/timezone';
import {DATE_TIME_FORMAT} from "../../../config/input.constants";

@Component({
  selector: 'create-event',
  templateUrl: './create-event-modal.html',
})
export class CreateEventModal implements OnInit {
  event: EventDTO | null = null
  tags: ITag[] = []
  editForm = this.fb.group({
    title: [null, [Validators.required]],
    location: [null, [Validators.required]],
    startTime: [dayjs()],
    endTime: [dayjs()],
    notes: [null],
    sendPushNotification: [false, [Validators.required]],
    sendEmailNotification: [false, [Validators.required]],
    notificationTime: [null],
    isAllDay: [false],
    eventDate: [null],
    status: [null, [Validators.required]],
    tagId: [null, [Validators.required]],
  });

  timeZone = 'Europe/Chisinau'
  constructor(protected calendarService: CalendarService, protected activeModal: NgbActiveModal, protected fb: FormBuilder) {}

  cancel(): void {
    this.activeModal.dismiss();
  }
  ngOnInit(): void {
    this.calendarService.queryAllTags().subscribe(res => {
      this.tags = res.body ?? []
    })
    dayjs.extend(timezone);
    dayjs.extend(utc)
  }

  create() {
    let newEvent = this.getNewEvent()
    this.calendarService.create(newEvent).subscribe(res => {
      this.activeModal.close();
    })
  }

  getNewEvent(): EventDTO {
    let eventNew = new EventDTO();
    eventNew.title = this.editForm.get('title')!.value
    eventNew.location = this.editForm.get('location')!.value
    eventNew.tagId = this.editForm.get('tagId')!.value
    if (this.editForm.get('notes')!.value != null) {
      eventNew.notes = this.editForm.get('notes')!.value
    }
    eventNew.isAllDay = this.editForm.get('isAllDay')!.value
    if (!this.editForm.get('isAllDay')!.value) {
      eventNew.startTime = dayjs(this.editForm.get('startTime')!.value, DATE_TIME_FORMAT).tz(this.timeZone)
      eventNew.endTime = dayjs(this.editForm.get('endTime')!.value, DATE_TIME_FORMAT).tz(this.timeZone)
    } else {
      eventNew.eventDate = dayjs(this.editForm.get('eventDate')!.value, DATE_TIME_FORMAT).tz(this.timeZone)
    }
    if (this.editForm.get('sendPushNotification')!.value || this.editForm.get('sendEmailNotification')!.value) {
      eventNew.notificationTime = dayjs(this.editForm.get('notificationTime')!.value, DATE_TIME_FORMAT).tz(this.timeZone)
    }
    eventNew.sendEmailNotification = this.editForm.get('sendEmailNotification')!.value
    eventNew.sendPushNotification = this.editForm.get('sendPushNotification')!.value

    return eventNew;
  }
}
