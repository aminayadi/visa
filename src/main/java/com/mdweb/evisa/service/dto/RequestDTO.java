package com.mdweb.evisa.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mdweb.evisa.domain.Request} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestDTO implements Serializable {

    private String id;

    @NotNull
    private String requesttype;

    @NotNull
    private ZonedDateTime createddate;

    @NotNull
    private ZonedDateTime updatedate;

    @NotNull
    private String status;

    private UserDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id='" + getId() + "'" +
            ", requesttype='" + getRequesttype() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", updatedate='" + getUpdatedate() + "'" +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
