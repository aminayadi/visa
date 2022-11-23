import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerson, NewPerson } from '../person.model';

export type PartialUpdatePerson = Partial<IPerson> & Pick<IPerson, 'id'>;

type RestOf<T extends IPerson | NewPerson> = Omit<T, 'birthday' | 'expirationdate'> & {
  birthday?: string | null;
  expirationdate?: string | null;
};

export type RestPerson = RestOf<IPerson>;

export type NewRestPerson = RestOf<NewPerson>;

export type PartialUpdateRestPerson = RestOf<PartialUpdatePerson>;

export type EntityResponseType = HttpResponse<IPerson>;
export type EntityArrayResponseType = HttpResponse<IPerson[]>;

@Injectable({ providedIn: 'root' })
export class PersonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/people');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(person: NewPerson): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(person);
    return this.http
      .post<RestPerson>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(person: IPerson): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(person);
    return this.http
      .put<RestPerson>(`${this.resourceUrl}/${this.getPersonIdentifier(person)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(person: PartialUpdatePerson): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(person);
    return this.http
      .patch<RestPerson>(`${this.resourceUrl}/${this.getPersonIdentifier(person)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPerson[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonIdentifier(person: Pick<IPerson, 'id'>): string {
    return person.id;
  }

  comparePerson(o1: Pick<IPerson, 'id'> | null, o2: Pick<IPerson, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonIdentifier(o1) === this.getPersonIdentifier(o2) : o1 === o2;
  }

  addPersonToCollectionIfMissing<Type extends Pick<IPerson, 'id'>>(
    personCollection: Type[],
    ...peopleToCheck: (Type | null | undefined)[]
  ): Type[] {
    const people: Type[] = peopleToCheck.filter(isPresent);
    if (people.length > 0) {
      const personCollectionIdentifiers = personCollection.map(personItem => this.getPersonIdentifier(personItem)!);
      const peopleToAdd = people.filter(personItem => {
        const personIdentifier = this.getPersonIdentifier(personItem);
        if (personCollectionIdentifiers.includes(personIdentifier)) {
          return false;
        }
        personCollectionIdentifiers.push(personIdentifier);
        return true;
      });
      return [...peopleToAdd, ...personCollection];
    }
    return personCollection;
  }

  protected convertDateFromClient<T extends IPerson | NewPerson | PartialUpdatePerson>(person: T): RestOf<T> {
    return {
      ...person,
      birthday: person.birthday?.toJSON() ?? null,
      expirationdate: person.expirationdate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPerson: RestPerson): IPerson {
    return {
      ...restPerson,
      birthday: restPerson.birthday ? dayjs(restPerson.birthday) : undefined,
      expirationdate: restPerson.expirationdate ? dayjs(restPerson.expirationdate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPerson>): HttpResponse<IPerson> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPerson[]>): HttpResponse<IPerson[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
