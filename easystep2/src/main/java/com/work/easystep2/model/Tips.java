package com.work.easystep2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tips")
public class Tips {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title")
	private	String	title;
	
	@Column(name = "details")
	private	String	details;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "postDate")
	private Date	postDate;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	private Date	updateDate;
	
	
	/* Auto get date */
	@PrePersist
	public void onEdit() {
		if (postDate == null) {
			postDate = new Date();
		}
		updateDate = new Date();
	}
	
	
	
	public Tips() {}
	public Tips(String title, String details) {
		this.title	= title;
		this.details	= details;
	}



	public Integer getTipId() {
		return id;
	}
	public String getTipTitle() {
		return title;
	}
	public String getTipDetails() {
		return details;
	}



	public void setTipTitle(String tipTitle) {
		this.title = tipTitle;
	}
	public void setTipDetails(String tipDetails) {
		this.details = tipDetails;
	}

}
