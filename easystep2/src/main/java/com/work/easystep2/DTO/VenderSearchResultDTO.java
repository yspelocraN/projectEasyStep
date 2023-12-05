package com.work.easystep2.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.easystep2.model.Vender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VenderSearchResultDTO extends SearchResultDTO<Vender>{

    private Integer venderId;

    private String vendertel;

    private String venderconame;
    
    private String venderzipno;

    public VenderSearchResultDTO(Vender data) {
        super(data);
        this.venderId = data.getVenderid();
        this.vendertel = data.getTel();
        this.venderconame = data.getConame();
        this.venderzipno = data.getZipno();
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void getVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public String getVendertel() {
        return vendertel;
    }

    public void setVenderemail(String vendertel) {
        this.vendertel = vendertel;
    }

    public String getVenderconame() {
        return venderconame;
    }

    public void setVenderconame(String venderconame) {
        this.venderconame = venderconame;
    }

    @Override
    public String toString() {
        return super.toString() + ", [venderId=" + venderId + ", venderemail=" + vendertel + ", venderconame=" + venderconame + ", venderzipno=" + venderzipno + "]";
    }
  
}
