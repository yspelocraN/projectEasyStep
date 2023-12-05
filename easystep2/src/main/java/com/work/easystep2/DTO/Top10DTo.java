package com.work.easystep2.DTO;


import com.work.easystep2.model.VenderTop10;
import lombok.Getter;

@Getter
public class Top10DTo {
	
	private Integer venderid;
	private String vendername;
	private String zipno;
	private String city;
	private String dist;
	public Integer getVenderid() {
		return venderid;
	}

	public void setVenderid(Integer venderid) {
		this.venderid = venderid;
	}

	public String getVendername() {
		return vendername;
	}

	public void setVendername(String vendername) {
		this.vendername = vendername;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	private String street;
	private String tel;
	private String mobile;
	private String url;
	
	public Top10DTo() {
		super();
	}

	public Top10DTo(Integer venderid, String vendername, String zipno, String city, String dist, String street,
			String email, String tel, String mobile) {
		super();
		this.venderid = venderid;
		this.vendername = vendername;
		this.zipno = zipno;
		this.city = city;
		this.dist = dist;
		this.street = street;
		this.tel = tel;
		this.mobile = mobile;
	}
	public Top10DTo(VenderTop10 vender) {
		this.venderid = vender.getVenderId();
		this.vendername = vender.getVerdername();
		this.zipno = vender.getZipno();
		this.city=vender.getZip().getCity();
		this.dist=vender.getZip().getDist();
		this.street = vender.getStreet();
		this.tel = vender.getTel();
		this.mobile = vender.getMobile();
		this.url=vender.getUrl();
	}

}
