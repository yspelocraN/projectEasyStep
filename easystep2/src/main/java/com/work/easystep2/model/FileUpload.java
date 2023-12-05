package com.work.easystep2.model;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {

	String uploadFile(MultipartFile multipartFile, Integer id) throws IOException;
	
	String venderUpload(MultipartFile multipartFile, Integer id) throws IOException;

	List<Photo> uploadMultipleFiles(List<MultipartFile> files, Integer id) throws IOException;
	
	// 功能是 傳一堆圖片 跟類似這堆圖片庫ID
	void addvenderFiles(List<MultipartFile> files, Integer venderid) throws IOException;

}
