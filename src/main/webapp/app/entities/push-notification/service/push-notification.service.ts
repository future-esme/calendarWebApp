import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPushNotification, NewPushNotification } from '../push-notification.model';

export type PartialUpdatePushNotification = Partial<IPushNotification> & Pick<IPushNotification, 'id'>;

export type EntityResponseType = HttpResponse<IPushNotification>;
export type EntityArrayResponseType = HttpResponse<IPushNotification[]>;

@Injectable({ providedIn: 'root' })
export class PushNotificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/push-notifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPushNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPushNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getPushNotificationIdentifier(pushNotification: Pick<IPushNotification, 'id'>): string {
    return pushNotification.id;
  }

}
