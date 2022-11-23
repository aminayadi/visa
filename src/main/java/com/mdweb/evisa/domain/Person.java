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
 * A Person.
 */
@Document(collection = "person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("diplomaticmission")
    private String diplomaticmission;

    @NotNull
    @Field("nationality")
    private String nationality;

    @NotNull
    @Field("requestparty")
    private String requestparty;

    @NotNull
    @Field("identity")
    private String identity;

    @NotNull
    @Field("birthday")
    private ZonedDateTime birthday;

    @NotNull
    @Field("passporttype")
    private String passporttype;

    @NotNull
    @Field("passportnumber")
    private String passportnumber;

    @NotNull
    @Field("expirationdate")
    private ZonedDateTime expirationdate;

    @NotNull
    @Field("visatype")
    private String visatype;

    @NotNull
    @Field("reason")
    private String reason;

    @NotNull
    @Field("otherreason")
    private String otherreason;

    @NotNull
    @Field("guest")
    private String guest;

    @NotNull
    @Field("adress")
    private String adress;

    @NotNull
    @Field("administation")
    private String administation;

    @NotNull
    @Field("automaticcheck")
    private String automaticcheck;

    @NotNull
    @Field("manualcheck")
    private String manualcheck;

    @NotNull
    @Field("entries")
    private String entries;

    @NotNull
    @Field("exits")
    private String exits;

    @NotNull
    @Field("lastmove")
    private String lastmove;

    @NotNull
    @Field("summary")
    private String summary;

    @DBRef
    @Field("requests")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Person id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiplomaticmission() {
        return this.diplomaticmission;
    }

    public Person diplomaticmission(String diplomaticmission) {
        this.setDiplomaticmission(diplomaticmission);
        return this;
    }

    public void setDiplomaticmission(String diplomaticmission) {
        this.diplomaticmission = diplomaticmission;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Person nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRequestparty() {
        return this.requestparty;
    }

    public Person requestparty(String requestparty) {
        this.setRequestparty(requestparty);
        return this;
    }

    public void setRequestparty(String requestparty) {
        this.requestparty = requestparty;
    }

    public String getIdentity() {
        return this.identity;
    }

    public Person identity(String identity) {
        this.setIdentity(identity);
        return this;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public ZonedDateTime getBirthday() {
        return this.birthday;
    }

    public Person birthday(ZonedDateTime birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPassporttype() {
        return this.passporttype;
    }

    public Person passporttype(String passporttype) {
        this.setPassporttype(passporttype);
        return this;
    }

    public void setPassporttype(String passporttype) {
        this.passporttype = passporttype;
    }

    public String getPassportnumber() {
        return this.passportnumber;
    }

    public Person passportnumber(String passportnumber) {
        this.setPassportnumber(passportnumber);
        return this;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public ZonedDateTime getExpirationdate() {
        return this.expirationdate;
    }

    public Person expirationdate(ZonedDateTime expirationdate) {
        this.setExpirationdate(expirationdate);
        return this;
    }

    public void setExpirationdate(ZonedDateTime expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getVisatype() {
        return this.visatype;
    }

    public Person visatype(String visatype) {
        this.setVisatype(visatype);
        return this;
    }

    public void setVisatype(String visatype) {
        this.visatype = visatype;
    }

    public String getReason() {
        return this.reason;
    }

    public Person reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOtherreason() {
        return this.otherreason;
    }

    public Person otherreason(String otherreason) {
        this.setOtherreason(otherreason);
        return this;
    }

    public void setOtherreason(String otherreason) {
        this.otherreason = otherreason;
    }

    public String getGuest() {
        return this.guest;
    }

    public Person guest(String guest) {
        this.setGuest(guest);
        return this;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getAdress() {
        return this.adress;
    }

    public Person adress(String adress) {
        this.setAdress(adress);
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdministation() {
        return this.administation;
    }

    public Person administation(String administation) {
        this.setAdministation(administation);
        return this;
    }

    public void setAdministation(String administation) {
        this.administation = administation;
    }

    public String getAutomaticcheck() {
        return this.automaticcheck;
    }

    public Person automaticcheck(String automaticcheck) {
        this.setAutomaticcheck(automaticcheck);
        return this;
    }

    public void setAutomaticcheck(String automaticcheck) {
        this.automaticcheck = automaticcheck;
    }

    public String getManualcheck() {
        return this.manualcheck;
    }

    public Person manualcheck(String manualcheck) {
        this.setManualcheck(manualcheck);
        return this;
    }

    public void setManualcheck(String manualcheck) {
        this.manualcheck = manualcheck;
    }

    public String getEntries() {
        return this.entries;
    }

    public Person entries(String entries) {
        this.setEntries(entries);
        return this;
    }

    public void setEntries(String entries) {
        this.entries = entries;
    }

    public String getExits() {
        return this.exits;
    }

    public Person exits(String exits) {
        this.setExits(exits);
        return this;
    }

    public void setExits(String exits) {
        this.exits = exits;
    }

    public String getLastmove() {
        return this.lastmove;
    }

    public Person lastmove(String lastmove) {
        this.setLastmove(lastmove);
        return this;
    }

    public void setLastmove(String lastmove) {
        this.lastmove = lastmove;
    }

    public String getSummary() {
        return this.summary;
    }

    public Person summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Person requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public Person addRequest(Request request) {
        this.requests.add(request);
        return this;
    }

    public Person removeRequest(Request request) {
        this.requests.remove(request);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", diplomaticmission='" + getDiplomaticmission() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", requestparty='" + getRequestparty() + "'" +
            ", identity='" + getIdentity() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", passporttype='" + getPassporttype() + "'" +
            ", passportnumber='" + getPassportnumber() + "'" +
            ", expirationdate='" + getExpirationdate() + "'" +
            ", visatype='" + getVisatype() + "'" +
            ", reason='" + getReason() + "'" +
            ", otherreason='" + getOtherreason() + "'" +
            ", guest='" + getGuest() + "'" +
            ", adress='" + getAdress() + "'" +
            ", administation='" + getAdministation() + "'" +
            ", automaticcheck='" + getAutomaticcheck() + "'" +
            ", manualcheck='" + getManualcheck() + "'" +
            ", entries='" + getEntries() + "'" +
            ", exits='" + getExits() + "'" +
            ", lastmove='" + getLastmove() + "'" +
            ", summary='" + getSummary() + "'" +
            "}";
    }
}
