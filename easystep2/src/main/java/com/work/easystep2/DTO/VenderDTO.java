package com.work.easystep2.DTO;

import com.work.easystep2.model.Vender;

import lombok.Getter;

@Getter
public class VenderDTO {

	private Integer venderid;
	private String acct;
	private String pwd;
	private String username;
	private String tel;
	private String mobile;
	private String zipno;
	private String city;
	private String dist;
	private String street;
	private String email;
	private String coname;
	private String uninum;
	
	public VenderDTO() {
		super();
	}
	public VenderDTO(Vender venderDTO,String city,String dist) {
		super();
		this.venderid=venderDTO.getVenderid();
		this.acct = venderDTO.getAcct();
		this.pwd =venderDTO.getPwd();
		this.username = venderDTO.getUsername();
		this.tel = venderDTO.getTel();
		this.mobile = venderDTO.getMobile();
		this.zipno = venderDTO.getZipno();
		this.street = venderDTO.getStreet();
		this.email = venderDTO.getEmail();
		this.coname = venderDTO.getConame();
		this.uninum = venderDTO.getUninum();
		this.city=city;
		this.dist=dist;
	}
	
	@Override
	public String toString() {
		return "[venderid=" + venderid + ", acct=" + acct + ", pwd=" + pwd + ", username=" + username
				+ ", tel=" + tel + ", mobile=" + mobile + ", zipno=" + zipno + ", street=" + street + ", email=" + email
				+ ", coname=" + coname + ", uninum=" + uninum + "]";
	}
	public Integer getVenderid() {
		return venderid;
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