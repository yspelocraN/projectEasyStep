package com.work.easystep2.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.DTO.BlogDTO;
import com.work.easystep2.REPO.TravelerPerImgRepository;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerPerImg;


@RestController
public class Blogcontroller {
	
	@Autowired
	private TravelerRepository tRepo;
	@Autowired
	private TravelerPerImgRepository preRepo;
	
	//尋找部落格
	@GetMapping("/blog/{travelerId}")
	public ResponseEntity<?> findMyBlog(@PathVariable Integer travelerId) {
		Optional<Traveler> result = tRepo.findById(travelerId);
		TravelerPerImg photoresult = preRepo.findByTravelerId(travelerId);
		String photo;
		if(photoresult == null){
			photo = "https://res.cloudinary.com/dciwmbvcl/image/upload/v1698479284/ycbgbhoevl950faqlhln.jpg";
		}else {
			photo = photoresult.getPhotoUrl();		
		}
		if(result.isPresent()) {
			Traveler t = result.get();
			BlogDTO bDTO = new BlogDTO(t, photo);
			return new ResponseEntity<BlogDTO>(bDTO,HttpStatus.OK);
			
		}
		return new ResponseEntity<String>("沒有此資料",HttpStatus.NOT_FOUND);

	}
	
	//部落格列表
	@GetMapping("/blog/list")
	public ResponseEntity<?> findAllTraveler(){
		List<Traveler> result = tRepo.findAll();
		if(result != null) {
			List<BlogDTO> blogDTO = new ArrayList<>();
			for(Traveler T : result) {
				BlogDTO B = new BlogDTO(T);
				blogDTO.add(B);
			}
			return new ResponseEntity<>(blogDTO,HttpStatus.OK);
		}
		return new ResponseEntity<String>("沒有資料",HttpStatus.NOT_FOUND);	
	}
	
	@PutMapping("/blog/editAbout/{travelerId}")
	public ResponseEntity<?> updateAbout(@PathVariable Integer travelerId,
										 @RequestParam String about){
		Optional<Traveler> result = tRepo.findById(travelerId);
		if(result != null) {
			Traveler t = result.get();
			t.setAbout(about);
			tRepo.save(t);
			return ResponseEntity.ok("");
		}else {
        	return ResponseEntity.notFound().build();
		}
	}
	
}
