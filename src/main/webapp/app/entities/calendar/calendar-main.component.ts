import {Component, OnInit} from "@angular/core";
import {CalendarService} from "./calendar.service";
import {Calendar} from "./calendar.model";
import {IEvent} from "../event/event.model";
import { NgbCalendar, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

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
  constructor(protected calendarService: CalendarService,
              protected ngbCalendar: NgbCalendar) {}
  ngOnInit(): void {
    this.today()
    this.calendarService.find(this.selectedMonth, this.year).subscribe((res) => {
      this.calendar = res.body;
    })
  }

  showEventsDay(day: number) {
    this.selectedDay = day
    this.calendarService.queryMyEvents(day, this.selectedMonth, this.year).subscribe(res => {
      this.events = res.body ?? []
    })
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
  }

  nextMonth() {
    let targetMonthOrder = this.months.indexOf(this.selectedMonth) + 1
    if (targetMonthOrder > 11) {
      targetMonthOrder = 0
      this.year += 1
    }
    this.selectedMonth = this.months[targetMonthOrder];
  }

}
