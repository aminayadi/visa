import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ApprovalFormService, ApprovalFormGroup } from './approval-form.service';
import { IApproval } from '../approval.model';
import { ApprovalService } from '../service/approval.service';
import { IRequest } from 'app/entities/request/request.model';
import { RequestService } from 'app/entities/request/service/request.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-approval-update',
  templateUrl: './approval-update.component.html',
})
export class ApprovalUpdateComponent implements OnInit {
  isSaving = false;
  approval: IApproval | null = null;

  requestsSharedCollection: IRequest[] = [];
  usersSharedCollection: IUser[] = [];

  editForm: ApprovalFormGroup = this.approvalFormService.createApprovalFormGroup();

  constructor(
    protected approvalService: ApprovalService,
    protected approvalFormService: ApprovalFormService,
    protected requestService: RequestService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRequest = (o1: IRequest | null, o2: IRequest | null): boolean => this.requestService.compareRequest(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ approval }) => {
      this.approval = approval;
      if (approval) {
        this.updateForm(approval);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const approval = this.approvalFormService.getApproval(this.editForm);
    if (approval.id !== null) {
      this.subscribeToSaveResponse(this.approvalService.update(approval));
    } else {
      this.subscribeToSaveResponse(this.approvalService.create(approval));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApproval>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(approval: IApproval): void {
    this.approval = approval;
    this.approvalFormService.resetForm(this.editForm, approval);

    this.requestsSharedCollection = this.requestService.addRequestToCollectionIfMissing<IRequest>(
      this.requestsSharedCollection,
      ...(approval.requests ?? [])
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      ...(approval.users ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestService
      .query()
      .pipe(map((res: HttpResponse<IRequest[]>) => res.body ?? []))
      .pipe(
        map((requests: IRequest[]) =>
          this.requestService.addRequestToCollectionIfMissing<IRequest>(requests, ...(this.approval?.requests ?? []))
        )
      )
      .subscribe((requests: IRequest[]) => (this.requestsSharedCollection = requests));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, ...(this.approval?.users ?? []))))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
