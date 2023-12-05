package com.work.easystep2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "travelerShot")
public class TravelerPerImg {

	@Id @Column(name = "photoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer photoid;
	
	private String photoToken;

	private String photoUrl;
	
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Traveler.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "travelerid", referencedColumnName = "travelerid")
	private Traveler traveler;
	
	public TravelerPerImg() {
		super();
	}

	public TravelerPerImg(String photoToken, String photoUrl, Traveler traveler) {
		super();
		this.photoToken = photoToken;
		this.photoUrl = photoUrl;
		this.traveler = traveler;
	}



	public void setPhotoid(Integer photoid) {
		this.photoid = photoid;
	}

	public Integer getPhotoid() {
		return photoid;
	}

	public String getPhotoToken() {
		return photoToken;
	}

	public void setPhotoToken(String photoToken) {
		this.photoToken = photoToken;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

}
