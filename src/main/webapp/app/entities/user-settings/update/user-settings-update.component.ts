import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UserSettingsFormService, UserSettingsFormGroup } from './user-settings-form.service';
import { IUserSettings } from '../user-settings.model';
import { UserSettingsService } from '../service/user-settings.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-user-settings-update',
  templateUrl: './user-settings-update.component.html',
})
export class UserSettingsUpdateComponent implements OnInit {
  isSaving = false;
  userSettings: IUserSettings | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: UserSettingsFormGroup = this.userSettingsFormService.createUserSettingsFormGroup();

  constructor(
    protected userSettingsService: UserSettingsService,
    protected userSettingsFormService: UserSettingsFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSettings }) => {
      this.userSettings = userSettings;
      if (userSettings) {
        this.updateForm(userSettings);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userSettings = this.userSettingsFormService.getUserSettings(this.editForm);
    if (userSettings.id !== null) {
      this.subscribeToSaveResponse(this.userSettingsService.update(userSettings));
    } else {
      this.subscribeToSaveResponse(this.userSettingsService.create(userSettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSettings>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(userSettings: IUserSettings): void {
    this.userSettings = userSettings;
    this.userSettingsFormService.resetForm(this.editForm, userSettings);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, userSettings.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.userSettings?.userId)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
