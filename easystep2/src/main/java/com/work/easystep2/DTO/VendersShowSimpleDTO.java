package com.work.easystep2.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendersShowSimpleDTO {

	private Integer venderId;
	
	private String venderName;
	
	private String venderIntroduce;
	
	private String showphoto;
	
	private Date writeOrUpdate;
	
	public VendersShowSimpleDTO() {
		
	}

	public Integer getVenderId() {
		return venderId;
	}

	public String getVenderName() {
		return venderName;
	}

	public String getVenderIntroduce() {
		return venderIntroduce;
	}

	public String getShowphoto() {
		return showphoto;
	}

	public Date getWriteOrUpdate() {
		return writeOrUpdate;
	}

	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public void setVenderIntroduce(String venderIntroduce) {
		this.venderIntroduce = venderIntroduce;
	}

	public void setShowphoto(String showphoto) {
		this.showphoto = showphoto;
	}

	public void setWriteOrUpdate(Date writeOrUpdate) {
		this.writeOrUpdate = writeOrUpdate;
	}
	
}
