package com.work.easystep2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity @Table(name = "venderInfo")
public class Vender {

	@Id @Column(name = "venderId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer venderid;
	
	@Column(name = "enable")
	private boolean enable =false;
	
	@OneToOne(mappedBy = "vender", cascade = CascadeType.ALL, orphanRemoval = true)
	private VenderToken venderToken;
	
	@OneToOne(mappedBy = "vender",cascade = CascadeType.ALL, orphanRemoval = true)
	private VenderPerImg venderPerImg;
	
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "vender",fetch = FetchType.LAZY)
	private VenderInformation vInfo;
	
	@Column(name = "venderAcct")
	private String acct;
	
	@Column(name = "venderPwd")
	private String pwd;
	
	@Column(name = "venderName")
	private String username;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "zipNo")
	private String zipno;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "coName")
	private String coname;
	
	@Column(name = "uniNum")
	private String uninum;
	
	@Column(name = "googleId")
	private String sub;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipNo", insertable = false, updatable = false)
	private Zip zip;
	
	public Vender() {
		super();
	}
	public Vender(String acct, String pwd, String username, String tel, String mobile, String zipno, String street,
			String email, String coname, String uninum) {
		super();
		this.acct = acct;
		this.pwd = pwd;
		this.username = username;
		this.tel = tel;
		this.mobile = mobile;
		this.zipno = zipno;
		this.street = street;
		this.email = email;
		this.coname = coname;
		this.uninum = uninum;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	public VenderPerImg getVenderPerImg() {
		return venderPerImg;
	}
	public void setVenderPerImg(VenderPerImg venderPerImg) {
		this.venderPerImg = venderPerImg;
	}
	public void setVenderid(Integer venderid) {
		this.venderid = venderid;
	}
	public Integer getVenderid() {
		return venderid;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public VenderToken getVenderToken() {
		return venderToken;
	}
	public void setVenderToken(VenderToken venderToken) {
		this.venderToken = venderToken;
	}
	public Zip getZip() {
		return zip;
	}
	public void setZip(Zip zipbean) {
		this.zip = zipbean;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConame() {
		return coname;
	}
	public void setConame(String coname) {
		this.coname = coname;
	}
	public String getUninum() {
		return uninum;
	}
	public void setUninum(String uninum) {
		this.uninum = uninum;
	}
	public VenderInformation getVInfo() {
		return vInfo;
	}
	public void setVInfo(VenderInformation vInfo) {
		this.vInfo = vInfo;
	}

}

