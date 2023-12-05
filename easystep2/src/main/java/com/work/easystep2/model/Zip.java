package com.work.easystep2.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name="zip")
public class Zip {
	
	@Id @Column(name = "zipNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String zipno;
	
	@Column(name = "city")
	private String city;
	@Column(name = "Dist")
	private String dist;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zip", cascade = CascadeType.ALL)
	private Set<Vender> venderbean = new HashSet<Vender>();
	
	
	public Zip() {
		super();
	}
	
	public Set<Vender> getVender() {
		return venderbean;
	}
	public void setVender(Set<Vender> venderbean) {
		this.venderbean = venderbean;
	}
	public String getZipno() {
		return zipno;
	}
	public void setZipno(String zipno) {
		this.zipno = zipno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}

}
