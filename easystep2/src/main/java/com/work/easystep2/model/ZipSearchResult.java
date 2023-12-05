package com.work.easystep2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZipSearchResult extends SearchResult<Zip>{

    @JsonProperty("zipNo")
    private String zipNo;

    @JsonProperty("city")
    private String city;

    @JsonProperty("Dist")
    private String dist;

    public ZipSearchResult(Zip data) {
        super(data);
        this.zipNo = data.getZipno();
        this.city = data.getCity();
        this.dist = data.getDist();
    }

    public String getZipNo() {
        return zipNo;
    }

    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return "[" + zipNo + ", " + city + ", " + dist + "]";
    }
    
}
