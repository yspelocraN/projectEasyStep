package com.work.easystep2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.work.easystep2.DTO.FeedbackDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="vender_feedback")
public class VenderFeedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vender_feedbackid")
	private Integer venderFId;
	
	@Column(name="vender_acct")
	private String venderAcct;
	
	@Column(name="traveler_acct")
	private String travelerAcct;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="write_date")
	private Date writeDate;
	
	@Column(name="score")
	private Integer score;
	
	@Column(name="feedback")
	private String feedback;
	
	@PrePersist   // 當物件轉換成 Persistent 狀態以前，做以下事情
	public void onCreate() {
		if(writeDate == null) {
			writeDate = new Date();
		}
	}
	public VenderFeedback(FeedbackDTO f) {
		this.score = f.getScore();
		this.feedback = f.getFeedback();
	}
	public VenderFeedback() {
		
	}
	public Integer getVenderFId() {
		return venderFId;
	}
	public String getVenderAcct() {
		return venderAcct;
	}
	public String getTravelerAcct() {
		return travelerAcct;
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
	public void setVenderFId(Integer venderFId) {
		this.venderFId = venderFId;
	}
	public void setVenderAcct(String venderAcct) {
		this.venderAcct = venderAcct;
	}
	public void setTravelerAcct(String travelerAcct) {
		this.travelerAcct = travelerAcct;
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
	
}