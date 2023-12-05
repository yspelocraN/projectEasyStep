package com.work.easystep2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhotoId")
    private Integer PhotoId;

    @Lob
    @Column(name = "PhotoURL")
    private String PhotoURL;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private PhotoAlbum albumId;

    public Photo() {
    }

    public Photo(Photo photo) {
        this.PhotoURL = photo.getPhotoURL();
        this.PhotoId = photo.getPhotoId();
    }

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


    public PhotoAlbum getAlbumId() {
        return albumId;
    }

    public void setAlbumId(PhotoAlbum albumId) {
        this.albumId = albumId;
    }
    
}

