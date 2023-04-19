import {RouterModule, Routes} from "@angular/router";
import {UserRouteAccessService} from "../../core/auth/user-route-access.service";
import {NgModule} from "@angular/core";
import {CalendarMainComponent} from "./calendar-main.component";

const tagRoute: Routes = [
  {
    path: '',
    component: CalendarMainComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tagRoute)],
  exports: [RouterModule],
})
export class CalendarRoutingModule {}
