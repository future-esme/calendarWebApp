import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserSettings } from '../user-settings.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-settings.test-samples';

import { UserSettingsService } from './user-settings.service';

const requireRestSample: IUserSettings = {
  ...sampleWithRequiredData,
};

describe('UserSettings Service', () => {
  let service: UserSettingsService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserSettings | IUserSettings[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserSettingsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a UserSettings', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userSettings = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userSettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserSettings', () => {
      const userSettings = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userSettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserSettings', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserSettings', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserSettings', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserSettingsToCollectionIfMissing', () => {
      it('should add a UserSettings to an empty array', () => {
        const userSettings: IUserSettings = sampleWithRequiredData;
        expectedResult = service.addUserSettingsToCollectionIfMissing([], userSettings);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSettings);
      });

      it('should not add a UserSettings to an array that contains it', () => {
        const userSettings: IUserSettings = sampleWithRequiredData;
        const userSettingsCollection: IUserSettings[] = [
          {
            ...userSettings,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserSettingsToCollectionIfMissing(userSettingsCollection, userSettings);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserSettings to an array that doesn't contain it", () => {
        const userSettings: IUserSettings = sampleWithRequiredData;
        const userSettingsCollection: IUserSettings[] = [sampleWithPartialData];
        expectedResult = service.addUserSettingsToCollectionIfMissing(userSettingsCollection, userSettings);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSettings);
      });

      it('should add only unique UserSettings to an array', () => {
        const userSettingsArray: IUserSettings[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userSettingsCollection: IUserSettings[] = [sampleWithRequiredData];
        expectedResult = service.addUserSettingsToCollectionIfMissing(userSettingsCollection, ...userSettingsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userSettings: IUserSettings = sampleWithRequiredData;
        const userSettings2: IUserSettings = sampleWithPartialData;
        expectedResult = service.addUserSettingsToCollectionIfMissing([], userSettings, userSettings2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSettings);
        expect(expectedResult).toContain(userSettings2);
      });

      it('should accept null and undefined values', () => {
        const userSettings: IUserSettings = sampleWithRequiredData;
        expectedResult = service.addUserSettingsToCollectionIfMissing([], null, userSettings, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSettings);
      });

      it('should return initial array if no UserSettings is added', () => {
        const userSettingsCollection: IUserSettings[] = [sampleWithRequiredData];
        expectedResult = service.addUserSettingsToCollectionIfMissing(userSettingsCollection, undefined, null);
        expect(expectedResult).toEqual(userSettingsCollection);
      });
    });

    describe('compareUserSettings', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserSettings(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareUserSettings(entity1, entity2);
        const compareResult2 = service.compareUserSettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareUserSettings(entity1, entity2);
        const compareResult2 = service.compareUserSettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareUserSettings(entity1, entity2);
        const compareResult2 = service.compareUserSettings(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
