package com.work.easystep2.DTO;

import com.work.easystep2.model.Traveler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelerDTO {
	
	private Integer travelerid;
	private String acct;
	private String pwd;
	private String username;
	private String sexabbr;
	private String mobile;
	private String email;
	private String tel;
	private String ecname;
	private String relationship;

	public TravelerDTO() {
	}
	public TravelerDTO(Traveler travelerDTO) {
		this.travelerid=travelerDTO.getTravelerid();
		this.acct = travelerDTO.getAcct();
		this.pwd =travelerDTO.getPwd();
		this.username = travelerDTO.getUsername();
		this.sexabbr = travelerDTO.getSexabbr();
		this.tel =travelerDTO.getTel();
		this.mobile = travelerDTO.getMobile();
		this.email = travelerDTO.getEmail();
		this.ecname=travelerDTO.getEcname();
		this.relationship=travelerDTO.getRelationship();
	}
	public Integer getTravelerid() {
		return travelerid;
	}
	public void setTravelerid(Integer travelerid) {
		this.travelerid = travelerid;
	}
	public String getAcct() {
		return acct;
	}
	public void setAcct(String acct) {
		this.acct = acct;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSexabbr() {
		return sexabbr;
	}
	public void setSexabbr(String sexabbr) {
		this.sexabbr = sexabbr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEcname() {
		return ecname;
	}
	public void setEcname(String ecname) {
		this.ecname = ecname;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

}

