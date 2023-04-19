import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {CalendarMainComponent} from "./calendar-main.component";
import {CalendarRoutingModule} from "./calendar-routing.module";

@NgModule({
  imports: [SharedModule, CalendarRoutingModule],
  declarations: [CalendarMainComponent],
})
export class CalendarModule {}
