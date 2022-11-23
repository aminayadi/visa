package com.mdweb.evisa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Approval.
 */
@Document(collection = "approval")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("decission")
    private String decission;

    @Field("remarks")
    private String remarks;

    @NotNull
    @Field("createddate")
    private ZonedDateTime createddate;

    @NotNull
    @Field("updatedate")
    private ZonedDateTime updatedate;

    @DBRef
    @Field("requests")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    @DBRef
    @Field("users")
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Approval id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDecission() {
        return this.decission;
    }

    public Approval decission(String decission) {
        this.setDecission(decission);
        return this;
    }

    public void setDecission(String decission) {
        this.decission = decission;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Approval remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ZonedDateTime getCreateddate() {
        return this.createddate;
    }

    public Approval createddate(ZonedDateTime createddate) {
        this.setCreateddate(createddate);
        return this;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getUpdatedate() {
        return this.updatedate;
    }

    public Approval updatedate(ZonedDateTime updatedate) {
        this.setUpdatedate(updatedate);
        return this;
    }

    public void setUpdatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Approval requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public Approval addRequest(Request request) {
        this.requests.add(request);
        return this;
    }

    public Approval removeRequest(Request request) {
        this.requests.remove(request);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Approval users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Approval addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Approval removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Approval)) {
            return false;
        }
        return id != null && id.equals(((Approval) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approval{" +
            "id=" + getId() +
            ", decission='" + getDecission() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", updatedate='" + getUpdatedate() + "'" +
            "}";
    }
}
