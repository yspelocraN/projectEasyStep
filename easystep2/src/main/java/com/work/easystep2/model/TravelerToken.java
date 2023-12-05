package com.work.easystep2.model;

import java.util.Date;

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

@Entity @Table(name = "travelerToken")
public class TravelerToken {

	@Id @Column(name = "tokenId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tokenid;
	
	private String token;
	
	
	@Column(name = "expirydate", nullable = false, insertable = true, updatable = true)
	private Date expiryDate;
	
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Traveler.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "travelerid", referencedColumnName = "travelerid")
	private Traveler traveler;
	
	
	
	public TravelerToken() {
		super();
	}

	public TravelerToken(String token, Traveler traveler) {
		super();
		this.token = token;
		this.traveler = traveler;
	}

	public Integer getTokenid() {
		return tokenid;
	}

	public void setTokenid(Integer tokenid) {
		this.tokenid = tokenid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

}
