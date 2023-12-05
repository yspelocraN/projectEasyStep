package com.work.easystep2.DTO;

import java.util.Date;

import com.work.easystep2.model.ChatWithVender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatWithVenderDTO {
	
	private Integer chatId;

	private String venderAcct;

	private String travelerAcct;

	private String sender;

	private String msg;
	
	private Date writeDatetime;
	
	public ChatWithVenderDTO(ChatWithVender c) {
		this.chatId=c.getChatId();
		this.venderAcct=c.getVenderAcct();
		this.travelerAcct=c.getTravelerAcct();
		this.sender=c.getSender();
		this.msg=c.getMsg();
		this.writeDatetime=c.getWriteDatetime();
	}
	public ChatWithVenderDTO() {
		
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
