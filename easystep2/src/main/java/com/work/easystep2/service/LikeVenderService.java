package com.work.easystep2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.LikeVender;
import com.work.easystep2.REPO.LikeVenderRepository;

@Service
public class LikeVenderService {

	@Autowired
	private LikeVenderRepository likeReo;
	
	//尋找已有加入收藏的廠商
	public List<LikeVender> getLikeVender(Integer travelerId){
		Optional<List<LikeVender>> optional = Optional.ofNullable(likeReo.findLikeVenderBytravelerId(travelerId));
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	//一次刪除多筆有加入收藏的廠商
	public boolean deleteAllVenderById(Integer travelerid) {
		Optional<List<LikeVender>> optional = Optional.ofNullable(likeReo.findLikeVenderBytravelerId(travelerid));
		if(optional.isPresent()) {
			likeReo.deleteBytravelerId(travelerid);
			return true;
		}
		return false;
	}
	
	//一次刪除一筆收藏的廠商
	public boolean deleteOneVenderById(Integer travelerid,Integer venderid) {
		Optional<LikeVender> optional=Optional.ofNullable(likeReo.findBytravelerIdAndvenderId(travelerid,venderid));
		if(optional.isPresent()) {
			likeReo.deleteBytravelerIdAndVenderId(travelerid,venderid);
			return true;
		}
		return false;
	}
	
	
	//新增收藏廠商（一間
	public LikeVender addLikeVender(Integer travelerid,Integer venderid) {
		LikeVender newLikeVender=new LikeVender(travelerid, venderid);
		return likeReo.save(newLikeVender);
		
	}

}
