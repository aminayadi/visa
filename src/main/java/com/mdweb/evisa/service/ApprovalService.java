package com.mdweb.evisa.service;

import com.mdweb.evisa.domain.Approval;
import com.mdweb.evisa.repository.ApprovalRepository;
import com.mdweb.evisa.service.dto.ApprovalDTO;
import com.mdweb.evisa.service.mapper.ApprovalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Approval}.
 */
@Service
public class ApprovalService {

    private final Logger log = LoggerFactory.getLogger(ApprovalService.class);

    private final ApprovalRepository approvalRepository;

    private final ApprovalMapper approvalMapper;

    public ApprovalService(ApprovalRepository approvalRepository, ApprovalMapper approvalMapper) {
        this.approvalRepository = approvalRepository;
        this.approvalMapper = approvalMapper;
    }

    /**
     * Save a approval.
     *
     * @param approvalDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalDTO save(ApprovalDTO approvalDTO) {
        log.debug("Request to save Approval : {}", approvalDTO);
        Approval approval = approvalMapper.toEntity(approvalDTO);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    /**
     * Update a approval.
     *
     * @param approvalDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalDTO update(ApprovalDTO approvalDTO) {
        log.debug("Request to update Approval : {}", approvalDTO);
        Approval approval = approvalMapper.toEntity(approvalDTO);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    /**
     * Partially update a approval.
     *
     * @param approvalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApprovalDTO> partialUpdate(ApprovalDTO approvalDTO) {
        log.debug("Request to partially update Approval : {}", approvalDTO);

        return approvalRepository
            .findById(approvalDTO.getId())
            .map(existingApproval -> {
                approvalMapper.partialUpdate(existingApproval, approvalDTO);

                return existingApproval;
            })
            .map(approvalRepository::save)
            .map(approvalMapper::toDto);
    }

    /**
     * Get all the approvals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ApprovalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Approvals");
        return approvalRepository.findAll(pageable).map(approvalMapper::toDto);
    }

    /**
     * Get all the approvals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApprovalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return approvalRepository.findAllWithEagerRelationships(pageable).map(approvalMapper::toDto);
    }

    /**
     * Get one approval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ApprovalDTO> findOne(String id) {
        log.debug("Request to get Approval : {}", id);
        return approvalRepository.findOneWithEagerRelationships(id).map(approvalMapper::toDto);
    }

    /**
     * Delete the approval by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Approval : {}", id);
        approvalRepository.deleteById(id);
    }
}
