import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserSettingsComponent } from './list/user-settings.component';
import { UserSettingsDetailComponent } from './detail/user-settings-detail.component';
import { UserSettingsUpdateComponent } from './update/user-settings-update.component';
import { UserSettingsDeleteDialogComponent } from './delete/user-settings-delete-dialog.component';
import { UserSettingsRoutingModule } from './route/user-settings-routing.module';

@NgModule({
  imports: [SharedModule, UserSettingsRoutingModule],
  declarations: [UserSettingsComponent, UserSettingsDetailComponent, UserSettingsUpdateComponent, UserSettingsDeleteDialogComponent],
})
export class UserSettingsModule {}
