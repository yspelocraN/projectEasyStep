package com.work.easystep2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhotoAlbum")
public class PhotoAlbum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AlbumId")
	private Integer AlbumId;
	
	@Column(name = "AlbumName")
	private String AlbumName;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "albumId")
	private List<Photo> Photos;
	
	@Column(name = "travelerId")
	private Integer travelerId;

	public Integer getAlbumId() {
		return AlbumId;
	}

	public void setAlbumId(Integer AlbumId) {
		this.AlbumId = AlbumId;
	}

	public String getAlbumName() {
		return AlbumName;
	}

	public void setAlbumName(String albumName) {
		AlbumName = albumName;
	}

	public List<Photo> getPhotos() {
		return Photos;
	}

	public void setPhotos(List<Photo> photos) {
		Photos = photos;
	}

	public Integer getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(Integer travelerId) {
		this.travelerId = travelerId;
	}
	
}
