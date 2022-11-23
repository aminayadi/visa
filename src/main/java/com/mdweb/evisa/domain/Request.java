package com.mdweb.evisa.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Request.
 */
@Document(collection = "request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("requesttype")
    private String requesttype;

    @NotNull
    @Field("createddate")
    private ZonedDateTime createddate;

    @NotNull
    @Field("updatedate")
    private ZonedDateTime updatedate;

    @NotNull
    @Field("status")
    private String status;

    @DBRef
    @Field("user")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Request id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequesttype() {
        return this.requesttype;
    }

    public Request requesttype(String requesttype) {
        this.setRequesttype(requesttype);
        return this;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public ZonedDateTime getCreateddate() {
        return this.createddate;
    }

    public Request createddate(ZonedDateTime createddate) {
        this.setCreateddate(createddate);
        return this;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getUpdatedate() {
        return this.updatedate;
    }

    public Request updatedate(ZonedDateTime updatedate) {
        this.setUpdatedate(updatedate);
        return this;
    }

    public void setUpdatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
    }

    public String getStatus() {
        return this.status;
    }

    public Request status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Request user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", requesttype='" + getRequesttype() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", updatedate='" + getUpdatedate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
