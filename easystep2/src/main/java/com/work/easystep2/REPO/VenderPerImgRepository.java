package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderPerImg;

public interface VenderPerImgRepository extends JpaRepository<VenderPerImg, Integer>{

	VenderPerImg findVenderPerImgByphotoToken(String photoToken);
	
	VenderPerImg findVenderPerImgByphotoid(Integer photoid);
	
	VenderPerImg findByVender(Vender vender);
	
	/* 從Vender ID進行刪除 */
	void deleteByVenderVenderid(Integer vId);
	
}
