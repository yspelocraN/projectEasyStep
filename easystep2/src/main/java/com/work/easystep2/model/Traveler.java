package com.work.easystep2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "travelerInfo")
public class Traveler {

	@Id @Column(name = "travelerId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer travelerid;
	
	@Column(name = "travelerAcct")
	private String acct;

	@Column(name = "travelerPwd")
	private String pwd;
	

	@Column(name = "travelerName")
	private String username;

	@Column(name = "enable")
	private boolean enable=false;
	

	@Column(name = "sexAbbr")
	private String sexabbr;
	

	@Column(name = "tel")
	private String mobile;
	
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "ecTel")
	private String tel;
	
	@Column(name = "ecName")
	private String ecname;
	
	@Column(name = "googleId")
	private String sub;
	
	@Column(name = "ecRel")
	private String relationship;
	
	@OneToOne(mappedBy = "traveler", cascade = CascadeType.ALL, orphanRemoval = true)
	private TravelerToken travelerToken;
	
	
	@OneToOne(mappedBy = "traveler", cascade = CascadeType.ALL, orphanRemoval = true)
	private TravelerPerImg travelerPerImg;
	
	@Column(name = "about")
	private String about;
	
	public Traveler() {
	}
	public Traveler(String acct, String pwd, String username, String sexabbr, String tel, String mobile,
			String email) {
		this.acct = acct;
		this.pwd = pwd;
		this.username = username;
		this.sexabbr = sexabbr;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
		
	}
	
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public TravelerPerImg getTravelerPerImg() {
		return travelerPerImg;
	}
	public void setTravelerPerImg(TravelerPerImg travelerPerImg) {
		this.travelerPerImg = travelerPerImg;
	}
	public TravelerToken getTravelerToken() {
		return travelerToken;
	}
	public void setTravelerToken(TravelerToken travelerToken) {
		this.travelerToken = travelerToken;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
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
	
	public int getTravelerid() {
		return travelerid;
	}
	public void setTravelerid(int travelerid) {
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}

