package com.mdweb.evisa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mdweb.evisa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Approval.class);
        Approval approval1 = new Approval();
        approval1.setId("id1");
        Approval approval2 = new Approval();
        approval2.setId(approval1.getId());
        assertThat(approval1).isEqualTo(approval2);
        approval2.setId("id2");
        assertThat(approval1).isNotEqualTo(approval2);
        approval1.setId(null);
        assertThat(approval1).isNotEqualTo(approval2);
    }
}
