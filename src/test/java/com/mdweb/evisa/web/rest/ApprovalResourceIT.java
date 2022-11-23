package com.mdweb.evisa.web.rest;

import static com.mdweb.evisa.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mdweb.evisa.IntegrationTest;
import com.mdweb.evisa.domain.Approval;
import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.domain.User;
import com.mdweb.evisa.repository.ApprovalRepository;
import com.mdweb.evisa.service.ApprovalService;
import com.mdweb.evisa.service.dto.ApprovalDTO;
import com.mdweb.evisa.service.mapper.ApprovalMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ApprovalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApprovalResourceIT {

    private static final String DEFAULT_DECISSION = "AAAAAAAAAA";
    private static final String UPDATED_DECISSION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ApprovalRepository approvalRepository;

    @Mock
    private ApprovalRepository approvalRepositoryMock;

    @Autowired
    private ApprovalMapper approvalMapper;

    @Mock
    private ApprovalService approvalServiceMock;

    @Autowired
    private MockMvc restApprovalMockMvc;

    private Approval approval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createEntity() {
        Approval approval = new Approval()
            .decission(DEFAULT_DECISSION)
            .remarks(DEFAULT_REMARKS)
            .createddate(DEFAULT_CREATEDDATE)
            .updatedate(DEFAULT_UPDATEDATE);
        // Add required entity
        Request request;
        request = RequestResourceIT.createEntity();
        request.setId("fixed-id-for-tests");
        approval.getRequests().add(request);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        approval.getUsers().add(user);
        return approval;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createUpdatedEntity() {
        Approval approval = new Approval()
            .decission(UPDATED_DECISSION)
            .remarks(UPDATED_REMARKS)
            .createddate(UPDATED_CREATEDDATE)
            .updatedate(UPDATED_UPDATEDATE);
        // Add required entity
        Request request;
        request = RequestResourceIT.createUpdatedEntity();
        request.setId("fixed-id-for-tests");
        approval.getRequests().add(request);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        approval.getUsers().add(user);
        return approval;
    }

    @BeforeEach
    public void initTest() {
        approvalRepository.deleteAll();
        approval = createEntity();
    }

    @Test
    void createApproval() throws Exception {
        int databaseSizeBeforeCreate = approvalRepository.findAll().size();
        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isCreated());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate + 1);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getDecission()).isEqualTo(DEFAULT_DECISSION);
        assertThat(testApproval.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testApproval.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testApproval.getUpdatedate()).isEqualTo(DEFAULT_UPDATEDATE);
    }

    @Test
    void createApprovalWithExistingId() throws Exception {
        // Create the Approval with an existing ID
        approval.setId("existing_id");
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        int databaseSizeBeforeCreate = approvalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDecissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = approvalRepository.findAll().size();
        // set the field null
        approval.setDecission(null);

        // Create the Approval, which fails.
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreateddateIsRequired() throws Exception {
        int databaseSizeBeforeTest = approvalRepository.findAll().size();
        // set the field null
        approval.setCreateddate(null);

        // Create the Approval, which fails.
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedateIsRequired() throws Exception {
        int databaseSizeBeforeTest = approvalRepository.findAll().size();
        // set the field null
        approval.setUpdatedate(null);

        // Create the Approval, which fails.
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllApprovals() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        // Get all the approvalList
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approval.getId())))
            .andExpect(jsonPath("$.[*].decission").value(hasItem(DEFAULT_DECISSION)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))))
            .andExpect(jsonPath("$.[*].updatedate").value(hasItem(sameInstant(DEFAULT_UPDATEDATE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApprovalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(approvalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApprovalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(approvalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApprovalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(approvalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApprovalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(approvalRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getApproval() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        // Get the approval
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL_ID, approval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approval.getId()))
            .andExpect(jsonPath("$.decission").value(DEFAULT_DECISSION))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.createddate").value(sameInstant(DEFAULT_CREATEDDATE)))
            .andExpect(jsonPath("$.updatedate").value(sameInstant(DEFAULT_UPDATEDATE)));
    }

    @Test
    void getNonExistingApproval() throws Exception {
        // Get the approval
        restApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingApproval() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval
        Approval updatedApproval = approvalRepository.findById(approval.getId()).get();
        updatedApproval
            .decission(UPDATED_DECISSION)
            .remarks(UPDATED_REMARKS)
            .createddate(UPDATED_CREATEDDATE)
            .updatedate(UPDATED_UPDATEDATE);
        ApprovalDTO approvalDTO = approvalMapper.toDto(updatedApproval);

        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getDecission()).isEqualTo(UPDATED_DECISSION);
        assertThat(testApproval.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testApproval.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testApproval.getUpdatedate()).isEqualTo(UPDATED_UPDATEDATE);
    }

    @Test
    void putNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval.remarks(UPDATED_REMARKS).createddate(UPDATED_CREATEDDATE).updatedate(UPDATED_UPDATEDATE);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getDecission()).isEqualTo(DEFAULT_DECISSION);
        assertThat(testApproval.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testApproval.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testApproval.getUpdatedate()).isEqualTo(UPDATED_UPDATEDATE);
    }

    @Test
    void fullUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval
            .decission(UPDATED_DECISSION)
            .remarks(UPDATED_REMARKS)
            .createddate(UPDATED_CREATEDDATE)
            .updatedate(UPDATED_UPDATEDATE);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getDecission()).isEqualTo(UPDATED_DECISSION);
        assertThat(testApproval.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testApproval.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testApproval.getUpdatedate()).isEqualTo(UPDATED_UPDATEDATE);
    }

    @Test
    void patchNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(UUID.randomUUID().toString());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteApproval() throws Exception {
        // Initialize the database
        approvalRepository.save(approval);

        int databaseSizeBeforeDelete = approvalRepository.findAll().size();

        // Delete the approval
        restApprovalMockMvc
            .perform(delete(ENTITY_API_URL_ID, approval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
