package com.mdweb.evisa.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mdweb.evisa.domain.Approval} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalDTO implements Serializable {

    private String id;

    @NotNull
    private String decission;

    private String remarks;

    @NotNull
    private ZonedDateTime createddate;

    @NotNull
    private ZonedDateTime updatedate;

    private Set<RequestDTO> requests = new HashSet<>();

    private Set<UserDTO> users = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDecission() {
        return decission;
    }

    public void setDecission(String decission) {
        this.decission = decission;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ZonedDateTime getCreateddate() {
        return createddate;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
    }

    public Set<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(Set<RequestDTO> requests) {
        this.requests = requests;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalDTO)) {
            return false;
        }

        ApprovalDTO approvalDTO = (ApprovalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalDTO{" +
            "id='" + getId() + "'" +
            ", decission='" + getDecission() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", updatedate='" + getUpdatedate() + "'" +
            ", requests=" + getRequests() +
            ", users=" + getUsers() +
            "}";
    }
}
