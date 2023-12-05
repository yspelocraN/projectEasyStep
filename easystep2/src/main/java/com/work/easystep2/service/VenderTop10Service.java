package com.work.easystep2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.VenderTop10;
import com.work.easystep2.REPO.VenderTop10Repository;

@Service
public class VenderTop10Service {

	@Autowired
	private VenderTop10Repository vTop10Rep;
	
	// 用城市找廠商
	public List<VenderTop10> getAllVender(String city) {
		List<VenderTop10> list = vTop10Rep.findVenderByZipCity(city);
		return list;
	}
	
	// 用id找廠商
	public VenderTop10 getLikeVender(Integer venderId) {
		return vTop10Rep.findByVenderId(venderId);
	}
	
}
