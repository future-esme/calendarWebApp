import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserSettings } from '../user-settings.model';

@Component({
  selector: 'jhi-user-settings-detail',
  templateUrl: './user-settings-detail.component.html',
})
export class UserSettingsDetailComponent implements OnInit {
  userSettings: IUserSettings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSettings }) => {
      this.userSettings = userSettings;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
