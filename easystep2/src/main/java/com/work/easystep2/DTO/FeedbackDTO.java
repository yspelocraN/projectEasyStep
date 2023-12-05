package com.work.easystep2.DTO;

import java.util.Date;

import com.work.easystep2.model.VenderFeedback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
	
	private String venderName;
	
	private String travelerName;
	
	private Date writeDate;
	
	private Integer score;
	
	private String feedback;
	
	private String travelerPhoto;

	public FeedbackDTO(VenderFeedback v) {
		this.writeDate = v.getWriteDate();
		this.score = v.getScore();
		this.feedback = v.getFeedback();
	}
	public FeedbackDTO() {
		
	}
	public String getVenderName() {
		return venderName;
	}
	public String getTravelerName() {
		return travelerName;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public Integer getScore() {
		return score;
	}
	public String getFeedback() {
		return feedback;
	}
	public String getTravelerPhoto() {
		return travelerPhoto;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public void setTravelerPhoto(String travelerPhoto) {
		this.travelerPhoto = travelerPhoto;
	}
	
}
