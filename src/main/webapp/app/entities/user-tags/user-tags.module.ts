import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserTagsComponent } from './list/user-tags.component';
import { UserTagsUpdateComponent } from './update/user-tags-update.component';
import { UserTagsDeleteDialogComponent } from './delete/user-tags-delete-dialog.component';
import { UserTagsRoutingModule } from './route/user-tags-routing.module';

@NgModule({
  imports: [SharedModule, UserTagsRoutingModule],
  declarations: [UserTagsComponent, UserTagsUpdateComponent, UserTagsDeleteDialogComponent],
})
export class UserTagsModule {}
