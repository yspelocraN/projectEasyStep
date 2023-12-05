package com.work.easystep2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "venderTop10")
public class VenderTop10 {

	@Id @Column(name = "venderId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer venderId;
	
	@Column(name = "venderName")
	private String verdername;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "zipNo")
	private String zipno;
	
	@Column(name = "street")
	private String street;
	
	
	@Column(name = "url")
	private String url;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipNo", insertable = false, updatable = false)
	private Zip zip;


	public VenderTop10() {
		super();
	}


	public VenderTop10(Integer venderId, String verdername, String tel, String mobile, String zipno, String street,
			Zip zip) {
		super();
		this.venderId = venderId;
		this.verdername = verdername;
		this.tel = tel;
		this.mobile = mobile;
		this.zipno = zipno;
		this.street = street;
		this.zip = zip;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getVenderId() {
		return venderId;
	}


	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}


	public String getVerdername() {
		return verdername;
	}


	public void setVerdername(String verdername) {
		this.verdername = verdername;
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


	public String getZipno() {
		return zipno;
	}


	public void setZipno(String zipno) {
		this.zipno = zipno;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public Zip getZip() {
		return zip;
	}


	public void setZip(Zip zip) {
		this.zip = zip;
	}

}
