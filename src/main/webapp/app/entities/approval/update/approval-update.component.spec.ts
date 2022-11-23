import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApprovalFormService } from './approval-form.service';
import { ApprovalService } from '../service/approval.service';
import { IApproval } from '../approval.model';
import { IRequest } from 'app/entities/request/request.model';
import { RequestService } from 'app/entities/request/service/request.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { ApprovalUpdateComponent } from './approval-update.component';

describe('Approval Management Update Component', () => {
  let comp: ApprovalUpdateComponent;
  let fixture: ComponentFixture<ApprovalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let approvalFormService: ApprovalFormService;
  let approvalService: ApprovalService;
  let requestService: RequestService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApprovalUpdateComponent],
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
      .overrideTemplate(ApprovalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApprovalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    approvalFormService = TestBed.inject(ApprovalFormService);
    approvalService = TestBed.inject(ApprovalService);
    requestService = TestBed.inject(RequestService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Request query and add missing value', () => {
      const approval: IApproval = { id: 'CBA' };
      const requests: IRequest[] = [{ id: 'fcd2103c-9bd8-4849-b4bd-fcf3fcbc7694' }];
      approval.requests = requests;

      const requestCollection: IRequest[] = [{ id: 'ee9769af-d20e-410d-9d12-cd18fc9f6f3d' }];
      jest.spyOn(requestService, 'query').mockReturnValue(of(new HttpResponse({ body: requestCollection })));
      const additionalRequests = [...requests];
      const expectedCollection: IRequest[] = [...additionalRequests, ...requestCollection];
      jest.spyOn(requestService, 'addRequestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ approval });
      comp.ngOnInit();

      expect(requestService.query).toHaveBeenCalled();
      expect(requestService.addRequestToCollectionIfMissing).toHaveBeenCalledWith(
        requestCollection,
        ...additionalRequests.map(expect.objectContaining)
      );
      expect(comp.requestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const approval: IApproval = { id: 'CBA' };
      const users: IUser[] = [{ id: '3285bd57-67cb-42f8-9a9f-548e0c34cb61' }];
      approval.users = users;

      const userCollection: IUser[] = [{ id: '37c16cfd-6252-4f45-ab7d-1db21f3aa5d2' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [...users];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ approval });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const approval: IApproval = { id: 'CBA' };
      const request: IRequest = { id: 'af2548d8-f6fc-44c9-8bd7-de4dcf099309' };
      approval.requests = [request];
      const user: IUser = { id: '10647111-a725-4a78-860d-eadc0591e85a' };
      approval.users = [user];

      activatedRoute.data = of({ approval });
      comp.ngOnInit();

      expect(comp.requestsSharedCollection).toContain(request);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.approval).toEqual(approval);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApproval>>();
      const approval = { id: 'ABC' };
      jest.spyOn(approvalFormService, 'getApproval').mockReturnValue(approval);
      jest.spyOn(approvalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approval });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: approval }));
      saveSubject.complete();

      // THEN
      expect(approvalFormService.getApproval).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(approvalService.update).toHaveBeenCalledWith(expect.objectContaining(approval));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApproval>>();
      const approval = { id: 'ABC' };
      jest.spyOn(approvalFormService, 'getApproval').mockReturnValue({ id: null });
      jest.spyOn(approvalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approval: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: approval }));
      saveSubject.complete();

      // THEN
      expect(approvalFormService.getApproval).toHaveBeenCalled();
      expect(approvalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApproval>>();
      const approval = { id: 'ABC' };
      jest.spyOn(approvalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approval });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(approvalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRequest', () => {
      it('Should forward to requestService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(requestService, 'compareRequest');
        comp.compareRequest(entity, entity2);
        expect(requestService.compareRequest).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
