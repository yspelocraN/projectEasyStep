package com.work.easystep2.DTO;

import com.work.easystep2.model.Photo;

public class PhotoDTO {

	private Integer PhotoId;
	private String PhotoURL;
	public Integer getPhotoId() {
		return PhotoId;
	}
	public void setPhotoId(Integer photoId) {
		PhotoId = photoId;
	}
	public String getPhotoURL() {
		return PhotoURL;
	}
	public void setPhotoURL(String PhotoUrl) {
		PhotoURL = PhotoUrl;
	}
	public PhotoDTO(Photo p) {
		this.PhotoId=p.getPhotoId();
		this.PhotoURL=p.getPhotoURL();
	}
	
}
