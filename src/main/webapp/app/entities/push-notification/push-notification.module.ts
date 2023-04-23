import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PushNotificationComponent } from './list/push-notification.component';
import { PushNotificationDetailComponent } from './detail/push-notification-detail.component';
import { PushNotificationRoutingModule } from './route/push-notification-routing.module';

@NgModule({
  imports: [SharedModule, PushNotificationRoutingModule],
  declarations: [
    PushNotificationComponent,
    PushNotificationDetailComponent
  ],
})
export class PushNotificationModule {}
