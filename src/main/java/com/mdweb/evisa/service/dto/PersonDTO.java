package com.mdweb.evisa.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mdweb.evisa.domain.Person} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonDTO implements Serializable {

    private String id;

    @NotNull
    private String diplomaticmission;

    @NotNull
    private String nationality;

    @NotNull
    private String requestparty;

    @NotNull
    private String identity;

    @NotNull
    private ZonedDateTime birthday;

    @NotNull
    private String passporttype;

    @NotNull
    private String passportnumber;

    @NotNull
    private ZonedDateTime expirationdate;

    @NotNull
    private String visatype;

    @NotNull
    private String reason;

    @NotNull
    private String otherreason;

    @NotNull
    private String guest;

    @NotNull
    private String adress;

    @NotNull
    private String administation;

    @NotNull
    private String automaticcheck;

    @NotNull
    private String manualcheck;

    @NotNull
    private String entries;

    @NotNull
    private String exits;

    @NotNull
    private String lastmove;

    @NotNull
    private String summary;

    private Set<RequestDTO> requests = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiplomaticmission() {
        return diplomaticmission;
    }

    public void setDiplomaticmission(String diplomaticmission) {
        this.diplomaticmission = diplomaticmission;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRequestparty() {
        return requestparty;
    }

    public void setRequestparty(String requestparty) {
        this.requestparty = requestparty;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPassporttype() {
        return passporttype;
    }

    public void setPassporttype(String passporttype) {
        this.passporttype = passporttype;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public ZonedDateTime getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(ZonedDateTime expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getVisatype() {
        return visatype;
    }

    public void setVisatype(String visatype) {
        this.visatype = visatype;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOtherreason() {
        return otherreason;
    }

    public void setOtherreason(String otherreason) {
        this.otherreason = otherreason;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdministation() {
        return administation;
    }

    public void setAdministation(String administation) {
        this.administation = administation;
    }

    public String getAutomaticcheck() {
        return automaticcheck;
    }

    public void setAutomaticcheck(String automaticcheck) {
        this.automaticcheck = automaticcheck;
    }

    public String getManualcheck() {
        return manualcheck;
    }

    public void setManualcheck(String manualcheck) {
        this.manualcheck = manualcheck;
    }

    public String getEntries() {
        return entries;
    }

    public void setEntries(String entries) {
        this.entries = entries;
    }

    public String getExits() {
        return exits;
    }

    public void setExits(String exits) {
        this.exits = exits;
    }

    public String getLastmove() {
        return lastmove;
    }

    public void setLastmove(String lastmove) {
        this.lastmove = lastmove;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(Set<RequestDTO> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonDTO{" +
            "id='" + getId() + "'" +
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
            ", requests=" + getRequests() +
            "}";
    }
}
