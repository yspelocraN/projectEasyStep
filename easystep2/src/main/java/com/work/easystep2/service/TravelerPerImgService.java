package com.work.easystep2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.TravelerPerImg;
import com.work.easystep2.REPO.TravelerPerImgRepository;

import jakarta.transaction.Transactional;

@Service
public class TravelerPerImgService {

	@Autowired
	TravelerPerImgRepository imgRep;

	@Transactional
	public TravelerPerImg save(TravelerPerImg travelerPerImg){
		return imgRep.save(travelerPerImg);
	}

}
