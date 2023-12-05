package com.work.easystep2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.VenderPerImg;
import com.work.easystep2.REPO.VenderPerImgRepository;

import jakarta.transaction.Transactional;

@Service
public class VenderPerImgService {

	@Autowired
	VenderPerImgRepository imgRep;
	
	
	@Transactional
	public VenderPerImg save(VenderPerImg  venderPerImg){
		return imgRep.save(venderPerImg);
	}

}
