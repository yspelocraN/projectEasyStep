package com.work.easystep2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.Zip;
import com.work.easystep2.REPO.ZipRepository;

@Service
public class ZipService {

	@Autowired
	private ZipRepository zipRe;
	
	public Zip findCity(String zipno) {
		Zip zip = zipRe.findZipByZipno(zipno);
		return zip;
	}
	
}
