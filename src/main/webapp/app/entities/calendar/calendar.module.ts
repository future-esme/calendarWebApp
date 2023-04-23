import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {CalendarMainComponent} from "./main/calendar-main.component";
import {CalendarRoutingModule} from "./calendar-routing.module";
import {CreateEventModal} from "./create-event-modal/create-event-modal";

@NgModule({
  imports: [SharedModule, CalendarRoutingModule],
  declarations: [CalendarMainComponent, CreateEventModal],
})
export class CalendarModule {}
