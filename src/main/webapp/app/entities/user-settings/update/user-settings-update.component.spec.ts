import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserSettingsFormService } from './user-settings-form.service';
import { UserSettingsService } from '../service/user-settings.service';
import { IUserSettings } from '../user-settings.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { UserSettingsUpdateComponent } from './user-settings-update.component';

describe('UserSettings Management Update Component', () => {
  let comp: UserSettingsUpdateComponent;
  let fixture: ComponentFixture<UserSettingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userSettingsFormService: UserSettingsFormService;
  let userSettingsService: UserSettingsService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserSettingsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UserSettingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserSettingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userSettingsFormService = TestBed.inject(UserSettingsFormService);
    userSettingsService = TestBed.inject(UserSettingsService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const userSettings: IUserSettings = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const userId: IUser = { id: 22747 };
      userSettings.userId = userId;

      const userCollection: IUser[] = [{ id: 81944 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userSettings });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userSettings: IUserSettings = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const userId: IUser = { id: 30138 };
      userSettings.userId = userId;

      activatedRoute.data = of({ userSettings });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(userId);
      expect(comp.userSettings).toEqual(userSettings);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSettings>>();
      const userSettings = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(userSettingsFormService, 'getUserSettings').mockReturnValue(userSettings);
      jest.spyOn(userSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSettings }));
      saveSubject.complete();

      // THEN
      expect(userSettingsFormService.getUserSettings).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userSettingsService.update).toHaveBeenCalledWith(expect.objectContaining(userSettings));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSettings>>();
      const userSettings = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(userSettingsFormService, 'getUserSettings').mockReturnValue({ id: null });
      jest.spyOn(userSettingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSettings: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSettings }));
      saveSubject.complete();

      // THEN
      expect(userSettingsFormService.getUserSettings).toHaveBeenCalled();
      expect(userSettingsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSettings>>();
      const userSettings = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(userSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userSettingsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
