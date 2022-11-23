import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonFormService, PersonFormGroup } from './person-form.service';
import { IPerson } from '../person.model';
import { PersonService } from '../service/person.service';
import { IRequest } from 'app/entities/request/request.model';
import { RequestService } from 'app/entities/request/service/request.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  person: IPerson | null = null;

  requestsSharedCollection: IRequest[] = [];

  editForm: PersonFormGroup = this.personFormService.createPersonFormGroup();

  constructor(
    protected personService: PersonService,
    protected personFormService: PersonFormService,
    protected requestService: RequestService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRequest = (o1: IRequest | null, o2: IRequest | null): boolean => this.requestService.compareRequest(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.person = person;
      if (person) {
        this.updateForm(person);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.personFormService.getPerson(this.editForm);
    if (person.id !== null) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  protected updateForm(person: IPerson): void {
    this.person = person;
    this.personFormService.resetForm(this.editForm, person);

    this.requestsSharedCollection = this.requestService.addRequestToCollectionIfMissing<IRequest>(
      this.requestsSharedCollection,
      ...(person.requests ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestService
      .query()
      .pipe(map((res: HttpResponse<IRequest[]>) => res.body ?? []))
      .pipe(
        map((requests: IRequest[]) =>
          this.requestService.addRequestToCollectionIfMissing<IRequest>(requests, ...(this.person?.requests ?? []))
        )
      )
      .subscribe((requests: IRequest[]) => (this.requestsSharedCollection = requests));
  }
}
