import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPushNotification } from '../push-notification.model';
import { PushNotificationService } from '../service/push-notification.service';

@Injectable({ providedIn: 'root' })
export class PushNotificationRoutingResolveService implements Resolve<IPushNotification | null> {
  constructor(protected service: PushNotificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPushNotification | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pushNotification: HttpResponse<IPushNotification>) => {
          if (pushNotification.body) {
            return of(pushNotification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
