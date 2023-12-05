package com.work.easystep2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.work.easystep2.DTO.PhotoAlbumDTO;
import com.work.easystep2.REPO.AlbumRepository;
import com.work.easystep2.model.Photo;
import com.work.easystep2.model.PhotoAlbum;
import com.work.easystep2.service.FileUploadImplService;


@RestController
@CrossOrigin
public class AlbumController {
	
	@Autowired
	private AlbumRepository albumRepo;
	
	@Autowired
	 private FileUploadImplService fileService;
	
	
	//建立相簿
	@PostMapping("/album/create")
	public ResponseEntity<?> createAlbum(@RequestParam String albumName, @RequestParam String travelerid) {
		try {
			Integer travelerId = Integer.parseInt(travelerid);
			PhotoAlbum photoAlbum = new PhotoAlbum();
			photoAlbum.setAlbumName(albumName);
			photoAlbum.setTravelerId(travelerId);
			albumRepo.save(photoAlbum);
			return new ResponseEntity<String>("相簿創建成功",HttpStatus.OK);		
		} catch (Exception e) {
			return new ResponseEntity<String>("相簿創建失敗",HttpStatus.NOT_FOUND);		
		}
	}	
	
	//上傳照片
	@PostMapping("/photo/upload")
    public List<Photo> uploadPhotos(@RequestParam Integer albumId, @RequestParam("images") MultipartFile[] files) {
        try {
            List<Photo> uploadedUrls = fileService.uploadMultipleFiles(Arrays.asList(files), albumId);
            return uploadedUrls;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


	//show出該user的所有相簿
	@GetMapping("/photo/{UserId}")
	public List<PhotoAlbumDTO> getUserAlbums(@PathVariable("UserId") Integer userId) {
        List<PhotoAlbum> userAlbums = albumRepo.findByTravelerId(userId);
        
        if(userAlbums!=null) {       	
			List<PhotoAlbumDTO> pDTO = new ArrayList<>();
			
			for (PhotoAlbum pp : userAlbums) {
				PhotoAlbumDTO ppp = new PhotoAlbumDTO(pp);
				pDTO.add(ppp);
			}
			return pDTO;
        }
        return null;
    }
		
	//顯示該相簿的所有照片
	@GetMapping("/photo/showphoto")	
	public ResponseEntity<?> showPhotoByAlbum(@RequestParam Integer albumId) {
	    Optional<PhotoAlbum> albumOptional = albumRepo.findById(albumId);
	    if (albumOptional.isPresent()) {
	        PhotoAlbum album = albumOptional.get();
	        List<Photo> photos = new ArrayList<>();
	        
	        for (Photo photo : album.getPhotos()) {
	            Photo onePhoto = new Photo(photo);
	            photos.add(onePhoto);
	        }
	        
	        return new ResponseEntity<>(photos, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
	    }
	}
	
	//刪除相簿
	@DeleteMapping("/photo/deleteAlbum/{albumId}")
	public ResponseEntity<?> deleteAlbum(@PathVariable Integer albumId){
		Optional<PhotoAlbum> result = albumRepo.findById(albumId);
		if(result.isPresent()) {
			albumRepo.deleteById(albumId);
			return ResponseEntity.ok().build();
		}else {		
	    	return ResponseEntity.notFound().build();
		}
	}

}
