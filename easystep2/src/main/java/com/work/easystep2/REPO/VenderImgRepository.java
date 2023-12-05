package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.VenderImg;

import jakarta.transaction.Transactional;

public interface VenderImgRepository extends JpaRepository<VenderImg, Integer> {
	
	List<VenderImg> findByVenderid(Integer Venderid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from vender_img where vender_venderid=:n", nativeQuery = true)
	void deletePhotos(@Param(value = "n")Integer venderId);
	
}
