package com.mdweb.evisa.web.rest;

import com.mdweb.evisa.repository.ApprovalRepository;
import com.mdweb.evisa.service.ApprovalService;
import com.mdweb.evisa.service.dto.ApprovalDTO;
import com.mdweb.evisa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mdweb.evisa.domain.Approval}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalResource {

    private final Logger log = LoggerFactory.getLogger(ApprovalResource.class);

    private static final String ENTITY_NAME = "approval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApprovalService approvalService;

    private final ApprovalRepository approvalRepository;

    public ApprovalResource(ApprovalService approvalService, ApprovalRepository approvalRepository) {
        this.approvalService = approvalService;
        this.approvalRepository = approvalRepository;
    }

    /**
     * {@code POST  /approvals} : Create a new approval.
     *
     * @param approvalDTO the approvalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approvalDTO, or with status {@code 400 (Bad Request)} if the approval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approvals")
    public ResponseEntity<ApprovalDTO> createApproval(@Valid @RequestBody ApprovalDTO approvalDTO) throws URISyntaxException {
        log.debug("REST request to save Approval : {}", approvalDTO);
        if (approvalDTO.getId() != null) {
            throw new BadRequestAlertException("A new approval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApprovalDTO result = approvalService.save(approvalDTO);
        return ResponseEntity
            .created(new URI("/api/approvals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /approvals/:id} : Updates an existing approval.
     *
     * @param id the id of the approvalDTO to save.
     * @param approvalDTO the approvalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalDTO,
     * or with status {@code 400 (Bad Request)} if the approvalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approvalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approvals/{id}")
    public ResponseEntity<ApprovalDTO> updateApproval(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ApprovalDTO approvalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Approval : {}, {}", id, approvalDTO);
        if (approvalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApprovalDTO result = approvalService.update(approvalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approvalDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /approvals/:id} : Partial updates given fields of an existing approval, field will ignore if it is null
     *
     * @param id the id of the approvalDTO to save.
     * @param approvalDTO the approvalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalDTO,
     * or with status {@code 400 (Bad Request)} if the approvalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the approvalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the approvalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approvals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApprovalDTO> partialUpdateApproval(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ApprovalDTO approvalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Approval partially : {}, {}", id, approvalDTO);
        if (approvalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApprovalDTO> result = approvalService.partialUpdate(approvalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, approvalDTO.getId())
        );
    }

    /**
     * {@code GET  /approvals} : get all the approvals.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvals in body.
     */
    @GetMapping("/approvals")
    public ResponseEntity<List<ApprovalDTO>> getAllApprovals(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Approvals");
        Page<ApprovalDTO> page;
        if (eagerload) {
            page = approvalService.findAllWithEagerRelationships(pageable);
        } else {
            page = approvalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approvals/:id} : get the "id" approval.
     *
     * @param id the id of the approvalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approvalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approvals/{id}")
    public ResponseEntity<ApprovalDTO> getApproval(@PathVariable String id) {
        log.debug("REST request to get Approval : {}", id);
        Optional<ApprovalDTO> approvalDTO = approvalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approvalDTO);
    }

    /**
     * {@code DELETE  /approvals/:id} : delete the "id" approval.
     *
     * @param id the id of the approvalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approvals/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable String id) {
        log.debug("REST request to delete Approval : {}", id);
        approvalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
