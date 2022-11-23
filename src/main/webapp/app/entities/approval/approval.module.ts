import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApprovalComponent } from './list/approval.component';
import { ApprovalDetailComponent } from './detail/approval-detail.component';
import { ApprovalUpdateComponent } from './update/approval-update.component';
import { ApprovalDeleteDialogComponent } from './delete/approval-delete-dialog.component';
import { ApprovalRoutingModule } from './route/approval-routing.module';

@NgModule({
  imports: [SharedModule, ApprovalRoutingModule],
  declarations: [ApprovalComponent, ApprovalDetailComponent, ApprovalUpdateComponent, ApprovalDeleteDialogComponent],
})
export class ApprovalModule {}
