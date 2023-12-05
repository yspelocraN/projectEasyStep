package com.work.easystep2.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.DTO.TravelerDTO;
import com.work.easystep2.model.TravelerPerImg;
import com.work.easystep2.model.Vender;
import com.work.easystep2.DTO.VenderDTO;
import com.work.easystep2.model.VenderPerImg;
import com.work.easystep2.service.FileUploadImplService;
import com.work.easystep2.service.TravelerService;
import com.work.easystep2.service.VenderService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/Cloud")
@CrossOrigin()
public class FileUploadController {
	
	@Autowired
	 private FileUploadImplService fileService;
	
	@Autowired
	private TravelerService trlsService;
	
	@Autowired
	private VenderService vedService;
	
 
	@PostMapping("/upload2")
	public String uploadFile2(@RequestParam("image")MultipartFile multipartFile, Integer id) throws IOException {
		String imageURL = fileService.uploadFile(multipartFile, id);
		return imageURL;
	}
	 
	@PostMapping("/VenderUpload")
	public String uploadFile(@RequestParam("image")MultipartFile multipartFile, Integer id) throws IOException {
		String imageURL = fileService.venderUpload(multipartFile,id);
		return imageURL;
	}
	 
	//將更新資料存進session
	@GetMapping("/getphoto")
	@ResponseBody
	public String getSessionPhoto(HttpSession session) throws JsonProcessingException, JSONException {
		TravelerDTO traveler =(TravelerDTO)session.getAttribute("DTO");
		Integer id= traveler.getTravelerid();
		Traveler result=trlsService.findTraveler(id);
		if(result.getTravelerPerImg()!=null) {
			TravelerPerImg perImg=result.getTravelerPerImg();
			String url=perImg.getPhotoUrl();
			session.setAttribute("URL",url);
			String newUrl=(String) session.getAttribute("URL");
			return newUrl;
		}
		return null;
	}
		
	//將更新資料存進session
	@GetMapping("/getvephoto")
	@ResponseBody
	public String getVendrPhoto(HttpSession session) throws JsonProcessingException, JSONException {
		VenderDTO vender = (VenderDTO)session.getAttribute("DTO");
		Integer id = vender.getVenderid();
		Vender result = vedService.findVender(id);
		if(result.getVenderPerImg() != null) {
			VenderPerImg perImg = result.getVenderPerImg();
			String url = perImg.getPhotoUrl();
			session.setAttribute("URL",url);
			String newUrl = (String)session.getAttribute("URL");
			return newUrl;
		}
		return null;
	}
	
}
