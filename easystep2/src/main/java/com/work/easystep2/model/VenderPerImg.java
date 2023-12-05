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

@Entity @Table(name = "venderShot")
public class VenderPerImg {

	@Id @Column(name = "photoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer photoid;
	
	private String photoToken;

	private String photoUrl;
	
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Vender.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "venderid", referencedColumnName = "venderid")
	private Vender vender;
	
	public VenderPerImg() {
		super();
	}


	public VenderPerImg(String photoToken, String photoUrl, Vender vender) {
		super();
		this.photoToken = photoToken;
		this.photoUrl = photoUrl;
		this.vender = vender;
	}
	
	

	public Integer getPhotoid() {
		return photoid;
	}

	public void setPhotoid(Integer photoid) {
		this.photoid = photoid;
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

	public Vender getVender() {
		return vender;
	}

	public void setVender(Vender vender) {
		this.vender = vender;
	}

}
