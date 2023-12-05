package com.work.easystep2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "likeVender")
public class LikeVender {
	
	@Id @Column(name = "likeId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer likeId;
	
	@Column(name = "travelerId")
	private Integer travelerId;
	
	@Column(name = "venderId")
	private Integer venderId;

	public LikeVender() {
		super();
	}

	public LikeVender(Integer travelerId, Integer venderId) {
		super();
		this.travelerId = travelerId;
		this.venderId = venderId;
	}

	public Integer getLikeId() {
		return likeId;
	}

	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}

	public Integer getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(Integer travelerId) {
		this.travelerId = travelerId;
	}

	public Integer getVenderId() {
		return venderId;
	}

	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}

}
