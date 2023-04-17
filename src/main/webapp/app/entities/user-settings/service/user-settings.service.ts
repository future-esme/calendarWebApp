import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {IUserSettings, NewUserSettings, UserSettings} from '../user-settings.model';

export type PartialUpdateUserSettings = Partial<IUserSettings> & Pick<IUserSettings, 'id'>;

export type EntityResponseType = HttpResponse<IUserSettings>;
export type EntityArrayResponseType = HttpResponse<IUserSettings[]>;

@Injectable({ providedIn: 'root' })
export class UserSettingsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-settings');
  protected resourceAdminUrl = this.applicationConfigService.getEndpointFor('api/admin/users-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userSettings: NewUserSettings): Observable<EntityResponseType> {
    return this.http.post<IUserSettings>(this.resourceUrl, userSettings, { observe: 'response' });
  }

  update(userSettings: IUserSettings): Observable<EntityResponseType> {
    return this.http.put<IUserSettings>(`${this.resourceUrl}/${this.getUserSettingsIdentifier(userSettings)}`, userSettings, {
      observe: 'response',
    });
  }

  partialUpdate(userSettings: PartialUpdateUserSettings): Observable<EntityResponseType> {
    return this.http.patch<IUserSettings>(`${this.resourceUrl}/${this.getUserSettingsIdentifier(userSettings)}`, userSettings, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUserSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findDefault(): Observable<EntityResponseType> {
    return this.http.get<IUserSettings>(this.resourceAdminUrl, { observe: 'response' });
  }

  updateDefault(userSettings: UserSettings): Observable<HttpResponse<{}>> {
    return this.http.post<{}>(this.resourceAdminUrl, userSettings, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserSettings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserSettingsIdentifier(userSettings: Pick<IUserSettings, 'id'>): string {
    return userSettings.id;
  }

  compareUserSettings(o1: Pick<IUserSettings, 'id'> | null, o2: Pick<IUserSettings, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserSettingsIdentifier(o1) === this.getUserSettingsIdentifier(o2) : o1 === o2;
  }

  addUserSettingsToCollectionIfMissing<Type extends Pick<IUserSettings, 'id'>>(
    userSettingsCollection: Type[],
    ...userSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userSettings: Type[] = userSettingsToCheck.filter(isPresent);
    if (userSettings.length > 0) {
      const userSettingsCollectionIdentifiers = userSettingsCollection.map(
        userSettingsItem => this.getUserSettingsIdentifier(userSettingsItem)!
      );
      const userSettingsToAdd = userSettings.filter(userSettingsItem => {
        const userSettingsIdentifier = this.getUserSettingsIdentifier(userSettingsItem);
        if (userSettingsCollectionIdentifiers.includes(userSettingsIdentifier)) {
          return false;
        }
        userSettingsCollectionIdentifiers.push(userSettingsIdentifier);
        return true;
      });
      return [...userSettingsToAdd, ...userSettingsCollection];
    }
    return userSettingsCollection;
  }
}
