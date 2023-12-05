package com.work.easystep2.DTO;
	

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.easystep2.model.Traveler;


@JsonInclude(Include.NON_NULL)
public class TravelerSearchResultDTO extends SearchResultDTO<Traveler> {
 
    @JsonProperty("travelerAcct")
    private String travelerAcct;

    @JsonProperty("travelerName")
    private String travelerName;

    @JsonProperty("sexAbbr")
    private String sexAbbr;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("travelerId")
    private Integer travelerId;

    public TravelerSearchResultDTO(Traveler data) {
        super(data);
        this.travelerAcct = data.getAcct();
        this.travelerName = data.getUsername();
        this.sexAbbr = data.getSexabbr();
        this.email=data.getEmail();
        this.travelerId=data.getTravelerid();
        
    }

 

    public String getTravelerAcct() {
        return travelerAcct;
    }

    public void setTravelerAcct(String travelerAcct) {
        this.travelerAcct = travelerAcct;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getSexAbbr() {
        return sexAbbr;
    }

    public void setSexAbbr(String sexAbbr) {
        this.sexAbbr = sexAbbr;
    }

    @Override
    public String toString() {
        return super.toString() + ", [travelerAcct=" + travelerAcct + ", travelerName=" + travelerName + ", sexAbbr=" + sexAbbr + "]";
    }
    
}