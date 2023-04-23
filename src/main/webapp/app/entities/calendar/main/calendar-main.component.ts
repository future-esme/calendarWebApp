import {Component, OnInit} from "@angular/core";
import {CalendarService} from "../service/calendar.service";
import {Calendar, Day} from "../calendar.model";
import {IEvent} from "../../event/event.model";
import {NgbCalendar, NgbDateStruct, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CreateEventModal} from "../create-event-modal/create-event-modal";
import {EventIterator} from "./event-iterator";

@Component({
  selector: 'calendar',
  templateUrl: './calendar-main.component.html',
  styleUrls: ['calendar-main.component.css']
})
export class CalendarMainComponent implements OnInit {
  calendar: Calendar | null = null
  months = [
    'JANUARY',
    'FEBRUARY',
    'MARCH',
    'APRIL',
    'MAY',
    'JUNE',
    'JULY',
    'AUGUST',
    'SEPTEMBER',
    'OCTOBER',
    'NOVEMBER',
    'DECEMBER']
  selectedMonth = 'APRIL'
  year = 2023
  selectedDay = 1

  selectedDate: NgbDateStruct | undefined = undefined;

  events: IEvent[] = []
  event: IEvent | null = null
  eventIterator: EventIterator | null = null
  constructor(protected calendarService: CalendarService,
              protected ngbCalendar: NgbCalendar,
              protected modalService: NgbModal) {}
  ngOnInit(): void {
    this.today()
    this.getCalendar()
  }

  showEventsDay(day: Day) {
    if (day.targetMonth) {
      this.selectedDay = day.day
      this.calendarService.queryMyEvents(day.day, this.selectedMonth, this.year).subscribe(res => {
        this.events = res.body ?? []
        this.event = null
        this.eventIterator = new EventIterator(this.events)
        if (this.eventIterator.hasNext()) {
          this.displayEventOverIterator(this.eventIterator.next())
        }
      })
    }
  }

  today() {
    this.selectedDate = this.ngbCalendar.getToday()
    this.year = this.selectedDate.year
    this.selectedDay = this.selectedDate.day
    this.selectedMonth = this.months[this.selectedDate.month - 1]
  }

  prevMonth() {
    let targetMonthOrder = this.months.indexOf(this.selectedMonth) - 1
    if (targetMonthOrder < 0) {
      targetMonthOrder = 11
      this.year -= 1
    }
    this.selectedMonth = this.months[targetMonthOrder];
    this.getCalendar()
  }

  nextMonth() {
    let targetMonthOrder = this.months.indexOf(this.selectedMonth) + 1
    if (targetMonthOrder > 11) {
      targetMonthOrder = 0
      this.year += 1
    }
    this.selectedMonth = this.months[targetMonthOrder];
    this.getCalendar()
  }


  isSelectedDay(current: Day): boolean {
    return current.day == this.selectedDay && current.targetMonth
  }

  addEvent() {
    const modalRef = this.modalService.open(CreateEventModal, { size: 'md', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      this.getCalendar()
    })
  }

  displayEvent(eventId: string) {
    this.calendarService.findEvent(eventId).subscribe(res => {
      if (res.body != null) {
        this.event = res.body ?? null
      }
    })
  }

  displayEventOverIterator(event: IEvent) {
    this.event = event
  }

  deleteEvent(eventId: string) {
    this.calendarService.delete(eventId).subscribe(() => {
      this.getCalendar()
    })
  }

  hasNext(): boolean {
    return this.eventIterator != undefined && this.eventIterator?.hasNext()
  }

  next() {
    if (this.eventIterator != null) this.displayEventOverIterator(this.eventIterator?.next())
  }

  hasPrev(): boolean {
    return this.eventIterator != undefined && this.eventIterator?.hasPrev()
  }

  prev() {
    if (this.eventIterator != null) this.displayEventOverIterator(this.eventIterator?.prev())
  }

  private getCurrentDayEvents() {
    this.calendar?.weeks.forEach(week => {
      week.days.forEach(day => {
        if (day.day == this.selectedDay) {
          this.showEventsDay(day)
        }
      })
    })
  }

  private getCalendar() {
    this.calendarService.find(this.selectedMonth, this.year).subscribe((res) => {
      this.calendar = res.body;
      this.getCurrentDayEvents()
    })
  }
}
