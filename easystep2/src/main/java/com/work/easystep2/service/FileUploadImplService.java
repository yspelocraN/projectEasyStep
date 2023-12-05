package com.work.easystep2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.work.easystep2.REPO.PhotoReopsitory;
import com.work.easystep2.REPO.TravelerPerImgRepository;
import com.work.easystep2.REPO.VenderImgRepository;
import com.work.easystep2.REPO.VenderPerImgRepository;
import com.work.easystep2.model.FileUpload;
import com.work.easystep2.model.Photo;
import com.work.easystep2.model.PhotoAlbum;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerPerImg;
import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderImg;
import com.work.easystep2.model.VenderPerImg;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadImplService implements FileUpload {
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private TravelerPerImgService imgService;
	
	@Autowired
	private VenderPerImgService vImgService;
	
	@Autowired
	TravelerService trService;
	
	@Autowired
	VenderService vedService;
	
	@Autowired
	TravelerPerImgRepository imgRep;
	
	@Autowired
	VenderPerImgRepository vImgRep;
	
	@Autowired
	private PhotoReopsitory pREPO;
	
	@Autowired
	private VenderImgRepository vIRpo;
	
	@Override
	public String uploadFile(MultipartFile multipartFile, Integer id) throws IOException {
		Traveler traveler = trService.findTraveler(id);
		if (traveler != null) {
			if (traveler.getTravelerPerImg() != null) {
				TravelerPerImg perImg = traveler.getTravelerPerImg();
				Integer perImgId = perImg.getPhotoid();
				Optional<TravelerPerImg> optional = Optional.ofNullable(imgRep.findTravelerPerImgByphotoid(perImgId));
				TravelerPerImg travelerPerImg = optional.get();
				String uniquePublicId = travelerPerImg.getPhotoToken();
				Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId); // 设置上传选项，包括public_id
			    Map<?,?> response = cloudinary.uploader().upload(multipartFile.getBytes(), uploadOptions);
			    Object urlObject = response.get("url");
			    String url = urlObject.toString();
				travelerPerImg.setPhotoUrl(url);
				imgService.save(travelerPerImg);
				return url;
			} else {
				String uniquePublicId = UUID.randomUUID().toString();
				Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId); // 设置上传选项，包括public_id
				Map<?,?> response = cloudinary.uploader().upload(multipartFile.getBytes(), uploadOptions);
				Object urlObject = response.get("url");
				String url = urlObject.toString();
				TravelerPerImg trImg = new TravelerPerImg(uniquePublicId, url,traveler);
				imgService.save(trImg);
				return url;
			}
		}
		return null;
	}

	@Override
	public String venderUpload(MultipartFile multipartFile, Integer id) throws IOException {
		Vender vender = vedService.findVender(id);
		System.out.println("t" + vender);
		if (vender != null) {
			if (vender.getVenderPerImg() != null) {
				VenderPerImg perImg = vender.getVenderPerImg();
				Integer perImgId = perImg.getPhotoid();
				Optional<VenderPerImg> optional = Optional.ofNullable(vImgRep.findVenderPerImgByphotoid(perImgId));
				VenderPerImg venderePerImg = optional.get();
				String uniquePublicId = venderePerImg.getPhotoToken();
				Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId); // 设置上传选项，包括public_id
			    Map<?,?> response = cloudinary.uploader().upload(multipartFile.getBytes(), uploadOptions);
			    Object urlObject = response.get("url");
			    String url = urlObject.toString();
			    venderePerImg.setPhotoUrl(url);
				vImgService.save(venderePerImg);
				return url;
			} else {
				System.out.println("ko");
				String uniquePublicId = UUID.randomUUID().toString();
				Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId); // 设置上传选项，包括public_id
				Map<?,?> response = cloudinary.uploader().upload(multipartFile.getBytes(), uploadOptions);
				Object urlObject = response.get("url");
				String url = urlObject.toString();
				VenderPerImg vedImg = new VenderPerImg(uniquePublicId, url,vender);
				vImgService.save(vedImg);
				return url;
			}
		}
		return null;
	}

	@Override
	public List<Photo> uploadMultipleFiles(List<MultipartFile> files, Integer albumId) throws IOException {
	    List<Photo> uploadedPhotos = new ArrayList<>();

	    for (MultipartFile file : files) {
	        String uniquePublicId = UUID.randomUUID().toString();
	        Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId);

	        try {
	            Map<?, ?> response = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
	            Object urlObject = response.get("url");
	            String url = urlObject.toString();

	            Photo photo = new Photo();
	            photo.setPhotoURL(url);

	            PhotoAlbum album = new PhotoAlbum();
	            album.setAlbumId(albumId);
	            photo.setAlbumId(album);

	            Photo savedPhoto = pREPO.save(photo);
	            uploadedPhotos.add(savedPhoto);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		return uploadedPhotos;
	}

	// 廠商環境照片 新添進入 如果是更新就刪除原本的
	@Override
	public void addvenderFiles(List<MultipartFile> files, Integer venderid) throws IOException {	
		vIRpo.deletePhotos(venderid);
		for (MultipartFile file : files) {
	        String uniquePublicId = UUID.randomUUID().toString();
	        Map<String, String> uploadOptions = Map.of("public_id", uniquePublicId);
	        try {
	            Map<?, ?> response = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
	            Object urlObject = response.get("url");
	            String url = urlObject.toString();
	            VenderImg photo = new VenderImg();
	            photo.setVenderImg(url);
	            photo.setVenderid(venderid);
	            vIRpo.save(photo);       
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
