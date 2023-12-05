package com.work.easystep2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="vender_img")
public class VenderImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vender_imgid")
	private Integer venderImgid;

	@Lob
	@Column(name="vender_img")
	private String venderImg;
	
	@Column(name="vender_venderid")
	private Integer venderid;

	public Integer getVenderImgid() {
		return venderImgid;
	}

	public String getVenderImg() {
		return venderImg;
	}

	public Integer getVenderid() {
		return venderid;
	}

	public void setVenderImgid(Integer venderImgid) {
		this.venderImgid = venderImgid;
	}

	public void setVenderImg(String venderImg) {
		this.venderImg = venderImg;
	}

	public void setVenderid(Integer venderid) {
		this.venderid = venderid;
	}
	
}
