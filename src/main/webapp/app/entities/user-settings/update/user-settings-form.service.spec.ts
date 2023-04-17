import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-settings.test-samples';

import { UserSettingsFormService } from './user-settings-form.service';

describe('UserSettings Form Service', () => {
  let service: UserSettingsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSettingsFormService);
  });

  describe('Service methods', () => {
    describe('createUserSettingsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserSettingsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            weekFirstDay: expect.any(Object),
            weekNumber: expect.any(Object),
            keepTrash: expect.any(Object),
            emailLanguage: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });

      it('passing IUserSettings should create a new form with FormGroup', () => {
        const formGroup = service.createUserSettingsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            weekFirstDay: expect.any(Object),
            weekNumber: expect.any(Object),
            keepTrash: expect.any(Object),
            emailLanguage: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });
    });

    describe('getUserSettings', () => {
      it('should return NewUserSettings for default UserSettings initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserSettingsFormGroup(sampleWithNewData);

        const userSettings = service.getUserSettings(formGroup) as any;

        expect(userSettings).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserSettings for empty UserSettings initial value', () => {
        const formGroup = service.createUserSettingsFormGroup();

        const userSettings = service.getUserSettings(formGroup) as any;

        expect(userSettings).toMatchObject({});
      });

      it('should return IUserSettings', () => {
        const formGroup = service.createUserSettingsFormGroup(sampleWithRequiredData);

        const userSettings = service.getUserSettings(formGroup) as any;

        expect(userSettings).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserSettings should not enable id FormControl', () => {
        const formGroup = service.createUserSettingsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserSettings should disable id FormControl', () => {
        const formGroup = service.createUserSettingsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
