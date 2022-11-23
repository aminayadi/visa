import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApproval } from '../approval.model';
import { ApprovalService } from '../service/approval.service';

@Injectable({ providedIn: 'root' })
export class ApprovalRoutingResolveService implements Resolve<IApproval | null> {
  constructor(protected service: ApprovalService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApproval | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((approval: HttpResponse<IApproval>) => {
          if (approval.body) {
            return of(approval.body);
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
