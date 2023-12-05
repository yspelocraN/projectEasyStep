package com.work.easystep2.DTO;

import com.work.easystep2.model.Traveler;

public class BlogDTO {

	private Integer travelerid;
	private String username;
	private String sexabbr;
	private String mobile;
	private String email;
	private String tel;
	private String photoURL;
	private String about;
	
	public BlogDTO(Traveler traveler,String photoURL) {
		this.travelerid=traveler.getTravelerid();
		this.username=traveler.getUsername();
		this.sexabbr=traveler.getSexabbr();
		this.mobile=traveler.getMobile();
		this.email=traveler.getEmail();
		this.tel=traveler.getTel();
		this.photoURL=photoURL;
		this.about=traveler.getAbout();
	}

	public BlogDTO(Traveler t) {
		this.travelerid=t.getTravelerid();
		this.username=t.getUsername();
		this.email=t.getEmail();
		this.tel=t.getTel();
	}

	public Integer getTravelerid() {
		return travelerid;
	}

	public void setTravelerid(Integer travelerid) {
		this.travelerid = travelerid;
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

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}
