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

@Entity
@Table(name="Article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ArticleId")
	private Integer ArticleId;
	
	@Column(name="Text")
	private String Text;
	
	@Column(name="Title")
	private String Title;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")//固定時間在java環境中的格式
	@Temporal(TemporalType.DATE)//選擇時間類型
	@Column(name="CreateTime")
	private Date createTime;
	
	@Column(name = "travelerId")
	private Integer travelerId;
	
	@PrePersist //當物件轉換成 Persistent 狀態以前,要做的事
	public void onCreate() {
		if(createTime==null) {
			createTime= new Date();
		}
	}

	public Integer getArticleId() {
		return ArticleId;
	}

	public void setArticleId(Integer articleId) {
		ArticleId = articleId;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(Integer travelerId) {
		this.travelerId = travelerId;
	}
	
}
