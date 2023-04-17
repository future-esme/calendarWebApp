import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IUserSettings, UserSettings } from '../user-settings.model';
import {UserSettingsService} from "../service/user-settings.service";
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-user-settings-detail',
  templateUrl: './user-settings-default-detail.component.html',
})
export class UserSettingsDefaultDetailComponent implements OnInit {
  userSettings: IUserSettings | null = null;
  isEdit = false

  weekFirstDay = ['SATURDAY', 'SUNDAY', 'MONDAY']
  languages = ['RO', 'RU', 'EN']

  editForm = this.fb.group({
    weekFirstDay: ['', [Validators.required]],
    weekNumber: [false, [Validators.required]],
    keepTrash: [false, [Validators.required]],
    emailLanguage: ['', [Validators.required]]
  });

  constructor(protected activatedRoute: ActivatedRoute,
              protected userSettingsService: UserSettingsService,
              protected fb: FormBuilder,
              public router: Router) {}

  ngOnInit(): void {
    this.fetchUserSettings()
  }

  previousState(): void {
    window.history.back();
  }

  editDefaultSettings() {
    this.editForm.controls['weekFirstDay'].setValue(this.userSettings?.weekFirstDay!);
    this.editForm.controls['weekNumber'].setValue(this.userSettings?.weekNumber!);
    this.editForm.controls['keepTrash'].setValue(this.userSettings?.keepTrash!);
    this.editForm.controls['emailLanguage'].setValue(this.userSettings?.emailLanguage!);
    this.isEdit = true
  }

  save() {
    this.userSettingsService.updateDefault(new UserSettings(
      null,
      this.editForm.get(['weekFirstDay'])!.value,
      this.editForm.get(['weekNumber'])!.value,
      this.editForm.get(['keepTrash'])!.value,
      this.editForm.get(['emailLanguage'])!.value,
      null
    )).subscribe(() => {
      this.fetchUserSettings()
      this.isEdit = false
    })
  }

  fetchUserSettings() {
    this.userSettingsService.findDefault().subscribe(res => {
      this.userSettings = res.body
    })
  }
}
