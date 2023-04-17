import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserSettings, NewUserSettings } from '../user-settings.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserSettings for edit and NewUserSettingsFormGroupInput for create.
 */
type UserSettingsFormGroupInput = IUserSettings | PartialWithRequiredKeyOf<NewUserSettings>;

type UserSettingsFormDefaults = Pick<NewUserSettings, 'id' | 'weekNumber' | 'keepTrash'>;

type UserSettingsFormGroupContent = {
  id: FormControl<IUserSettings['id'] | NewUserSettings['id']>;
  weekFirstDay: FormControl<IUserSettings['weekFirstDay']>;
  weekNumber: FormControl<IUserSettings['weekNumber']>;
  keepTrash: FormControl<IUserSettings['keepTrash']>;
  emailLanguage: FormControl<IUserSettings['emailLanguage']>;
  userId: FormControl<IUserSettings['userId']>;
};

export type UserSettingsFormGroup = FormGroup<UserSettingsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserSettingsFormService {
  createUserSettingsFormGroup(userSettings: UserSettingsFormGroupInput = { id: null }): UserSettingsFormGroup {
    const userSettingsRawValue = {
      ...this.getFormDefaults(),
      ...userSettings,
    };
    return new FormGroup<UserSettingsFormGroupContent>({
      id: new FormControl(
        { value: userSettingsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      weekFirstDay: new FormControl(userSettingsRawValue.weekFirstDay, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      weekNumber: new FormControl(userSettingsRawValue.weekNumber),
      keepTrash: new FormControl(userSettingsRawValue.keepTrash),
      emailLanguage: new FormControl(userSettingsRawValue.emailLanguage, {
        validators: [Validators.maxLength(10)],
      }),
      userId: new FormControl(userSettingsRawValue.userId),
    });
  }

  getUserSettings(form: UserSettingsFormGroup): IUserSettings | NewUserSettings {
    return form.getRawValue() as IUserSettings | NewUserSettings;
  }

  resetForm(form: UserSettingsFormGroup, userSettings: UserSettingsFormGroupInput): void {
    const userSettingsRawValue = { ...this.getFormDefaults(), ...userSettings };
    form.reset(
      {
        ...userSettingsRawValue,
        id: { value: userSettingsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserSettingsFormDefaults {
    return {
      id: null,
      weekNumber: false,
      keepTrash: false,
    };
  }
}
