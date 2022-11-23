import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApproval } from '../approval.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../approval.test-samples';

import { ApprovalService, RestApproval } from './approval.service';

const requireRestSample: RestApproval = {
  ...sampleWithRequiredData,
  createddate: sampleWithRequiredData.createddate?.toJSON(),
  updatedate: sampleWithRequiredData.updatedate?.toJSON(),
};

describe('Approval Service', () => {
  let service: ApprovalService;
  let httpMock: HttpTestingController;
  let expectedResult: IApproval | IApproval[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApprovalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Approval', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const approval = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(approval).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Approval', () => {
      const approval = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(approval).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Approval', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Approval', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Approval', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApprovalToCollectionIfMissing', () => {
      it('should add a Approval to an empty array', () => {
        const approval: IApproval = sampleWithRequiredData;
        expectedResult = service.addApprovalToCollectionIfMissing([], approval);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(approval);
      });

      it('should not add a Approval to an array that contains it', () => {
        const approval: IApproval = sampleWithRequiredData;
        const approvalCollection: IApproval[] = [
          {
            ...approval,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApprovalToCollectionIfMissing(approvalCollection, approval);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Approval to an array that doesn't contain it", () => {
        const approval: IApproval = sampleWithRequiredData;
        const approvalCollection: IApproval[] = [sampleWithPartialData];
        expectedResult = service.addApprovalToCollectionIfMissing(approvalCollection, approval);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(approval);
      });

      it('should add only unique Approval to an array', () => {
        const approvalArray: IApproval[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const approvalCollection: IApproval[] = [sampleWithRequiredData];
        expectedResult = service.addApprovalToCollectionIfMissing(approvalCollection, ...approvalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const approval: IApproval = sampleWithRequiredData;
        const approval2: IApproval = sampleWithPartialData;
        expectedResult = service.addApprovalToCollectionIfMissing([], approval, approval2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(approval);
        expect(expectedResult).toContain(approval2);
      });

      it('should accept null and undefined values', () => {
        const approval: IApproval = sampleWithRequiredData;
        expectedResult = service.addApprovalToCollectionIfMissing([], null, approval, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(approval);
      });

      it('should return initial array if no Approval is added', () => {
        const approvalCollection: IApproval[] = [sampleWithRequiredData];
        expectedResult = service.addApprovalToCollectionIfMissing(approvalCollection, undefined, null);
        expect(expectedResult).toEqual(approvalCollection);
      });
    });

    describe('compareApproval', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApproval(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareApproval(entity1, entity2);
        const compareResult2 = service.compareApproval(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareApproval(entity1, entity2);
        const compareResult2 = service.compareApproval(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareApproval(entity1, entity2);
        const compareResult2 = service.compareApproval(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
