package com.work.easystep2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.work.easystep2.DTO.ChatWithVenderDTO;

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

@Entity
@Table(name = "chat_vender")
@Getter
@Setter
public class ChatWithVender {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "msgid")
	private Integer chatId;
	@Column(name = "vender_acct")
	private String venderAcct;
	@Column(name = "traveler_acct")
	private String travelerAcct;
	@Column(name = "sender")
	private String sender;
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
	public ChatWithVender(ChatWithVenderDTO c) {
		this.venderAcct = c.getVenderAcct();
		this.travelerAcct = c.getTravelerAcct();
		this.sender = c.getSender();
		this.msg = c.getMsg();
	}
	public ChatWithVender() {
		
	}
	public Integer getChatId() {
		return chatId;
	}
	public String getVenderAcct() {
		return venderAcct;
	}
	public String getTravelerAcct() {
		return travelerAcct;
	}
	public String getSender() {
		return sender;
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
	public void setVenderAcct(String venderAcct) {
		this.venderAcct = venderAcct;
	}
	public void setTravelerAcct(String travelerAcct) {
		this.travelerAcct = travelerAcct;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setWriteDatetime(Date writeDatetime) {
		this.writeDatetime = writeDatetime;
	}
	
}

