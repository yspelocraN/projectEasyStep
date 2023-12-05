package com.work.easystep2.DTO;

import java.util.Date;

import com.work.easystep2.model.Article;

public class ArticleDTO {

	private String Text;
	private String Title;
	private Date createTime;
	private Integer ArticleId;
	public Integer getArticleId() {
		return ArticleId;
	}

	public void setArticleId(Integer articleId) {
		ArticleId = articleId;
	}

	public ArticleDTO(Article art) {
		this.Text=art.getText();
		this.Title=art.getTitle();
		this.createTime=art.getCreateTime();
		this.ArticleId=art.getArticleId();		
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
	
}
