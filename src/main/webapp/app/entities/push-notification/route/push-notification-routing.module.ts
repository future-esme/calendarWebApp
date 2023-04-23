import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PushNotificationComponent } from '../list/push-notification.component';
import { PushNotificationDetailComponent } from '../detail/push-notification-detail.component';
import { PushNotificationRoutingResolveService } from './push-notification-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pushNotificationRoute: Routes = [
  {
    path: '',
    component: PushNotificationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PushNotificationDetailComponent,
    resolve: {
      pushNotification: PushNotificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pushNotificationRoute)],
  exports: [RouterModule],
})
export class PushNotificationRoutingModule {}
