import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequestFormService } from './request-form.service';
import { RequestService } from '../service/request.service';
import { IRequest } from '../request.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { RequestUpdateComponent } from './request-update.component';

describe('Request Management Update Component', () => {
  let comp: RequestUpdateComponent;
  let fixture: ComponentFixture<RequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestFormService: RequestFormService;
  let requestService: RequestService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestFormService = TestBed.inject(RequestFormService);
    requestService = TestBed.inject(RequestService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const request: IRequest = { id: 'CBA' };
      const user: IUser = { id: 'ff63a142-e861-46ed-b99e-417653b79f1e' };
      request.user = user;

      const userCollection: IUser[] = [{ id: '9c51b215-721f-405d-afa5-e254603e76fc' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ request });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const request: IRequest = { id: 'CBA' };
      const user: IUser = { id: '084b78fd-c74b-4ea3-aa54-55a43bf5ffad' };
      request.user = user;

      activatedRoute.data = of({ request });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.request).toEqual(request);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequest>>();
      const request = { id: 'ABC' };
      jest.spyOn(requestFormService, 'getRequest').mockReturnValue(request);
      jest.spyOn(requestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ request });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: request }));
      saveSubject.complete();

      // THEN
      expect(requestFormService.getRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestService.update).toHaveBeenCalledWith(expect.objectContaining(request));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequest>>();
      const request = { id: 'ABC' };
      jest.spyOn(requestFormService, 'getRequest').mockReturnValue({ id: null });
      jest.spyOn(requestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ request: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: request }));
      saveSubject.complete();

      // THEN
      expect(requestFormService.getRequest).toHaveBeenCalled();
      expect(requestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequest>>();
      const request = { id: 'ABC' };
      jest.spyOn(requestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ request });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
