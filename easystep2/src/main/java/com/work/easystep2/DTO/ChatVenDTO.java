package com.work.easystep2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatVenDTO {
	
	private Integer venderId;
	
	private String venderName;
	
	public ChatVenDTO() {
		
	}

	public Integer getVenderId() {
		return venderId;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

}
