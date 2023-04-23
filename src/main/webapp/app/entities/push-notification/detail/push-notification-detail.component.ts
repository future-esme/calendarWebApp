import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPushNotification } from '../push-notification.model';

@Component({
  selector: 'jhi-push-notification-detail',
  templateUrl: './push-notification-detail.component.html',
})
export class PushNotificationDetailComponent implements OnInit {
  pushNotification: IPushNotification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pushNotification }) => {
      this.pushNotification = pushNotification;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
