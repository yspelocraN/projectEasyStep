package com.work.easystep2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VenderSearchResult extends SearchResult<Vender>{

    @JsonProperty("venderAcct")
    private String acct;

    @JsonProperty("email")
    private String email;

    @JsonProperty("coName")
    private String coname;

    public VenderSearchResult(Vender data) {
        super(data);
        this.acct = data.getAcct();
        this.email = data.getEmail();
        this.coname = data.getConame();
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConame() {
        return coname;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }

    @Override
    public String toString() {
        return super.toString() + ", [acct=" + acct + ", email=" + email + ", coname=" + coname + "]";
    }

}





