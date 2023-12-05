package com.work.easystep2.DTO;

import com.work.easystep2.model.PhotoAlbum;

public class PhotoAlbumDTO {

	private Integer AlbumId;
	private String AlbumName;
	private Integer travelerId;
	public Integer getAlbumId() {
		return AlbumId;
	}
	public void setAlbumId(Integer albumId) {
		AlbumId = albumId;
	}
	public String getAlbumName() {
		return AlbumName;
	}
	public void setAlbumName(String albumName) {
		AlbumName = albumName;
	}
	public Integer getTravelerId() {
		return travelerId;
	}
	public void setTravelerId(Integer travelerId) {
		this.travelerId = travelerId;
	}
	public PhotoAlbumDTO(PhotoAlbum p) {
		this.AlbumId=p.getAlbumId();
		this.AlbumName=p.getAlbumName();
		this.travelerId=p.getTravelerId();		
	}
	
}
