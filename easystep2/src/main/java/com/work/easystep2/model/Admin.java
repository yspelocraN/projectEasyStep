package com.work.easystep2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrator")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adminId")
	private	Integer	adminId;
	
	@Column(name = "adminAcct")
	private	String	adminAcct;
	
	@Column(name = "adminPwd")
	private	String	adminPwd;
	
	@Column(name = "adminRole")
	private	Integer	adminLv;
	
	
	public Admin() {}
	public Admin(String adminAcct, String adminPwd) {
		this.adminAcct	= adminAcct;
		this.adminPwd	= adminPwd;
	}
	
	
	/* getter */
	public Integer getAdminId() {
		return adminId;
	}
	public String getAdminAcct() {
		return adminAcct;
	}
	public String getAdminPwd() {
		return adminPwd;
	}
	public Integer getAdminLv() {
		return adminLv;
	}
	
	
	/* getter */
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

}
