import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tag',
        data: { pageTitle: 'calendarApp.tag.home.title' },
        loadChildren: () => import('./tag/tag.module').then(m => m.TagModule),
      },
      {
        path: 'user-settings',
        data: { pageTitle: 'calendarApp.userSettings.home.title' },
        loadChildren: () => import('./user-settings/user-settings.module').then(m => m.UserSettingsModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'calendarApp.event.home.title' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'calendar',
        data: { pageTitle: 'calendarApp.event.home.title' },
        loadChildren: () => import('./calendar/calendar.module').then(m => m.CalendarModule),
      },
      {
        path: 'my-tags',
        data: { pageTitle: 'calendarApp.tag.home.title' },
        loadChildren: () => import('./user-tags/user-tags.module').then(m => m.UserTagsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
