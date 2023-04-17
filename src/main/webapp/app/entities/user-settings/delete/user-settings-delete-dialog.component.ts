import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserSettings } from '../user-settings.model';
import { UserSettingsService } from '../service/user-settings.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-settings-delete-dialog.component.html',
})
export class UserSettingsDeleteDialogComponent {
  userSettings?: IUserSettings;

  constructor(protected userSettingsService: UserSettingsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.userSettingsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
