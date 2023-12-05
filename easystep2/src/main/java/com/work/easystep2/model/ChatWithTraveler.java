package com.work.easystep2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chat_traveler")
public class ChatWithTraveler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "msgid")
	private Integer chatId;
	@Column(name = "traveler_id1")
	private Integer travelerid1;
	@Column(name = "traveler_id2")
	private Integer travelerid2;
	@Column(name = "sender_id")
	private Integer senderid;
	@Column(name = "msg")
	private String msg;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "msgdatetime")
	private Date writeDatetime;
	@PrePersist   // 當物件轉換成 Persistent 狀態以前，做以下事情
	public void onCreate() {
		if(writeDatetime == null) {
			writeDatetime = new Date();
		}
	}
	public ChatWithTraveler() {
		
	}
	public Integer getChatId() {
		return chatId;
	}
	public Integer getTravelerid1() {
		return travelerid1;
	}
	public Integer getTravelerid2() {
		return travelerid2;
	}
	public Integer getSenderid() {
		return senderid;
	}
	public String getMsg() {
		return msg;
	}
	public Date getWriteDatetime() {
		return writeDatetime;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	public void setTravelerid1(Integer travelerid1) {
		this.travelerid1 = travelerid1;
	}
	public void setTravelerid2(Integer travelerid2) {
		this.travelerid2 = travelerid2;
	}
	public void setSenderid(Integer senderid) {
		this.senderid = senderid;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setWriteDatetime(Date writeDatetime) {
		this.writeDatetime = writeDatetime;
	}
	
}
