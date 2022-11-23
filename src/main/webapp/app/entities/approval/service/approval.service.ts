import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApproval, NewApproval } from '../approval.model';

export type PartialUpdateApproval = Partial<IApproval> & Pick<IApproval, 'id'>;

type RestOf<T extends IApproval | NewApproval> = Omit<T, 'createddate' | 'updatedate'> & {
  createddate?: string | null;
  updatedate?: string | null;
};

export type RestApproval = RestOf<IApproval>;

export type NewRestApproval = RestOf<NewApproval>;

export type PartialUpdateRestApproval = RestOf<PartialUpdateApproval>;

export type EntityResponseType = HttpResponse<IApproval>;
export type EntityArrayResponseType = HttpResponse<IApproval[]>;

@Injectable({ providedIn: 'root' })
export class ApprovalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/approvals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(approval: NewApproval): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approval);
    return this.http
      .post<RestApproval>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(approval: IApproval): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approval);
    return this.http
      .put<RestApproval>(`${this.resourceUrl}/${this.getApprovalIdentifier(approval)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(approval: PartialUpdateApproval): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approval);
    return this.http
      .patch<RestApproval>(`${this.resourceUrl}/${this.getApprovalIdentifier(approval)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestApproval>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestApproval[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApprovalIdentifier(approval: Pick<IApproval, 'id'>): string {
    return approval.id;
  }

  compareApproval(o1: Pick<IApproval, 'id'> | null, o2: Pick<IApproval, 'id'> | null): boolean {
    return o1 && o2 ? this.getApprovalIdentifier(o1) === this.getApprovalIdentifier(o2) : o1 === o2;
  }

  addApprovalToCollectionIfMissing<Type extends Pick<IApproval, 'id'>>(
    approvalCollection: Type[],
    ...approvalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const approvals: Type[] = approvalsToCheck.filter(isPresent);
    if (approvals.length > 0) {
      const approvalCollectionIdentifiers = approvalCollection.map(approvalItem => this.getApprovalIdentifier(approvalItem)!);
      const approvalsToAdd = approvals.filter(approvalItem => {
        const approvalIdentifier = this.getApprovalIdentifier(approvalItem);
        if (approvalCollectionIdentifiers.includes(approvalIdentifier)) {
          return false;
        }
        approvalCollectionIdentifiers.push(approvalIdentifier);
        return true;
      });
      return [...approvalsToAdd, ...approvalCollection];
    }
    return approvalCollection;
  }

  protected convertDateFromClient<T extends IApproval | NewApproval | PartialUpdateApproval>(approval: T): RestOf<T> {
    return {
      ...approval,
      createddate: approval.createddate?.toJSON() ?? null,
      updatedate: approval.updatedate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restApproval: RestApproval): IApproval {
    return {
      ...restApproval,
      createddate: restApproval.createddate ? dayjs(restApproval.createddate) : undefined,
      updatedate: restApproval.updatedate ? dayjs(restApproval.updatedate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestApproval>): HttpResponse<IApproval> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestApproval[]>): HttpResponse<IApproval[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
