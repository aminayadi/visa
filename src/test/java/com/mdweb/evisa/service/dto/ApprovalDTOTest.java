package com.mdweb.evisa.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mdweb.evisa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalDTO.class);
        ApprovalDTO approvalDTO1 = new ApprovalDTO();
        approvalDTO1.setId("id1");
        ApprovalDTO approvalDTO2 = new ApprovalDTO();
        assertThat(approvalDTO1).isNotEqualTo(approvalDTO2);
        approvalDTO2.setId(approvalDTO1.getId());
        assertThat(approvalDTO1).isEqualTo(approvalDTO2);
        approvalDTO2.setId("id2");
        assertThat(approvalDTO1).isNotEqualTo(approvalDTO2);
        approvalDTO1.setId(null);
        assertThat(approvalDTO1).isNotEqualTo(approvalDTO2);
    }
}
