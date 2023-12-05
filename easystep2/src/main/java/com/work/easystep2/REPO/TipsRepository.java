package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Tips;

public interface TipsRepository extends JpaRepository<Tips, Integer> {

	Tips 	findTipsById(Integer id);
	
	Tips	findTipsByTitle(String targetTitle);
	
	void	deleteTipsByTitle(String targetTitle);
	
}
