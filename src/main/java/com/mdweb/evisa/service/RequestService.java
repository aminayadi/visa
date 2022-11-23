package com.mdweb.evisa.service;

import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.repository.RequestRepository;
import com.mdweb.evisa.service.dto.RequestDTO;
import com.mdweb.evisa.service.mapper.RequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    public RequestService(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    /**
     * Save a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    /**
     * Update a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDTO update(RequestDTO requestDTO) {
        log.debug("Request to update Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    /**
     * Partially update a request.
     *
     * @param requestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(existingRequest -> {
                requestMapper.partialUpdate(existingRequest, requestDTO);

                return existingRequest;
            })
            .map(requestRepository::save)
            .map(requestMapper::toDto);
    }

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable).map(requestMapper::toDto);
    }

    /**
     * Get all the requests with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return requestRepository.findAllWithEagerRelationships(pageable).map(requestMapper::toDto);
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RequestDTO> findOne(String id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOneWithEagerRelationships(id).map(requestMapper::toDto);
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }
}
