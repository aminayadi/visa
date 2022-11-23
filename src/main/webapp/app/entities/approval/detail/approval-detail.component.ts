import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApproval } from '../approval.model';

@Component({
  selector: 'jhi-approval-detail',
  templateUrl: './approval-detail.component.html',
})
export class ApprovalDetailComponent implements OnInit {
  approval: IApproval | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ approval }) => {
      this.approval = approval;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
