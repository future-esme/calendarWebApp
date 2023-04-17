import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserSettingsComponent } from '../list/user-settings.component';
import { UserSettingsDetailComponent } from '../detail/user-settings-detail.component';
import { UserSettingsUpdateComponent } from '../update/user-settings-update.component';
import { UserSettingsRoutingResolveService } from './user-settings-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userSettingsRoute: Routes = [
  {
    path: '',
    component: UserSettingsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserSettingsDetailComponent,
    resolve: {
      userSettings: UserSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserSettingsUpdateComponent,
    resolve: {
      userSettings: UserSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserSettingsUpdateComponent,
    resolve: {
      userSettings: UserSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userSettingsRoute)],
  exports: [RouterModule],
})
export class UserSettingsRoutingModule {}
