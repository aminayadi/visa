import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApprovalComponent } from '../list/approval.component';
import { ApprovalDetailComponent } from '../detail/approval-detail.component';
import { ApprovalUpdateComponent } from '../update/approval-update.component';
import { ApprovalRoutingResolveService } from './approval-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const approvalRoute: Routes = [
  {
    path: '',
    component: ApprovalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApprovalDetailComponent,
    resolve: {
      approval: ApprovalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApprovalUpdateComponent,
    resolve: {
      approval: ApprovalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApprovalUpdateComponent,
    resolve: {
      approval: ApprovalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(approvalRoute)],
  exports: [RouterModule],
})
export class ApprovalRoutingModule {}
