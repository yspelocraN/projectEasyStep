package com.work.easystep2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatTraDTO {

	private Integer travelerId;
	
	private String travelerName;
	
	public ChatTraDTO() {
		
	}

	public Integer getTravelerId() {
		return travelerId;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerId(Integer travelerId) {
		this.travelerId = travelerId;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	
}
