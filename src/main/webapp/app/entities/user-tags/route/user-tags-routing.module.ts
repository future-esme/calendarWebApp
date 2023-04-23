import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserTagsComponent } from '../list/user-tags.component';
import { UserTagsUpdateComponent } from '../update/user-tags-update.component';
import { UserTagsRoutingResolveService } from './user-tags-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tagRoute: Routes = [
  {
    path: '',
    component: UserTagsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserTagsUpdateComponent,
    resolve: {
      tag: UserTagsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserTagsUpdateComponent,
    resolve: {
      tag: UserTagsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tagRoute)],
  exports: [RouterModule],
})
export class UserTagsRoutingModule {}
