import {IEvent} from "../../event/event.model";

export class EventIterator {
  private readonly events: IEvent[] = []
  private position = 0

  constructor(events: IEvent[]) {
    this.events = events
  }

  hasNext(): boolean {
    return this.position < this.events.length
  }

  next(): IEvent {
    const event = this.events[this.position]
    this.position ++
    return event
  }

  hasPrev(): boolean {
    return this.position <= this.events.length && this.position != 1
  }

  prev(): IEvent {
    this.position --
    return this.events[this.position]
  }
}
